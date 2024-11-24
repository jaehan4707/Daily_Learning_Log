## 코루틴 빌더

-   코루틴을 생성하는 데 사용하는 함수, 빌더 함수를 호출하면 새로운 코루틴이 생성된다.
-   ex): runBlocking, launch

모든 코루틴 빌더 함수는 코루틴을 만들고 코루틴을 추상화한 Job 객체를 생성한다.  
launch 함수 또한 코루틴 빌더이므로, 다음과 같이 launch 함수를 호출하면 코루틴이 만들어지고, Job 객체가 생성돼 반환된다.  
반환된 Job 객체는 코루틴의 상태를 추적하고 제어하는 데 사용된다.

### join을 사용한 코루틴 순차 처리

코루틴 간에 순차 처리가 필요한 경우는 어떤 경우일까?

대표적인 예로는

1.  데이터베이스 작업을 **순차적으로 처리**해야 하는 경우
2.  캐싱된 토큰 값이 업데이트된 이후에 네트워크 요청을 해야 하는 경우

이러한 경우는 각 작업을 하는 코루틴이 **각자에 순서에 맞게 순차적으로 처리**돼야 할 것이다.

Job 객체는 순차 처리가 필요한 상황을 위해 **join** 함수를 제공한다.  
join 함수의 역할은 다음과 같다.

-   작업 A, 작업 B가 있을 때, 작업 A → 작업 B로 진행되어야 한다.

이럴 경우 작업B에 대해, “작업 A가 끝나면 시작해”와 같은 명령을 줄 수 있는 함수가 join 함수이다.

전문적으로 풀어서 설명하면

**join 함수는 먼저 처리돼야 하는 코루틴의 실행이 완료될 때까지 호출부의 코루틴을 일시 중단하도록 만든다.**

아래 코드는 순차 처리가 안될 경우의 상황이다.

```
fun main() = runBlocking {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Updating token...")
        delay(1000L)
        println("[${Thread.currentThread().name}] Token updated!")
    }

    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Network calling!")
    }
}
```

코드의 의도는 토큰을 업데이트하고, 업데이트된 코드를 바탕으로 네트워크 작업을 요청하는 것이지만, **실제 결과는 그렇지 않다.**

![](https://blog.kakaocdn.net/dn/xQehV/btsKQzqr8dB/u480E68BhLQiPAgBat6n4k/img.png)

결과는 토큰 업데이트 시작 이후 토큰 업데이트가 끝나기 전에 네트워크 요청을 하는 것을 볼 수 있다.

![](https://blog.kakaocdn.net/dn/bdA58g/btsKSC6YjuX/go0YISagUKdcmsyAtajpNK/img.png)

위 코드를 도식화한 그림이다.

1.  runBlocking 코루틴은 메인 스레드에서 실행되는 코루틴으로 runBlocking 코루틴에서 launch 함수를 호출해 updateTokenJob을 생성하고 Dispatchers.IO에 해당 코루틴을 실행 요청한다.
2.  Dispatchers.IO는 worker-1 스레드에 해당 코루틴을 할당해 실행시킨다.
3.  runBlocking 코루틴은 launch 함수를 한 번 더 호출해 neworkCallJob을 생성하고, Dispatchers.IO에 실행 요청 → worker-3 스레드에 networkCallJob을 보내 실행시킴.

위 코드는 잘못된 코드로, **토큰 요청과 네트워크 요청 작업이 병렬로 동시에 실행된다.**

이 문제를 해결하기 위해 토큰 요청이 완료된 이후에 networkCallJob이 실행돼야 한다. 코루틴,  
job 객체는 이러한 문제 해결을 위해 순차 처리할 수 있는 join 함수를 제공한다.

#### join 함수 사용 해 순차 처리하기

사용 방법은 간단한데, JobA 코루틴이 완료된 후에 JobB 코루틴이 실행돼야 한다면 JobB 코루틴이 실행되기 전에  
JobA 코루틴에 join 함수를 호출하면 된다.

```
fun main() = runBlocking {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Updating token...")
        delay(1000L)
        println("[${Thread.currentThread().name}] Token updated!")
    }
    updateTokenJob.join()
    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Network calling!")
    }
}
```

![](https://blog.kakaocdn.net/dn/DVpRE/btsKR8efXZN/e2ZonJ1tLQxEDl58fj4DQk/img.png)

Job 객체의 join 함수를 호출하면 join의 대상이 된 코루틴의 작업이 완료될 때까지 join을 호출한 코루틴이 일시 중단된다.

1.  updaetTokenJob.join이 호출되면, runBlocking 코루틴은 updateTokenJob 코루틴이 완료될 때까지 일시 중단된다.
2.  이후 updateTokenJob 내부의 코드가 모두 실행되면 runBlocking 코루틴이 재개돼 networkCallJob을 실행한다.
3.  토큰 업데이트 완료 → 네트워크 요청

![](https://blog.kakaocdn.net/dn/bZK3Cd/btsKQBIxFUE/KSfVPg7XMPcwYTTycbtrk1/img.png)

위 코드는 다음과 같은 과정으로 진행된다.

**join 함수는 join을 호출한 코루틴만 일시 중단한다.**

-   join 함수는 join 함수를 호출한 코루틴을 제외하고 이미 실행중인 다른 코루틴을 일시 중단하지 않는다.

```
fun main() = runBlocking {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Updating token...")
        delay(1000L)
        println("[${Thread.currentThread().name}] Token updated!")
    }
    val independentJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Independent job...")
    }
    updateTokenJob.join()
    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Network calling!")
    }
}
```

![](https://blog.kakaocdn.net/dn/80tRG/btsKQF5gjYH/4rfGhN5dC1sc7E3s5dXtk1/img.png)

코드의 동작 결과는 토큰 업데이트 요청 → 독립 작업 시작 → 토큰 업데이트 완료 → 네트워크 요청

즉 updateTokenJob.join()이 호출되더라도 updateTokenJob이 끝날 때까지 independentJob은 기다리지 않고 실행되는 것을 확인할 수 있다.

![](https://blog.kakaocdn.net/dn/bLTds1/btsKQq8jFZ6/ZF3uUURJiXHRLB8kPvxRIK/img.png)

1.  runBlocking 코루틴은 updateTokenJob.join을 호출하기 전에 independentJob을 실행한다.
2.  join 함수를 호출한 코루틴은 runBlocking 코루틴이고, join의 대상이 된 코루틴은 updateTokenJob이므로, runBlocking 코루틴만 updateTokenJob이 완료될 때까지 중단된다.
    1.  다른 스레드에서 이미 실행중인 independentJob은 일시중단에 영향을 받지 않는 것이다.

### joinAll을 사용한 코루틴 순차 처리

join을 통해서 하나의 코루틴을 일시중단 할 수 있는데, 여러 개의 코루틴의 실행이 모두 끝날 때까지 **일시 중단**시키는 방법은 없을까?

**그러기 위해서 코루틴은 joinAll 함수를 제공한다.**

#### joinAll 함수

![](https://blog.kakaocdn.net/dn/lQmsq/btsKQyk4YpM/KBNCSSGt2kswnNkOUWISj1/img.png)

내부 동작은 간단한데, 다음과 같이 가변 인자로 Job 타입의 객체를 받은 후 Job 객체에 대해 모두 join 함수를 호출한다.

이를 통해 joinAll의 대상이 된 코루틴들의 실행이 모두 끝날 때까지 호출부의 코루틴을 일시 중단한다.

아래는 이미지 2개를 변환한 후 변환된 이미지를 서버에 올려야 하는 상황의 코드이다.

```
fun main() = runBlocking {
    val job1 = launch(Dispatchers.Default) {
        delay(1000L)
        println("[${Thread.currentThread().name}] job1 is finished")
    }
    val job2 = launch(Dispatchers.Default) {
        delay(1000L)
        println("[${Thread.currentThread().name}] job2 is finished")
    }
    joinAll(job1, job2)
    val uploadImageJob: Job = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] image 1,2 upload")
    }
}
```

![](https://blog.kakaocdn.net/dn/SigBA/btsKSGH7B9I/xhHmIKu9E5ykYYo8O0eHYk/img.png)

위 코드의 진행을 도식화하면 다음과 같다.

![](https://blog.kakaocdn.net/dn/HgK0g/btsKQwnfiaA/rPI1UKt15CMvRtxuN6C2xK/img.png)

간단하게 설명하면,

1.  Job1과 Job2는 Dispatchers.Default에 의해 공유 스레드풀의 스레드인 worker-1, worker-2에 각각 할당돼 처리된다.
2.  joinAll을 통해 Job1, Job2가 완료될 때까지 runBlocking 코루틴(메인 스레드에 속함)은 일시 중단된다.
3.  Job1, Job2가 모두 완료되면 runBlocking 코루틴이 재개돼 updateImageJob을 실행 요청

### CoroutineStart.LAZY

코루틴을 빌더 함수를 통해 생성하면, 바로 실행이 된다. 만약 코루틴을 생성만 하고 나중에 실행하고 싶을 경우 어떻게 할 수 있을까?

코루틴 라이브러리는 생성된 코루틴을 지연 시작할 수 있는 기능을 제공하고 있다.

```
fun main() = runBlocking {
    val job1 = launch(Dispatchers.Default) {
        delay(1000L)
        println("[${Thread.currentThread().name}] job1 is finished")
    }
    val lazyJob : Job = launch(start = CoroutineStart.LAZY) {
        println("[${Thread.currentThread().name}] 지연 실행")
    }
}
```

보통의 코루틴은 launch 함수를 호출하고, 가용 가능한 스레드가 있다면 곧바로 실행된다. 따라서 Job1은 바로 실행되고, 지연시작을 설정한 lazyZob은 실행되지 않을 것이다.

![](https://blog.kakaocdn.net/dn/bbhuYM/btsKSFbyZOi/dQFOmMG5fMCkg6upchjoSK/img.png)

**즉 지연 코루틴은 명시적으로 실행을 요청하지 않으면 실행되지 않는다.**

지연 코루틴을 실행하기 위해서는 Job 객체의 start 함수를 명시적으로 호출해야 한다.

```
fun main(): Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch(Dispatchers.Default) {
        println("[${getElapsedTime(startTime)}] job1 is finished")
    }
    val lazyJob: Job = launch(start = CoroutineStart.LAZY) {
        println("[${getElapsedTime(startTime)}] 지연 실행")
    }
    delay(1000L)
    lazyJob.start()
}
```

![](https://blog.kakaocdn.net/dn/EIPFq/btsKRQSpKTL/yySli4JUHKu6TzstUGv4l0/img.png)

job1은 launch 블록을 통해 바로 시작되고, lazyJob은 delay를 지난 후, 명시적인 start 호출을 통해 코루틴이 실행된다.

### 코루틴 취소하기

코루틴을 취소해야 하는 이유가 뭘까?

**스레드를 생성하는 비용은 상당히 비싸다. 따라서 재사용 가능하게 스레드를 활용하게 만드는 방법으로 발전했고, 코루틴은 스레드 안에서 실행된다.**

하지만 코루틴이 실행될 필요가 없는데, 계속해서 실행된다면 그만큼 사용 가능한 스레드의 수에서 손해를 보게 되고, 이는 성능 저하로 이어질 것이다.

**이러한 문제를 해결하기 위해 Job 객체는 코루틴을 취소할 수 있는 cancel 함수를 제공한다.**

#### cancel

```
fun main(): Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch(Dispatchers.Default) {
        repeat(5) {
            delay(1000L)
            println("[${getElapsedTime(startTime)}] 반복횟수 $it")
        }
    }
    delay(3100L)
    job1.cancel()
}

```

![](https://blog.kakaocdn.net/dn/brCCjT/btsKQJfFnAP/NdqiI6keMIXW2h1ij3MDb0/img.png)

위 코드는 1초 대기 후 반복 횟수를 출력하는 작업을 5번 반복하는 코루틴이다.

그러다 3.1초가 지난 후, cancel을 통해서 코루틴이 취소됨을 확인할 수 있다.

#### cancelAndJoin

cancel 함수를 통해 코루틴을 취소하고, 다른 작업을 실행하면 해당 작업은 코루틴이 취소되기 전에 실행될 수 있다.

말이 조금 어려운데, 코드를 통해 살펴보자.

```
fun main(): Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch(Dispatchers.Default) {
        repeat(5) {
            delay(1000L)
            println("[${getElapsedTime(startTime)}] 반복횟수 $it")
        }
    }
    delay(3100L)
    job1.cancel()
    executeJob2()
}
```

예를 들어 Job1이 끝나고, 이어서 Job2를 실행시키려고 하는 코드이다. 위 코드는 문맥상으로 문제가 없어 보이지만, 순차성 관점에서 중요한 문제점이 있다.

**Job 객체에 cancel을 호출하면 코루틴은 즉시 취소되는 것이 아니다.**

조금 더 보충설명하면, 취소 요청을 보내는 것이다. 따라서 cancel 함수를 사용하면 cancel의 대상이 된 Job 객체는 곧바로 취소되는 것이 아니라 미래의 어느 시점에 취소된다.

따라서 Job1이 취소되고, Job2를 실행하려는 함수가 실행되는 것을 보장할 수 없는 것이다.

Job을 취소하고 바로 함수의 실행을 보장하기 위해선 Job 객체의 cancelAndJoin 함수를 사용하면 해결할 수 있다.

cancelAndJoin 함수는 대상이 된 코루틴의 취소가 완료될 때까지 코루틴을 일시 중단시킨다.

```
fun main(): Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch(Dispatchers.Default) {
        repeat(5) {
            delay(1000L)
            println("[${getElapsedTime(startTime)}] 반복횟수 $it")
        }
    }
    delay(3100L)
    job1.cancelAndJoin()
    executeJob2()
}
```

그러면 job1이 취소 완료될 때까지 runBlocking 코루틴이 일시 중단되고, Job2 실행 함수의 호출을 보장할 수 있게 된다.

### 코루틴의 취소 확인

**cancel** 함수나, **cancelAndJoin** 함수는 코루틴을 즉시 취소하는 것이 아닌 취소 요청을 보내는 함수이다. 취소 요청을 받은 코루틴이 요청을 확인하는 시점에 비로소 취소가 된다.

만약 코루틴이 취소 요청을 확인하지 않는다면 영원히 취소되지 않는다.

그렇다면 이 코루틴들은 언제 취소를 확인할까?

코루틴이 **취소를 확인하는 시점은 일반적으로 일시 중단 지점이나 코루틴이 실행을 대기하는 시점**이며,  
이 시점들이 없다면 코루틴은 취소되지 않는다.

```
fun main(): Unit = runBlocking {
    val job1 = launch(Dispatchers.Default) {
          while(true){
            println("작업 중!")
        }
    }
    delay(1000L)
    job1.cancel()
}
```

![](https://blog.kakaocdn.net/dn/rB7UZ/btsKTVmfdIn/8bzu2uYPLkQLKzzQxzMPiK/img.png)

1초 후 cancel 요청을 했지만, 코루틴이 취소되지 않고, 계속해서 while문을 실행시킨다.

취소되지 않는 이유는 **코루틴의 취소를 확인하지 못했기 때문이다.**

현재 코드로는 while문을 벗어날 수 없고, while 문 내부에도 일시 중단 지점이 없기 때문에 취소를 확인할 시점이 없다.

해당 코드가 취소되도록 만드는 방법은 세 가지 방법이 있다.

1.  dealy
2.  yield
3.  CoroutineScope.isActive

#### delay를 사용한 취소 확인

delay는 suspend 함수로 선언돼 특정 시간만큼 호출부의 코루틴을 일시 중단하게 만든다.  
코루틴은 일시 중단되는 시점에 코루틴의 취소를 확인하기 때문에 while문 내부에 delay를 사용하면 일시 중단 후 취소를 확인할 수 있다.

```
fun main(): Unit = runBlocking {
    val job1 = launch(Dispatchers.Default) {
        while (true){
            println("작업 중!")
            delay(1L)
        }
    }
    delay(10L)
    job1.cancel()
}
```

![](https://blog.kakaocdn.net/dn/VpZXx/btsKU8ZbqDs/EacXwWl0ptTuh2Co9GY3C0/img.png)

코드의 실행 결과를 보면 10ms 이후에 프로세스가 종료됨을 확인할 수 있다.

**하지만 이 방법은 while문이 반복될 때마다 작업을 일시 중단 시키고 있고, 이는 성능저하의 원인이 될 수 있다.**

#### yield를 사용한 취소 확인

> **yield 함수는 자신이 사용하던 스레드를 양보한다.  
> **

**스레드 사용을 양보한다는 것은 스레드 사용을 중단한다는 뜻**으로 yield를 호출한 코루틴이 일시 중단되며 이 시점에 취소됐는지 확인한다.

```
fun main(): Unit = runBlocking {
    val job1 = launch(Dispatchers.Default) {
        while (true){
            println("작업 중!")
            yield()
        }
    }
    delay(10L)
    job1.cancel()
}
```

![](https://blog.kakaocdn.net/dn/b5ivZ9/btsKTDTnHnH/AAXlcjxc41TTMg5qDywtkk/img.png)

**하지만 근본적으로 while 문 내부에서 일시 중단시키는 과정은 반복되고, 이러한 작업은 성능 저하로 이어진다.**

#### CoroutineScope.isActive를 사용한 취소 확인

**CorotuineScope**는 코루틴이 활성화됐는지 확인할 수 있는 **Boolean 타입의 프로퍼티인 isActive**를 제공한다.  
코루틴에 취소가 요청되면 isActive 프로퍼티의 값은 false로 바뀌며, while 문의 인자로 isActive를 넘기면 코루틴이 취소 요청이 되면 while문을 취소시킬 수 있다.

```
fun main(): Unit = runBlocking {
    val job1 = launch(Dispatchers.Default) {
        while (this.isActive) {
            println("작업 중!")
            yield()
        }
    }
    delay(10L)
    job1.cancel()
}
```

![](https://blog.kakaocdn.net/dn/dy3MDD/btsKVvT4Cy9/McBlcZNwbmpgodwi5YLkIK/img.png)

이 방법을 사용하면 코루틴이 잠시 멈추지도 않고 스레드 사용을 양보하지도 않으면서 계속 작업을 할 수 있어 효율적이다.

### 코루틴의 상태와 Job의 상태 변수

![](https://blog.kakaocdn.net/dn/emRMCH/btsKS80FU8t/h8dmdQSunYn66xVtTdYxK1/img.png)

코루틴은 위 그림과 같이 6가지 상태를 가질 수 있다.

-   생성 : 코루틴 빌더를 통해 코루틴을 생성하면 코루틴은 기본적으로 생성 상태에 놓이며, **자동으로 실행 중 상태로 넘어간다**.  
    만약 생성 상태의 코루틴이 실행 중 상태로 자동으로 변경되지 않도록 만들고 싶다면 코루틴 빌더의 start 인자로 CoroutineStart.Lazy를 넘겨 지연 코루틴을 만들면 된다.
-   실행 중 : 지연 코루틴이 아닌 코루틴을 만들면 자동으로 실행 중 상태로 바뀐다.  
    코루틴이 실제로 실행 중일 때뿐만 아니라 실행된 후에 일시 중단된 때도 실행 중 상태로 본다.
-   실행 완료 : 코루틴의 모든 코드가 실행 완료된 경우 실행 완료 상태로 넘어간다.
-   취소 중 : Job.cancel(), cancelAndJoin을 통해 코루틴에 취소가 요청됐을 경우 취소 중 상태로 넘어가며, 이는 아직 취소된 상태가 아니어서 코루틴은 계속해서 실행된다.
-   취소 완료 : 코루틴의 취소 확인 시점에 취소가 확인된 경우 취소 완료 상태가 된다.  
    이 상태에서는 더 이상 실행되지 않는다.

Job 객체는 코루틴이 어떤 상태에 있는지 나타내는 상태 변수들을 외부로 공개한다.  
상태 변수는 **isActive, isCancelled, isCompleted**의 세가지이며, 각 변수는 모두 boolean 타입이다.

-   isActive : 코루틴이 활성화 돼 있는지의 여부, 코루틴이 활성화돼 있으면 true를 반환하고, 활성화돼 있지 않으면 false를 반환한다. 활성화돼 있다는 것은 코루틴이 실행된 후 취소가 요청되거나 실행이 완료되지 않은 상태라는 의미이다. 따라서 취소가 요청되거나 실행이 완료된 코루틴은 활성화되지 않은 것으로 본다.
-   isCancelled : 코루틴이 취소 요청됐는지의 여부, 요청되기만 하면 true가 반환되므로 true이더라도 즉시 취소되는 것은 아니다.
-   isCompleted : 코루틴 실행이 완료됐는지의 여부, 실행 중일 경우 false를 반환하고, 실행 완료, 취소 완료일 경우 true를 반환한다.

#### 생성 상태의 코루틴

![](https://blog.kakaocdn.net/dn/pfeHG/btsKVtvfmx8/vaFmkO70GpBMzHnqW9vkKk/img.png)

```
**코루틴이 생성만 되고 실행되지 않은 상태**
```

생성 상태의 코루틴을 만들기 위해서는 지연 시작이 적용된 코루틴을 생성해야 한다.

```
fun main(): Unit = runBlocking {
    val job1 = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
    }
    printJobState(job1)
}
```

![](https://blog.kakaocdn.net/dn/pJBEm/btsKUfEI5iO/RzXgBFis5VnMrCzKwvjKE1/img.png)

**코드의 실행 결과를 보면 생성된 후 실행 X, 취소 요청 X, 실행 완료 X → 모두 false가 반환된다.**

#### 실행 중 상태의 코루틴

![](https://blog.kakaocdn.net/dn/2iXj4/btsKVqyxIcr/LsNKVA9XeVbnFAnamZDVt1/img.png)

코루틴 빌더로 코루틴을 생성하면 **CoroutineDispatcher에** 의해 스레드로 보내져 실행되고, 이때의 코루틴의 상태를 **실행 중** 상태라 부른다.

```
fun main(): Unit = runBlocking {
    val job1 = launch {
        delay(1000L)
    }
    printJobState(job1)
}
```

![](https://blog.kakaocdn.net/dn/bTea7A/btsKT4pDpkY/MYCD1wYFrYukrz2grdJu0K/img.png)

**실행 후 취소 요청 X, 실행 완료 X → isActive만 true**

#### 실행 완료 상태의 코루틴

![](https://blog.kakaocdn.net/dn/kZmvT/btsKUiuH0br/Eo8syBO14HZKhm0qCk2AsK/img.png)

1초간 실행되는 코루틴을 생성하고 3초 대기후 Job의 상태를 출력해 보는 코드를 작성해 보자.

```
fun main(): Unit = runBlocking {
    val job1 = launch {
        delay(1000L)
    }
    delay(3000L)
    printJobState(job1)
}
```

![](https://blog.kakaocdn.net/dn/6BTL5/btsKVbIqFEj/Krhd5wMPEzq9p0iVqSJ7hK/img.png)

3초가 지난 시점이라, 코루틴이 실행 완료되고 isCompleted가 true 인 것을 확인할 수 있다.

#### 취소 중인 코루틴

![](https://blog.kakaocdn.net/dn/pnzlW/btsKT3EeIWr/KEuDgwuLakpRtQi84kWXw1/img.png)

취소가 요청됐으나 취소되지 않은 상태인 취소 중 코루틴의 상태를 하기 위해선 코루틴의 취소를 요청해야 한다.

```
fun main() = runBlocking {
    val job: Job = launch {
        while (true){

        }
    }
    job.cancel()
    printJobState(job)
}
```

위 코드에서는 코루틴이 취소를 확인할 수 있는 시점이 없어서 실제로 코루틴이 취소 요청만 보낼 뿐이지, 실제로 취소되지는 않으므로, 취소 중인 상태에 머물고 있다.

![](https://blog.kakaocdn.net/dn/ABZY5/btsKVvzLrDh/KZHHh7GlfHOwZkm0veCeIk/img.png)

따라서 결과는 isCompleted가 아닌, isCancelled의 상태가 유지된다.  
**중요한 점은 취소가 요청되고, 실제로 코드가 실행 중이더라도 코루틴이 활성화된 상태로 보지 않아 isActive는 false가 된다.**

#### 취소 완료된 코루틴

![](https://blog.kakaocdn.net/dn/1Nqxu/btsKTD6UEMw/sQR5hjBEBoobpXmS7c6wh0/img.png)

코루틴은 취소가 요청되고 취소 요청아 확인되는 시점에 취소가 완료된다.

```
fun main() = runBlocking {
    val job: Job = launch {
        delay(5000L)
    }
    job.cancelAndJoin()
    printJobState(job)
}
```

이 코드에서는 launche 함수를 통해 5초간 지속되는 코루틴을 생성한 후, 코루틴이 취소될 수 있도록 cancelAndJoin 함수를 호출한다.  
5초가 지난 후, 일시 중단 상태가 되고, 코루틴의 취소 요청을 확인했으므로, 코루틴의 상태는 취소 완료가 된다.

![](https://blog.kakaocdn.net/dn/bcG9Tv/btsKUlLm6AI/BhtEAVm3DcZqb7orr0cKr0/img.png)

#### 코루틴 상태와 Job 상태

| 코루틴 상태 | isActive | isCancelled | isCompleted |
| --- | --- | --- | --- |
| 생성 | false | false | false |
| 실행 중 | true | false | false |
| 실행 완료 | false | false | true |
| 취소 중 | false | true | false |
| 취소 완료  | false | true | true |

# 요약

1.  runBlocking 함수와 launche 함수는 코루틴을 만들기 위한 코루틴 빌더 함수이다.
2.  launch 함수를 호출하면 Job 객체가 만들어져 반환되며, Job 객체는 코루틴의 상태를 추적하고 제어하는 데 사용된다.
3.  Job 객체의 Join 함수를 호출하면 함수를 호출한 코루틴이 Job 객체의 실행이 완료될 때까지 일시 중단된다.
4.  joinAll 함수를 사용해 복수의 코루틴이 실행 완료될 때까지 대기할 수 있다.
5.  Job 객체의 cancel 함수를 사용해 코루틴에 취소를 요청할 수 있다.
6.  cancel 함수가 호출되면 코루틴이 곧바로 취소되는 것이 아니라 취소 플래그의 상태만 바뀌는 것이다.
7.  코루틴에 취소를 요청한 후 취소가 완료될 때까지 대기하고 나서 다음 코드를 실행하고 싶다면 cancel 대신 cancelAndJoin 함수를 사용하면 된다.
8.  delay, yield 함수나 isActive 프로퍼티 등을 사용해 코루틴이 취소를 확인할 수 있도록 만들 수 있다.
9.  코루틴은 생성, 실행 중, 실행 완료 중, 취소 중, 취소 완료 상태를 가진다.
10.  Job 객체는 isActive, isCancelled, isCompleted 프로퍼티를 통해 코루틴의 상태를 나타낸다.

## 참고

[https://product.kyobobook.co.kr/detail/S000212376884](https://product.kyobobook.co.kr/detail/S000212376884)