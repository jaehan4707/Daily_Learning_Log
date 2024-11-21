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