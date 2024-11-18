## 단일 스레드 애플리케이션의 한계

**스레드는 하나의 작업을 수행할 때 다른 작업을 동시에 수행하지 못한다.**

메인 스레드 또한 예외가 아니어서 메인 스레드에서 실행하는 작업이 처리되는 동안 다른 작업 수행 X

—> 메인 스레드 하나만 사용하는 애플리케이션은 하나의 작업이 오래 걸리면 다른 작업을 전혀 할 수 없다.

—> 이에 따른 응답성에 문제가 생길 수 있다.

![](https://blog.kakaocdn.net/dn/AODca/btsKNvVfFfI/g9Q8VM8EmKptL7GSdgJO71/img.png)

휴대폰에서 애플리케이션을 메인 스레드만 사용해 만 들 경 우, 메인 스레드에서 UI 상호작용을 처리하는 작업이 반복된다.

여기서 네트워크 요청을 하고 응답을 대기하거나 복잡한 연산 작업을 수행하는 오래 걸리는 작업을 하고 있다면, 애플리케이션은 UI를 그리는 작업을 멈추고, 사용자 입력 또한 제대로 전달받지 못하게 된다.

**이는 안드로이드 휴대폰이 버벅이는 주요 이유가 될 수 있다.**

![](https://blog.kakaocdn.net/dn/W5qrD/btsKMDNrYvZ/bTAAENxBy1blkFfF7Y40Zk/img.png)

서버도 동일한 예가 있는데, 클라 → 서버로 오래 걸리는 작업 요청이 들어올 경우 단일 스레드만 사용할 경우 응답속도가 늦어진다.

**순차적인 처리는 당연하게도 응답속도와 처리속도의 지연을 수반한다.**

단일 스레드만 사용해 작업하면 해야 할 작업이 **다른 작업에 의해 방해**받거나 작업 속도가 느려질 수 있다.

## 멀티 스레드 프로그래밍을 통한 단일 스레드의 한계 극복

위에서 언급한것처럼 단일 스레드의 단점은 해야 할 작업이 다른 작업에 의해 방해받는다는 것이다.

이러한 문제점은 멀티 스레드 프로그래밍을 통해 해결할 수 있다.

![](https://blog.kakaocdn.net/dn/COdI1/btsKNxyCy7R/NmPmuLxpVxGkyEFhhNrqD0/img.png)

메인스레드가 할 작업을 별도 스레드가 처리할 수 있도록 만든다.

예를 들어, 메인 스레드에 오래 걸리는 작업이 요청됐을 때, 오래 걸리는 작업을 백그라운드 스레드에서 처리하도록 만들면 메인 스레드는 UI 작업에 집중할 수 있게 된다.

서버도 마찬가지로, 데이터베이스 3개를 여러 백그라운드 스레드에서 조회하고, 결과를 병합해서 반환하면 응답속도를 빠르게 할 수 있다.

## 스레드, 스레드풀을 사용한 멀티 스레드 프로그래밍

한계를 극복하기 위해 다양한 방식으로 멀티 스레드 프로그래밍은 변화했다.

코투린 이전에는, 스레드와 스레드풀을 활용했고, 스레드를 직접 다루는 가장 간단한 방법은 **Thread** 클래스이다.

### Thread 클래스와 한계

통상적으로 오래 걸리는 작업일 경우 별도 스레드에서 실행되도록 Thread 클래스를 상속하는 클래스를 만들어서 사용한다.

사용자 스레드와 데몬 스레드

-   JVM 프로세스는 일반적으로 메인 스레드의 작업이 종료되면 종료된다.
-   메인 스레드는 사용자 스레드이고, 사용자 스레드가 모두 종료되면 프로세스가 종료된다.
-   JVM은 스레드를 사용자 스레드와 데몬 스래드로 구분한다.
    -   **사용자 스레드는 우선도가 높은 스레드이고, 데몬 스레드는 우선도가 낮은 스레드이다.**
-   **JVM이 종료되는 시점은 우선도가 높은 사용자 스레드가 모두 종료될 때이다.**
-   Thread 클래스를 상속해서 만든 Thread 객체는 default 사용자 스레드

**한계 1**

-   Thread 클래스를 상속한 클래스를 인스턴스화해 실행할 때마다 매번 새로운 스레드가 생성된다. 스레드는 생성 비용이 비싸기 때문에 매번 새로운 스레드를 생성하는 것은 성능적으로 좋지 않다.

**한계2**

-   스레드 생성과 관리에 대한 책임이 개발자에게 있다. 따라서 프로그램의 복잡성이 증가하며, 실수로 인해 오류나 메모리 누수(Memory Leak)을 발생시킬 가능성이 있다.

이러한 한계 덕분에, 한 번 생산한 스레드를 재사용할 수 있어야 하고, 관리할 수 있는 시스템이 있어야 한다. 이러한 역할을 Executor 프레임워크가 한다.

## Executor

등장 배경

-   개발자가 스레드를 **직접 관리하는 문제를 해결**하고 생성된 스레드의 **재사용성**을 높이기 위해 등장

#### 쓰레드 풀

Executor은 스레드를 생성하고 관리하는데, **쓰레드 풀**이라는 개념을 사용한다.

스레드풀은 스레드와 풀의 합성어인데, 스레드의 집합이라는 의미이다.

쓰레드 풀을 관리하고 사용자로부터 요청받은 작업을 각 스레드에 할당하는 시스템을 더한 것이 Executor 프레임워크이다.

-   작업 처리를 위해 스레드 풀을 미리 생성하고, 작업을 요청받으면 **쉬고 있는 스레드에 작업을 분배**한다.
-   각 스레드가 작업을 끝내더라도 스레드를 종료하지 않고 다음 작업이 들어오면 **재사용**한다.

이러한 프레임워크가 주는 장점은 스레드에 대한 생성, 관리, 작은 분배와 같은 디테일한 책임을 프레임워크가 담당하게 되므로, 개발자가 더 이상 신경 쓰지 않아도 된다는 점이다.

개발자는 단순하게, 스레드 풀에 할당할 수 있는 스레드의 개수와 작업을 설정해 주면 된다.

Executor 프레임워크에서 사용자가 사용할 수 있는 함수는 크게 2가지이다.

1.  스레드풀을 생성하고 생성된 스레드풀을 관리하는 객체 반환
2.  스레드풀을 관리하는 객체에 작업을 제출

```
val executorService : ExecutorService = Executors.newFixedThreadPool(2)
// 두개의 스레드를 가진 스레드풀을 생성하고, 관리하는 객체
```

submit 함수를 통해 스레드풀에 작업을 제출할 수 있다.

```
fun main() {
  val startTime = System.currentTimeMillis()
  val executorService: ExecutorService = Executors.newFixedThreadPool(2)

  // 작업1
  executorService.submit {
      println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
      작업1 시작!")
      Thread.sleep(1000L)
      println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
      작업1 완료!")
  }
  // 작업2
  executorService.submit {
      println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
      작업2 시작!")
      Thread.sleep(1000L)
      println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
      작업2 완료!")
  }

  executorService.shutdown()
}

fun getElapsedTime(startTime: Long): String = 
	"${System.currentTimeMillis() - startTime}ms"
```

![](https://blog.kakaocdn.net/dn/bbNv82/btsKOypU9du/y5Bx3SAWqD5n3OpkPIdQVK/img.png)

executorService, 즉 스레드 풀의 스레드 최대 개수가 2개이므로, 작업 1과 작업 2는 **병렬적으로 수행**되어 , 동시에 끝난다.

하지만 만약 스레드 풀의 최대개수보다 많은 작업이 동시에 할당된다면 어떻게 될까?

당연하게도, 스레드 풀의 작업이 끝나고 가용 가능한 스레드가 생기면 작업이 진행될 것이다.

```
fun main() {
    val startTime = System.currentTimeMillis()
    val executorService: ExecutorService = Executors.newFixedThreadPool(2)

    // 작업1
    executorService.submit {
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업1 시작!")
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업1 완료!")
    }
    // 작업2
    executorService.submit {
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업2 시작!")
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업2 완료!")
    }

    // 작업3
    executorService.submit {
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업3 시작!")
        Thread.sleep(1000L)
        println("[${Thread.currentThread().name}][${getElapsedTime(startTime)}] 
        작업3 완료!")
    }

    executorService.shutdown()
}

fun getElapsedTime(startTime: Long): String = 
	"${System.currentTimeMillis() - startTime}ms"
```

![](https://blog.kakaocdn.net/dn/bPP5VH/btsKN5ocdbq/YzjobBxIHtdqJCB090iCZk/img.png)

작업 1과 작업 2는 동시에 실행되지만, 작업 3은 작업 1, 2가 완료된 후에 실행되는 것을 볼 수 있다.

이렇게 동작하는 이유는 위에서 예측했던 것처럼 2개의 스레드가 이미 작업을 처리 중이기 때문에 **가용가능한 스레드가 없어서 그렇다.**

## 내부 구조와 동작

![](https://blog.kakaocdn.net/dn/kEl2x/btsKN5BI5UN/lyRDaTyEYSy88GkzWU89nk/img.png)

크게 부분으로 나뉘는데

-   할당받은 작업을 적재하는 **작업 대기열(Blocking Queue)**
-   작업을 수행하는 스레드의 집합인 **스레드 풀**

**Executor Service 객체는 사용자로부터 요청받은 작업을 대기열에 적재한 후 쉬고 있는 스레드에 할당하는 역할을 한다.**

### 의의와 한계

프레임워크에게 책임을 맡기고, 개발자가 스레드를 관리하지 않는다는 점에서 혁신적인 프레임워크라고 할 수 있다. (단순하게 스레드 풀의 개수를 지정하고, submit으로 작업을 제출만 하면 된다)

하지만 대표적인 문제점이 있는데, 그것이 바로 **스레드 블로킹**이다.

#### **스레드 블로킹**

> 스레드가 아무것도 하지 못하고 사용될 수 없는 상태

스레드는 비싼 자원이기 때문에, 사용될 수 없는 상태에 놓이는 것이 반복되면 애플리케이션의 성능이 떨어지게 된다.

대표적인 상황이, 제출한 작업에서 결과를 전달받을 때까지 무한 대기하는 상황이다.

Future 객체와 get을 통해서 Executor 객체는 결괏값이 **반환될 때까지 스레드를 블로킹**한다.

→ **이는 성능상 심각한 문제를 초래할 수 있다.**

대표적인 블로킹 함수가 Thread의 Sleep이다. 일정 시간 대기해야 할 때 매우 유용한 함수이지만, 스레드를 블로킹시킨다는 단점이 있다.

```
fun main() {
    val startTime = System.currentTimeMillis()
    println("[${getElapsedTime(startTime)}] 메인 스레드 시작!")
    Thread.sleep(1000L)
    println("[${getElapsedTime(startTime)}] 메인 스레드 완료!")
}

fun getElapsedTime(startTime: Long): String = 
	"${System.currentTimeMillis() - startTime}ms"
```

![](https://blog.kakaocdn.net/dn/lkhJl/btsKMD0YATk/CFFMnu2tEMdvYmlkvw1Pn1/img.png)

## 기존 멀티 스레드 프로그래밍의 한계

스레드는 생성 비용과 작업을 전환하는 비용이 비싸다. 만약 스레드가 아무 작업을 하지 못하고 기다릴 경우 컴퓨터의 자원이 낭비된다.

![](https://blog.kakaocdn.net/dn/oomQd/btsKOKX3m9V/dpdpw3zj2RNHNEkIUJcgHK/img.png)

위 그림은 Thread0이, 작업 1 수행 중에 작업을 마저 수행하려면 Thread-1 스레드에서 실행되는 작업 2의 결과물이 필요한 상황이다. 정말 많은 상황인데, 네트워크 요청을 한 후에 응답을 기다려야 하거나, 복잡한 연산의 결괏값을 반환받을때까지 대기하는 상황에서 발생한다.

이러한 상황에서 Thread0은 Thread1의 작업이 완료될 때 까지 **아무것도 하지 못하고 대기**한다. 그 시간만큼 Thread0은 사용할 수 없게 되고, 스레드의 비용을 생각하면 매우 치명적인 영향이다.

피할 수 있는 방법은 다양하지만(콜백, 체이닝 함수) 작업이 많아지고, 작업 간의 종속성이 생긴다는 점에서 그 자체로 한계가 있다.

## 그렇다면 코루틴은 어떻게? 이러한 한계를 극복할까?

코루틴은 **작업 단위 코루틴을** 통해 스레드 블로킹 문제를 해결한다.

작업 단위 코루틴은 작업 실행 도중 일시 중단할 수 있는 작업 단위이다. 코루틴은 작업이 일시 중단되면 더 이상 스레드 사용이 필요하지 않으므로 스레드의 사용 권한을 양보하며, 양보된 스레드는 다른 작업을 실행하는 데 사용할 수 있다.

코루틴이 **경량화된 스레드**라고 불리는 이유가 여기에 있다. 개발자가 코루틴을 만들어 코루틴 스케줄러에 넘기면 코루틴 스케줄러는 자신이 사용할 수 있는 스레드나 스레드 풀에 해당 코루틴을 분배해 작업을 수행한다. 스레드를 사용하던 중에 필요 없게 되면 해당 스레드를 다른 코루틴이 쓸 수 있게 양보할 수 있어서 스레드 블로킹이 일어나지 않게 되는 것이다.

![](https://blog.kakaocdn.net/dn/b9dpA0/btsKNV7evdA/rAkXkK767W20OHK0In99bk/img.png)

기존의 멀티프로그래밍은 Thread0이 작업 3을 수행하기 위해서는 작업 1이 끝나고 수행할 수 있다.

작업1이 끝날 때까지 Thread0을 점유하고 있기 때문이다.

**코루틴은 자신이 스레드를 사용하지 않을 때 스레드 사용 권한을 반납한다.**

스레드 사용 권한을 반납하면 해당 스레드에서는 다른 코루틴이 실행될 수 있고, 작업 1을 일시 중단하고, 작업3이 실행된다. 이후 작업2가 완료되면 다시 Thread0이 작업1을 할당받아 작업을 시작한다.

-   작업 단위로서의 코루틴이 스레드를 사용하지 않을 때, **사용 권한을 양보**하는 방식으로 **스레드 사용을 최적화**하고 스레드가 **블로킹**되는 상황을 **방지**한다.
-   **스레드에 비해 생성과 전환에 비용이 적게 들기 때문에, 경제적**이다.

# 요약

-   JVM 상에서 실행되는 코틀린 애플리케이션은 실행 시 메인 스레드를 생성하고 메인 스레드를 사용해 코드를 실행한다.
-   단일 스레드 애플리케이션은 한 번에 하나의 작업만 수행할 수 있으며, 복잡한 작업이나 네트워크 요청 등이 있으면 응답성이 떨어질 수 있다.
-   멀티 스레드 프로그래밍을 사용하면 여러 작업을 동시에 실행할 수 있어서 단일 스레드 프로그래밍의 문제를 해결할 수 있다.
-   직접 Thread 클래스를 상속해 스레드를 생성하고 관리할 수 있으나, 생성된 스레드의 재사용이 어려워 리소스의 낭비를 일으킨다.
-   Executor 프레임워크는 스레드 풀을 사용해 스레드의 생성과 관리를 최적화하고 스레드 재사용을 용이하게 했다.
-   Executor 프레임워크를 비롯한 기존의 멀티 스레드 프로그래밍 방식들은 스레드 블로킹 문제를 근본적으로 해결할 수 없다.
-   스레드 블로킹은 스레드가 작업을 기다리면서 리소스를 소비하지만 아무 일도 하지 않는 상태를 말한다.
-   코루틴은 스레드 블로킹 문제 해결을 위해 등장했다. 코루틴은 필요할 때 스레드 사용 권한을 양보하고 일시 중단하며, 다른 작업이 스레드를 사용할 수 있게 한다.
-   일시 중단 후 재개된 코루틴은 재개 시점에 사용할 수 있는 스레드에 할당돼 실행된다.
-   코루틴은 스레드와 비교해 생성과 전환 비용이 적게 들고 스레드에 자유롭게 뗐다 붙였다 할 수 있어 경량화된 스레드라고 불린다.
-   코루틴을 사용하면 스레드 블로킹 없이 비동기적으로 작업을 처리할 수 있으며, 이를 통해 애플리케이션의 응답성을 크게 향상할 수 있다.

## 참고

[https://product.kyobobook.co.kr/detail/S000212376884](https://product.kyobobook.co.kr/detail/S000212376884)

[코틀린 코루틴의 정석 | 조세영 - 교보문고

코틀린 코루틴의 정석 | 많은 개발자들이 어렵게 느끼는 비동기 프로그래밍을 다양한 시각적 자료와 이해하기 쉬운 설명을 통해 누구나 쉽게 이해할 수 있도록 설명한다. 안드로이드, 스프링 등

product.kyobobook.co.kr](https://product.kyobobook.co.kr/detail/S000212376884)