## 개요

우리가 사용하고, 개발하는 애플리케이션은 여러 상황에서 에러에 노출됩니다.

![](https://blog.kakaocdn.net/dn/dynY3x/btsLLDLRRLd/0mNwKUBAfNOJ2hhKzMmX6K/img.png)

예외가 발생했을 때 예외가 적절히 처리되지 않으면 예측하지 못한 방향으로 동작하거나 비정상 종료될 수 있습니다.

따라서 안정적인 애플리케이션을 위해선 예외를 적절하게 처리하는 것이 중요합니다.

같은 맥락에서 비동기 작업을 수행하는 코루틴의 예외 처리 또한 중요한데요,

특히 네트워크 요청이나 데이터 베이스 작업 같은 입출력 작업과 같이 중요한 작업에서 쓰입니다.

보통 이러한 작업은 예측할 수 없는 예외가 발생할 가능성이 높아 코루틴에 대한 적절한 예외 처리는 안정적인 애플리케이션을 만드는 데 필수적입니다.

코루틴은 예외를 안전하게 처리할 수 있도록 만드는 여러 장치를 갖고 있습니다.

예외가 발생했을 때 코루틴이 어떻게 동작하는지, 그리고 어떻게 처리하는지 알아봅시다.

### 코루틴의 예외 전파

#### 코루틴에서 예외가 전파되는 방식

앞선 챕터에서 코루틴의 취소 전파에 대해서 다뤘습니다.

코루틴의 취소는 부모 코루틴 -> 자식 코루틴과 같이 위에서 아래 방향으로 전파됩니다.

이와 비슷하게 코루틴에서의 예외는 자식 -> 부모로 전파됩니다.

예외가 발생한 코루틴은 취소되고 부모 코루틴으로 전파됩니다. 만약 그 부모 코루틴도 적절한 대체가 없다면 다시 상위 코루틴으로 전파되는데, 이것이 반복된다면 최상위 코루틴, 즉 루트 코루틴까지 예외가 전파될 수 있습니다.

이러한 전파는 굉장히 위험합니다.

왜냐하면 루트 코루틴이 취소된다면, 모든 코루틴에게 취소가 전파되는 최악의 상황이 나옵니다.

아래와 같이 구조화된 코루틴이 있습니다.

![](https://blog.kakaocdn.net/dn/AkZsh/btsLKJ6Y9KQ/sk5AC9W7qbU7b4ok9f78Ck/img.png)

만약 코루틴 5에서 예외가 발생하면 어떻게 될까요?

![](https://blog.kakaocdn.net/dn/dE9rxY/btsLLuajkP8/c3PlQOBc3qv3kf5hk1JTB0/img.png)

예외는 부모로 전파되기 때문에 Coroutine2 -> Coroutine1으로 예외가 전파됩니다.

만약 그전에 예외가 적절하게 대처되면 좋겠지만, 만약 적절히 처리되지 않는다면 Coroutine1은 취소됩니다.

![](https://blog.kakaocdn.net/dn/VJX5W/btsLKC0Yq7y/iJZ2BtN0uG2DmvIS4uxkG1/img.png)

코루틴이 취소되면 모든 자식 코루틴에게 취소가 전파되는 특성 때문에 모든 코루틴에 취소가 전파됩니다.

즉, 코루틴의 예외 전파를 제대로 막지 못하면 구조화된 코루틴이 모두 취소될 수 있습니다.

코드를 통해 알아봅시다.

```
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine3")) {
            throw Exception("예외 발생!")
        }
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    delay(1000L)
}
```

runBlocking 코루틴은 Coroutine1,2를 자식 코루틴으로 갖고, Coroutine1은 Coroutine3을 자식 코루틴으로 갖습니다.  
Coroutine3에서 예외가 발생했고, 코드의 실행 결과는 어떻게 될까요?

![](https://blog.kakaocdn.net/dn/Xym2B/btsLK61VOAP/BeNVNbaMxW7oEdPQKABFxK/img.png)

다른 출력문 없이 예외가 발생했다는 로그만 확인할 수 있습니다.

즉 Coroutine3에서 발생한 예외가 모든 코루틴을 취소시킨 것을 알 수 있습니다.

코루틴의 구조화는 큰 작업을 연관된 작은 작업으로 나누는 방식으로 이루어집니다.

만약 작은 작업에서 발생한 예외로 인해 큰 작업이 취소된다면 안정적인 애플리케이션은 아닐 것입니다.

### 예외 전파 제한

#### Job 객체를 사용한 예외 전파 제한

Job 객체를 사용해 예외 전파 제한하기

코루틴의 예외 전파를 제한하기 위한 방법은 코루틴의 구조화를 깨는 것입니다.

앞선 챕터에서 코루틴의 구조화를 깨는 두 가지 방법을 다뤄봤었습니다.

1.  Job 생성 후 전달
2.  CoroutineScope 생성 후 전달

이코루틴은 자신의 부모 코루틴으로만 예외를 전파하는 특성을 가지고 있으므로, 구조화를 깬다면 예외가 전파되지 않습니다.

첫 번째 방법은 새로운 Job 객체를 만들어 구조화를 깨는 것입니다.

```
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Parent Coroutine")) {
        launch(CoroutineName("Coroutine1") + Job()) {
            launch(CoroutineName("Coroutine3")) {
                throw  Exception("예외 발생!")
            }
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    delay(1000L)
}
```

이 코드에서 Coroutine1은 새로운 Job 객체를 부모 Job으로 설정함으로써 Parent Coroutine 과의 구조화를 깨고 있습니다.

따라서 Coroutine3이 예외를 발생시켜도, Coroutine1은 예외가 전파되고, Parent Coroutine은 예외가 전파되진 않습니다. 대신 새롭게 만들어진 Job 객체에 예외를 전파합니다.

![](https://blog.kakaocdn.net/dn/V6FAE/btsLKGvBKXB/UJTvQkXcy2v1npSEWr0Hw1/img.png)

Parent Coroutine에는 예외가 전파되지 않아 Coroutine2는 정상적으로 실행되는 것을 확인할 수 있습니다.

![](https://blog.kakaocdn.net/dn/bizUqc/btsLK6gzeVX/dt3tljputeyTipOvvbYd8k/img.png)

Job 객체를 사용한 예외 전파 제한의 한계

Job 객체를 생성해 코루틴의 구조화를 깨는 것은 예외뿐만 아니라 취소 전파도 제한시키는데요,

작은 작업의 구조화가 깨진다면 큰 작업에 취소가 요청되더라도 작은 작업은 취소되지 않으며 이는 비동기 작업을 불안정하게 만든다.

코드를 통해 살펴보자.

```
fun main() = runBlocking<Unit> {
    val parentJob = launch(CoroutineName("Parent Coroutine")) {
        launch(CoroutineName("Coroutine1") + Job()) {
            launch(CoroutineName("Coroutine3")) {
                delay(100L)
                println("[${Thread.currentThread().name}] 코루틴 실행")
            }
        }
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    delay(20L)
    parentJob.cancel()
    delay(1000L)
}
```

Coroutine1은 더 이상 Parent Coroutine의 자식 코루틴이 아니기 때문에 취소 전파가 제한됩니다.

따라서 코드를 실행해 보면 Parent Coroutine이 취소됐음에도 Coroutine1과 Coroutin3은 정상 실행되는 것을 확인할 수 있습니다.

![](https://blog.kakaocdn.net/dn/TpVHV/btsLKqGuY89/mBNZqkNb629H1KFf6K375K/img.png)

일반적인 흐름에서는 Parent Coroutine이 취소된다면 Coroutine1과 Coroutine3도 함께 취소 돼야 합니다.

하지만 예외 전파 방지를 위해 새로운 Job 객체를 사용해 구조화가 깨져 버려 두 코루틴은 정상 실행 됩니다.

즉 구조화를 깨지 않으면서 예외 전파를 제한할 수 있는 방법이 필요합니다.

코루틴 라이브러리는 이것을 가능하게 하기 위해 **SupervisorJob** 객체를 제공하고 있습니다.

#### SupervisorJob 객체를 사용한 예외 전파 제한

SupervisorJob 객체를 사용해 예외 전파 제한하기

SupervisorJob

![](https://blog.kakaocdn.net/dn/bmOkAE/btsLM51lIDS/DdITECeBRreT03NjPJEPbk/img.png)

> 자식 코루틴으로부터 예외를 전파받지 않는 특수한 Job 객체로 하나의 자식 코루틴에서 발생한 예외가 다른 자식 코루틴에게 영향을 미치지 못하도록 만드는 데 사용된다.

일반적인 Job 객체는 예외가 발생하면 예외를 전파받아 취소되지만 SupervisorJob 객체를 취소되지 않는다.

SupervisorJob 객체도, Parent 인자 없이 사용하면 루트 Job으로 만들 수 있으며 parent 인자를 넘기면 부모 Job이 있는 SupervisjorJob 객체를 만들 수 있다.

코드를 통해 살펴보자.

```
fun main() = runBlocking<Unit> {
    val supervisorJob = SupervisorJob()
    launch(CoroutineName("Coroutine1") + supervisorJob) {
        launch(CoroutineName("Coroutine3")) {
            throw Exception("예외 발생!!")

        }
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    launch(CoroutineName("Coroutine2") + supervisorJob) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
}
```

코루틴 1의 부모 Job을 supervisorJob으로 전달한 코드입니다.

![](https://blog.kakaocdn.net/dn/bxdqBy/btsLM0mzAYs/nNfX8wpMLWBvvlRqxuoEhk/img.png)

그렇다면 코루틴 3의 에러가 코루틴 1로 전파돼 코루틴 1을 취소시키지만, 코루틴 1은 supervisorJob으로 예외를 전파하지 않습니다.

따라서 코드를 실행해 보면 supervisorJob의 다른 자식 코루틴인 Coroutine2 코루틴이 정상 실행되는 것을 볼 수 있습니다.

![](https://blog.kakaocdn.net/dn/SnfW0/btsLMuhz5i6/b2BPYNXZpC7wQUAeSQC3r0/img.png)

SupervisorJob 객체는 자식 코루틴의 예외를 전파받지 않는 특성을 가지고 있습니다.  
하지만 위 구조화의 그림처럼 SupervisorJob 객체는 runBlocking이 호출돼 만들어진 Job 객체와의 구조화를 깨는 점이 문제입니다.

코루틴의 구조화를 깨지 않고 SupervisorJob 사용하기

구조화를 깨지 않고 SupervisorJob을 사용하기 위해서는 인자로 부모 Job 객체를 넘기면 됩니다.

```
fun main() = runBlocking<Unit> {
    val supervisorJob = SupervisorJob(parent = this.coroutineContext[Job])
    launch(CoroutineName("Coroutine1") + supervisorJob) {
        launch(CoroutineName("Coroutine3")) {
            throw Exception("예외 발생!!")

        }
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    launch(CoroutineName("Coroutine2") + supervisorJob) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    supervisorJob.complete()
}
```

SupervisorJob()을 통해 생성된 Job 객체는 Job()을 통해 생성된 객체와 같이 자동으로 완료 처리가 되지 않기 때문에 명시적 완료 처리인 complete를 호출해 완료 처리를 해줘야 합니다.

위 코드처럼 supervisorJob의 인자로 부모 Job을 넘겨주면, runBlocking 코루틴과의 구조화를 깨지 않습니다.

![](https://blog.kakaocdn.net/dn/YO1ny/btsLN9CKtlH/uG5PYnX9npKKYqaJWahdhK/img.png)

SupervisorJob을 CoroutineScope와 함께 사용하기

CoroutineScope의 CoroutineContext에 SupervisorJob 객체가 설정된다면 CoroutineScope의 자식 코루틴에서 발생하는 예외가 다른 자식 코루틴으로 전파되지 않습니다.

```
fun main() = runBlocking<Unit> {
    val coroutineScope = CoroutineScope(SupervisorJob())
    coroutineScope.apply {
        launch(CoroutineName("Coroutine1")) {
            launch(CoroutineName("Coroutine3")) {
                throw Exception("예외 발생!!")

            }
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
}
```

위 코드에서는 CoroutineScope 생성 함수의 SupervisorJob()을 인자로 넘겨 CoroutineScope 객체가 생성됩니다.

하지만 새로운 CoroutineScope의 생성으로 runBlocking 코루틴과의 구조화는 깨지게 됩니다.

![](https://blog.kakaocdn.net/dn/cf9Yee/btsLMqfck0y/tat7dU54t7fV9vB2gcI8s1/img.png)

코드를 실행해 보면 예외가 발생했음에도 코루틴 2가 정상 실행되는 것을 확인할 수 있습니다.

![](https://blog.kakaocdn.net/dn/bM3TYK/btsLMKdlYZd/2iSrMvo5KueSK9AVP0o5Dk/img.png)

SupervisorJob을 사용할 때 흔히 하는 실수

예외 전파 방지를 위해 코루틴 빌더 함수의 context 인자에 SupervisorJob()을 넘겨 생성되는 코루틴의 하위에 자식 코루틴을 생성하는 것은 큰 문제를 내포하고 있습니다.

```
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Parent Coroutine")+ SupervisorJob()) {
        launch(CoroutineName("Coroutine1")) {
            launch(CoroutineName("Coroutine3")) {
                throw  Exception("예외 발생!")
            }
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    delay(1000L)
}
```

겉보기에는 문제가 없어 보이지만, launch 함수는 context 인자에 Job 객체가 입력될 경우 해당 Job 객체를 부모로 하는 새로운 Job 객체를 새로 생성합니다.

즉, launch 함수에 SupervisorJob()을 인자로 넘기면 SupervisorJob()을 통해 만들어지는 새로운 Job 객체가 만들어져 다음과 같은 구조가 형성됩니다.

![](https://blog.kakaocdn.net/dn/46G5H/btsLOtA0Rl9/2vgkKEEmgMbCIUBaQ2Jne0/img.png)

만약 이런 구조에서 코루틴 3에 예외가 발생하면 어떻게 될까요?

![](https://blog.kakaocdn.net/dn/wUvaa/btsLL8r8Sah/XKTz9bFyqjkKQJV5AdzY6k/img.png)

코루틴 3에서 발생한 예외가 코루틴 1을 통해 부모 코루틴까지 전파돼 취소되며, 자식 코루틴인 코루틴 2도 취소됩니다.

부모 코루틴의 예외가 SupervisorJob 객체로 전파되지는 않지만 이는 아무런 쓸모가 없습니다.

이렇게 SupervisorJob 객체는 예외 전파를 방지하는 강력한 도구이지만, 잘못 사용하면 기능을 제대로 수행하지 못할 수 있습니다.

따라서 Job 계층 구조를 정확하게 파악하고, 어떤 위치에 있어야 하는지 숙지한 후 사용해야 합니다.

#### supervisorScope를 사용한 예외 전파 제한

예외 전파의 세 번째 방법은 supervisorScope 함수를 사용하는 것입니다.

해당 함수는 SupervisorJob 객체를 가진 CoroutineScope 객체를 생성합니다.

즉 내부 구현으로 CoroutineScope에 SupervisorJob를 인자로 넘겨 복잡한 설정 없이 구조화를 깨지 않고 예외 전파를 제한하는 CoroutineScope를 생성할 수 있습니다. 또한 명시적 완료 호출 없이 자식 코루틴이 모두 실행 완료되면 자동으로 완료 처리됩니다.

```
fun main() = runBlocking<Unit> {
    supervisorScope {
        launch(CoroutineName("Coroutine1")) {
            launch(CoroutineName("Coroutine3")) {
                throw Exception("예외 발생!")
            }
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine2")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
}
```

위 코드는 runBlocking 함수에 의해 루트 Job 객체가 생성되고, 자식 코루틴으로 SupervisorJob 객체를 가집니다.

그 아래에 코루틴 1, 코루틴 2, 코루틴 3을 자식 코루틴으로 가집니다.

![](https://blog.kakaocdn.net/dn/HrNA4/btsLNVrlRwg/7g93GAvkwnQTGkffgjvPD1/img.png)

이 경우 만약 코루틴 3에서 예외가 발생한다면 코루틴 1까지만 예외가 전파됩니다.

따라서 코드 실행 결과 코루틴 2가 정상적으로 실행되는 것을 확인할 수 있습니다.

![](https://blog.kakaocdn.net/dn/b18veQ/btsLOplaOUp/s99m631kQADOWhvKWHgvjK/img.png)

이렇게 코루틴의 예외 전파를 제한하는 세 가지 방법을 알아봤습니다.

1.  Job 생성
2.  SupervisorJob
3.  SupervisorScope

각각의 장점이 있음로 상황에 맞게 고민하고 사용하는 것이 정답이라고 생각합니다.

### CoroutineExceptionHandler를 사용한 예외 처리
구조화된 코루틴들에 공통적인 예외 처리기를 설정해야 할 경우가 있습니다.

코루틴은 이를 위해 CoroutineContext 구성 요소로 **CoroutineExceptionHandler**라는 예외 처리기를 지원하고 있습니다.

#### CoroutineExceptionHandler 생성

```
public inline fun CoroutineExceptionHandler(crossinline handler: 
(CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler { /* compiled code */ }
```
​
CoroutineExceptionHandler 함수는 예외를 처리하는 람다식 **handler**를 매개변수로 가집니다.
​
handler는 **CoroutineContext**와 **Throwable** 타입의 매개변수를 갖는 **람다식으로**, 이 람다식에 **예외 발생 시 어떠한 동작을 수행할지 입력합니다.**
​
간단한 CoroutineExceptionHandler 객체입니다.
​
```
val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable->
    println("[예외 발생] ${throwable}")
}
```
​
#### CoroutineExceptionHandler 사용
​
생성된 CoroutineExceptionHandler 객체는 CoroutineContext 구성요소로 포함될 수 있습니다.
​
```
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] $throwable")
    }
    CoroutineScope(exceptionHandler).launch(CoroutineName("Coroutine1")) {
        throw Exception("Coroutine1에 예외 발생!")
    }
    delay(1000L)
}
```
​
CoroutineScope 함수가 호출되면 Job 객체가 새로 생성되므로, **구조화가 깨진다**는 사실은 이제 다들 알고 계실거라 생각합니다.
​
그리고 코루틴1은 **CoroutineScope의 구성요소를 상속받기 때문에 선언된 exceptionHandler도 상속받습니다.**
​
위 코드를 구조화하면 아래와 같습니다.
​
![](https://blog.kakaocdn.net/dn/sqH0G/btsLQBe28Hr/etSABTGp2vEguBZX9UzUI1/img.png)
​
실행 결과는 exceptionHandler에 의해 예외가 처리돼 예외 정보가 출력됨을 확인할 수 있습니다.
​
​
![](https://blog.kakaocdn.net/dn/cJGdBw/btsLPu2glea/mmVoaJHkDc6AWDH7eR7VI0/img.png)
여기서 궁금증이 생길 수 있습니다.
​
exceptionHandler는 상속받아서 코루틴1과 코루틴스코프 객체 모두 설정돼 있는데, **어디에서 예외가 처리된 것일까요?**
​
#### 처리되지 않은 예외만 처리하는 CoroutineExceptionHandler
​
**CoroutineExceptionHandler 객체는 처리되지 않은 예외만 처리합니다.**
​
**예외가 전파되었다면 그 예외는 처리된 것으로 처리합니다.**
​
자식 코루틴이 부모 코루틴으로 예외를 전파할 경우 자식 코루틴에 설정된 handler는 예외가 처리된 것으로
​
보기 때문에 동작하지 않습니다.
​
위 궁금증에 대답은 **코루틴 스코프**라고 할 수 있겠습니다.
​
```
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] $throwable")
    }
    launch(CoroutineName("Coroutine1")+exceptionHandler) {
        throw Exception("Coroutine1에 예외 발생!")
    }
    delay(1000L)
}
```
​
위 코드에서는 구조화가 깨지지 않은 채, runBlocking의 자식 코루틴으로 코루틴1이 생성되고, 핸들러는 코루틴1에만 설정됩니다.
​
![](https://blog.kakaocdn.net/dn/bmR77N/btsLPvtlE2W/g1JcVGEJ0epcKgCB5vfwL0/img.png)
​
코루틴 1에서 예외가 발생하므로 설정된 핸들러가 예외를 처리할 것처럼 보이지만, 실제 실행 결과는 에러가 처리되지 않고 종료됩니다.
​
![](https://blog.kakaocdn.net/dn/cSDQfd/btsLQRolNPi/a4x1knIgtIsxfG32mdANA0/img.png)
​
그 이유는 코루틴1이 runBlocking 코루틴으로 예외를 전파했기 때문입니다.
​
코루틴은 예외가 전파 == 예외 처리로 생각하기 때문에 핸들러는 이미 처리된 예외라고 생각해서 동작하지 않는 것입니다.
​
**따라서 계층 상 여러 CoroutineExceptionHandler가 설정돼 있더라도 마지막으로 예외를 전파받는 위치에 설정된 객체만 예외를 처리하게 됩니다.**
​
CoroutineExceptionHandler가 동작하도록 만들기 위해서는 CoroutineExceptionHandler가 **설정된 위치를 오류가 처리되는 위치로 만들어야 합니다.**
​
#### CoroutineExceptionHandler가 예외를 처리하도록 만들기
​
```
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] $throwable")
    }
    CoroutineScope(exceptionHandler).launch(CoroutineName("Coroutine1")) {
        throw Exception("Coroutine1에 예외 발생!")
    }
    delay(1000L)
}
```
​
위 코드에서 CoroutineExceptionHandler가 동작할 수 있었던 이유는 예외가 **마지막으로 처리되는 위치**인
​
CoroutineScope가 CoroutineExceptionHandler 설정돼 있었기 때문입니다.
​
Job과 CoroutineExceptionHandler 함께 설정하기
​
CoroutineExceptionHandler가 예외를 처리하게 하는 가장 간단한 방법은
​
**CoroutineExceptionHandler 객체를 루트 Job과 함께 설정하는 것입니다.**
​
**Job()을 호출하면 구조화를 끊고, 새로운 루트 Job을 만들 수 있으므로 이를 사용하면 CoroutineExceptionHandler 객체가 설정되는 위치를 마지막으로 예외를 전파받는 위치로 만들 수 있습니다.**
​
SupervisorJob과 CoroutineExceptionHandler 함께 사용하기
​
**SupervisorJob 객체는 예외를 전파받지 않는 특수한 Job 객체입니다.**
​
그렇다면 SupervisorJob과 CoroutineExceptionHandler를 함께 사용하면 어떻게 동작될까요?
​
**예외를 전파 받지 않으니 Handler가 동작하지 않을까요?**
​
**정답은 예외가 처리된다입니다.**
​
SupervisorJob 객체는 예외를 전파 받지 않을 뿐, **예외 발생에 대한 정보는 자식 코루틴으로부터 전달받습니다.**
​
이 정보를 바탕으로 Handler 객체가 예외를 처리합니다
​
```
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] $throwable")
    }
    val supervisedScope = CoroutineScope(SupervisorJob() + exceptionHandler)
    supervisedScope.apply {
        launch(CoroutineName("Coroutine1")) {
            throw Exception("Coroutine1에 예외 발생!")
        }
        launch(CoroutineName("Coroutine2")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    delay(1000L)
}
```
​
SupervisorJob 객체와 CoroutineExceptionHandler가 설정된 스코프 객체 아래에서 예외가 발생하면 SupervisorJob 객체에는 에러가 전파되지 않습니다.
​
**하지만 예외에 대한 정보는 전달받으므로, 에러를 처리하게 됩니다.**
​
![](https://blog.kakaocdn.net/dn/5PH5q/btsLQOrHqfq/kQ2qHwhpNJXoK1PXAeUN4K/img.png)
![](https://blog.kakaocdn.net/dn/cVaWhI/btsLOUf7PYl/kWTqkoKmQ8dKclR181vAs1/img.png)
​
#### CoroutineExceptionHandler는 예외 전파를 제한하지 않는다
​
CoroutineExceptionHandler는 예외가 마지막으로 처리되는 위치에서 예외를 처리할 뿐, 예외 전파를 제한하지 않습니다.
​
**(저도 try-catch처럼 예외 전파를 제한한다고 생각했습니다.)**
​
```
fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[예외 발생] $throwable")
    }
    launch(CoroutineName("Coroutine1")+exceptionHandler) {
        throw Exception("Coroutine1에 예외 발생!")
    }
}
```
​
위 코드에서는 핸들러가 설정된 코루틴1에 예외가 발생합니다. 하지만 코루틴1에서 발생한 예외는 runBlocking으로 전파되고, 종료됩니다.
​
즉, CoroutineExceptionHandler는 예외 전파를 제한하지 않습니다.
​
![](https://blog.kakaocdn.net/dn/dioXt4/btsLP5t722m/epita2YzjTqZVX97EuZSjK/img.png)
​
### try catch 문을 사용한 예외 처리
​
#### try catch 문을 사용해 코루틴 예외 처리하기
​
코루틴에서 예외가 발생했을 때 코틀린에서 try-catch 문을 통해 예외를 처리할 수 있다.
​
```
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        try {
            throw Exception("Coroutine1에 예외가 발생했습니다!")
        } catch (e: Exception) {
            println(e.message)
        }
    }
    launch(CoroutineName("Coroutine2")){
        delay(100L)
        println("Coroutine2 실행 완료")
    }
}
```
​
코루틴1에서 예외가 발생하지만, try-catch 문을 통해 처리되고 있기 때문에 runBlocking 코루틴으로 예외가 전파되지 않습니다.
​
따라서 코드 실행 결과를 보면
​
![](https://blog.kakaocdn.net/dn/cgDRQj/btsLQrRgBhi/Mgn5rk3Hh6uMD7kbiMckHK/img.png)
​
예외 메시지가 출력되는 것을 확인할 수 있습니다.
​
•흔히들 try-catch 문을 코루틴 빌더 함수에 사용하는 실수를 하는 경우가 있습니다.
​
이 경우 코루틴에서 발생한 예외가 잡히지 않는데요, 왜 그런지 코드를 통해 살펴보겠습니다.
​
```
fun main() = runBlocking<Unit> {
    try {
        launch(CoroutineName("Coroutine1")) {
            throw Exception("Coroutine1에 예외가 발생했습니다!")
        }
    } catch (e: Exception) {
        println(e.message)
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("Coroutine2 실행 완료")
    }
}
```
​
전의 코드와 위 코드의 **다른 점은 launch 함수가 try-catch 내부에 있느냐, 밖에 있느냐입니다.**
​
**launch는 코루틴을 생성하는 데 사용되는 함수일 뿐이므로 람다식의 실행을 처리하진 않습니다.**
​
**람다식의 실행은 코투린이 스레드로 분배되는 시점에 일어나기 때문에 try-catch 딴에서는 처리할 대상이 아닙니다.**
​
따라서 코드를 실행해 보면 예외가 전파되며, 프로세스가 비정상 종료되는 것을 확인할 수 있습니다.
​
![](https://blog.kakaocdn.net/dn/GoRX8/btsLQGtSAw7/h2kYDSYL3LtCn4WUjnGqA1/img.png)
​
**여기서 중요한 점은 코루틴 빌더 함수에 try-catch 문이 아닌, 람다식 내부에서 try-catch를 사용해 에러를 처리해야 한다는 점입니다.**
​
### async의 예외 처리
​
#### async의 예외 노출
​
async는 다른 코루틴 빌더 함수와 다르게 **결괏값을 Deferred 객체로 감싸고 await 호출 시점에 결과값을 노출합니다.**
​
이런 특성 때문에 코루틴 실행 중 예외가 발생해 **결괏값이 없다면 await 호출 시 예외가 노출됩니다.**
​
```
fun main() = runBlocking<Unit> {
    supervisorScope {
        val deferred = async(CoroutineName("Coroutine1")) {
            throw Exception("Coroutine1에 예외가 발생했습니다")
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("[노출된 예외] ${e.message}")
        }
    }
}
```
​
위 코드에서는 supervisorScope를 사용해 예외 전파를 방지하며, 내부에서 코루틴1이 async 함수에 의해 실행됩니다.
​
코루틴1에서 예외가 발생하므로 deferred에 대해 await 함수를 호출하면 예외가 외부로 노출되는데 이 처리를 위해 **try-catch 문으로 await 호출부를 감쌉니다.**
​
실행 결과 에러가 try-catch에 의해 처리되는 것을 볼 수 있습니다.
​
![](https://blog.kakaocdn.net/dn/vl7po/btsLQEJBRh0/YlwDpt9f1PG2OoxYA8Nr1k/img.png)
​
**즉 async 코루틴 빌더를 호출해 만들어진 코루틴에서 예외가 발생할 경우 await 호출부에서 예외를 처리해야 합니다.**
​
async 함수 사용 시 많이 하는 실수 중 하나는 await 호출부에서만 예외를 처리하는 것입니다.
​
빌더 함수로 예외가 발생하면 코루틴으로 예외를 전파하는데, 이를 적절하게 처리해야 합니다.
```
fun main() = runBlocking<Unit> {
    async(CoroutineName("Coroutine1")) {
        throw Exception("Coroutine1에 예외가 발생했습니다")
    }
    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println("[${Thread.currentThread().name} 코루틴 실행")
    }
}
```
​
위 코드에서는 runBlocking의 자식 코루틴으로 코루틴1과 2가 만들어지며 async를 사용해 만들어진 코루틴1에서 예외가 발생됩니다.
​
![](https://blog.kakaocdn.net/dn/b2Cwie/btsLOEEJlhU/d67TjpqsyEiMOx9tMybOn1/img.png)
​
await 호출부가 없음에도 예외 로그가 나오는 것을 확인할 수 있습니다.
​
그 이유는 코루틴1에서 발생한 예외가 부모 코루틴으로 전파돼 부모 코루틴을 취소시키기 때문입니다.
​
이를 해결하기 위해서 근본적인 이유인 예외 전파를 방지해야 하는데요, 이를 위해 supervisorScope를 사용해 구현할 수 있습니다.
​
```
fun main() = runBlocking<Unit> {
    supervisorScope {
        async(CoroutineName("Coroutine1")) {
            throw Exception("Coroutine1에 예외가 발생했습니다")
        }
        launch(CoroutineName("Coroutine2")) {
            delay(100L)
            println("[${Thread.currentThread().name} 코루틴 실행")
        }
    }
}
```
​
![](https://blog.kakaocdn.net/dn/w6OrH/btsLQmCyIIM/EEUxCxcvqRsCcJajUpCiW1/img.png)
​
코드의 실행결과를 보면 코루틴1이 예외를 전파하지 않아 코루틴2가 정상 실행되는 것을 확인할 수 있습니다.
​
**이처럼 async 코루틴 빌더를 사용할 때는 전파되는 예외와 await 호출 시 노출되는 예외를 모두 처리해줘야 합니다.**
​
### 전파되지 않는 예외
​
#### 전파되지 않는 CancellationException
​
코루틴은 CancellationException 예외가 발생해도 부모 코루틴으로 전파되지 않습니다.
​
```
fun main() = runBlocking<Unit>(CoroutineName("runBlocking 코루틴")) {
    launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine2")) {
            throw CancellationException()
        }
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
    delay(100L)
    println("[${Thread.currentThread().name}] 코루틴 실행")
}
```
​
따로 코루틴1에서 예외를 처리하는 핸들러를 설정하지 않았음에도 runBlocking 코루틴과 코루틴1이 정상 실행되는 것을 확인할 수 있습니다.
​
![](https://blog.kakaocdn.net/dn/UHrci/btsLQNNdlwG/ytstInRoL5g9ZLekXc5hnK/img.png)
​
**이는 CancellationException의 특징 때문인데요, 해당 에러는 코루틴만 취소 시키고 전파되지 않습니다.**
​
![](https://blog.kakaocdn.net/dn/VlrO1/btsLP1k8JFB/TXLz6BaHkkrQR5Gt37CPF0/img.png)
​
#### 코루틴 취소시 사용되는 JobCancellationException
​
CancellationException은 **코루틴의 취소**에 사용되는 특별한 예외이기 때문에 부모 코루틴으로 전파하지 않는 것입니다.
​
Job 객체에 cancel 함수를 호출하면 CancellationException의 서브 클래스인 JobCancellationException을 발생시켜 코루틴을 취소시킵니다.
​
```
fun main() = runBlocking<Unit>{
    val job = launch {
        delay(1000L)
    }
    job.invokeOnCompletion { e->
        println(e)
    }
    job.cancel()
}
/*
결과
kotlinx.coroutines.JobCancellationException: StandaloneCoroutine was cancelled;
job="coroutine#2":StandaloneCoroutine{Cancelled}@32e6e9c3
/*
```
​
1초간 지속되는 Job을 만들고 invokeOnCompletion 함수를 통해 job에 발생한 예외를 출력하는 콜백을 등록하고, cancel 함수 호출을 통해 어떤 예외인지 살펴보는 코드입니다.
​
코드의 실행 결과를 보면 JobCancellationException이 발생해 코루틴이 취소되는 것을 확인할 수 있습니다.
​
**이처럼 CancellationException은 특정 코루틴만 취소하는 데 사용됩니다.**
​
withTimeOut 사용해 실행 시간 제한하기
​
코루틴 라이브러리는 제한 시간을 두고 작업을 실행할 수 있도록 만드는 withTimeOut 함수를 제공하고 있습니다.
​
![](https://blog.kakaocdn.net/dn/qU3Bw/btsLQNNez5a/4XPO1a5O7naelYRByzfIV0/img.png)
​
withTimeOut 함수는 매개변수로 실행 제한 시간과 실행돼야 할 작업을 가집니다.
​
주어진 시간에 작업이 완료되지 않으면 **TimeOutCancellationException을 발생시키는데, 이는 위에서 언급됐던 CancellationException의 서브 클래스**입니다.
​
따라서 예외가 전파되지 않고, 예외가 발생한 코루틴만 취소시킵니다.
​
```
fun main() = runBlocking<Unit>(CoroutineName("Parent Coroutine")){
    launch(CoroutineName("Child Coroutine")){
        withTimeout(1000L){
            delay(2000L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    delay(2000L)
    println("[${Thread.currentThread().name}] 코루틴 실행")
}
```
​
이 코드에서는 자식 코루틴의 실행 시간을 1초로 제한시키고, 2초가 소요되는 작업을 실행합니다.
​
당연하게도 에러는 발생하고, withTimeOut은 TimeOutCancellationException을 발생시켜 자식 코루틴을 취소시키지만 예외는 전파되지 않고, 부모 코루틴이 정상적으로 실행된 것을 확인할 수 있습니다.
​
> **withTimeOut 함수는 실행 시간이 제한돼야 할 필요가 있는 다양한 작업에 사용되며, 대표적으로 네트워크 호출 실행 시간제한이 있다.**
​
만약 실행 시간 초과 시 취소가 아닌 결과가 반환돼야 하는 경우가 있습니다.
​
이럴 경우 취소가 아닌 null을 반환하는 withTimeOutOrNull 함수를 사용하면 처리할 수 있습니다.
​
```
fun main() = runBlocking<Unit>(CoroutineName("Parent Coroutine")){
    launch(CoroutineName("Child Coroutine")){
        val result = withTimeoutOrNull(1000L){
            delay(2000L)
            return@withTimeoutOrNull
        }
        println(result)
    }
}
// 결과 null
```

## 요약
1.  애플리케이션은 다양한 예외 상황에 노출되며, 예외를 적절히 처리해 애플리케이션의 안정성을 확보할 수 있다.
2.  코루틴은 비동기 작업을 실행할 때 사용되기 때문에 애플리케이션의 안정성을 위해 예외 처리가 필수적이다.
3.  코루틴에서 발생한 예외는 부모 코루틴으로 전파되며, 적절히 처리되지 않으면 최상위 루트 코루틴까지 전파된다.
4.  예외를 전파받은 코루틴이 취소되면 해당 코루틴의 모든 자식 코루틴에 취소가 전파되고, 취소된다.
5.  새로운 루트 Job 객체를 통해 코루틴의 구조화를 깨 코루틴의 예외 전파를 제한할 수 있다.
6.  SupervisorJob 객체는 예외를 전파받지 않는 특수한 Job 객체이며, 사용해 예외 전파를 제한할 수 있다.
7.  SupervisorJob 객체는 예외를 전파받지 않지만, 예외 정보는 전달받는다.
8.  예외가 전파되거나 예외 정보가 전달된 경우 해당 코루틴에서 예외가 처리된 것으로 본다.
9.  CoroutineExceptionHandler 객체는 이미 처리된 예외에 대해서 동작하지 않는다. 즉 마지막으로 예외가 전파되는 위치에 설정되지 않으면 동작하지 않는다.
10.  CoroutineExceptionHandler는 예외 전파를 제한하지 않는다.
11.  코루틴 빌더 함수에 대한 try-catch 문은 코루틴이 실행될 때 발생하는 예외를 잡지 못한다.
12.  CancellationException은 다른 예외와 다르게 부모 코루틴으로 전파되지 않는다.

## 참고

https://product.kyobobook.co.kr/detail/S000212376884
