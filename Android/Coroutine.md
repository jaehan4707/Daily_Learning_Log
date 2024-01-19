## 개요

프로젝트에서 코루틴을 많이 사용했지만, 코루틴에 대해서 정확하게 알지 못하고 기존 코드를 그대로 사용한 적이 많았다.

공식문서에서 비동기 작업으로 코루틴을 권장하고, 애플리케이션 특성상

`비동기 작업`이 중요한 만큼 한번 자세히 다룰 필요성이 있다고 느꼈다.

코루틴에 대해서 설명하기전에 비동기 작업이 왜 중요한지, 어떤 것인지 알고 넘어갈 필요성이 있다.

여기선 `동시성`과 `병렬성`이라는 단어의 차이를 알 필요성이 있다.

## 동시성 vs 병렬성

![](https://blog.kakaocdn.net/dn/WabAS/btsDKOzJEe4/I8FCf6AU3UHRqkPgvjlIek/img.png)

### 동시성

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FzTsj5%2FbtsDGsLYQ4d%2FK5ymxyhlIOwUzX3OIFnevk%2Fimg.png)

둘 이상의 작업이 동시에 실행되는것을 의미한다.

여기서 말하는 동시는 정확하게 같은 시간에 실행되는 것이 아닌 `동시에 실행하는 것처럼` 보인다는 의미이다.

예를 들어 N명의 사람이 하나의 큰 작업을 하는데, 동시에 작업을 하는것이 아닌 1명씩 돌아가면서 작업을 하는데,

교대하는 시간이 무척 빨라서 보는사람으로 하여금 동시에 작업하는 것처럼 느껴질 때 이것을 프로그래밍에서 동시성이라고 말한다.

실제로는 한번에 하나의 작업만을 처리한다.

컴퓨터 내에선 여러 스레드가 `Context Switching`을 통해서 교대를 하면서 작업을 처리한다.

(Thread와 Process에 대한 내용은 이번 글에서 자세히 다루지 않고 추후에 다룰 예정이다)

### 병렬성

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fd52Bpi%2FbtsDGXrG4s6%2FuiUYtJz24vjGwyCIubFFI1%2Fimg.png)

여러 작업을 `실제로 동시`에 처리하는 것이다.

컴퓨터를 보면 `멀티 코어`라는 단어를 본 경험이 있을 것이다.

여러 CPU 또는 코어를 사용해서 여러 작업을 병렬로 처리할 수 있는 것이 그 예이다.

대표적인 작업으로는 대용량 데이터 처리나, 수학, 과학 계산분야에서 계산 작업을 여러 개의 코어로 병렬로 처리할 수 있다.

여기서 핵심은 각각의 작업은 별도의 프로세스나 스레드에서 실행된다는 점이다.

이는 여러 개의 CPU 또는 코어가 있기에 가능하다.

즉 요약하자면

-   동시성은 실제로 동시에 일을 처리하는 것이 아니지만, Context Switching을 통해서 하나의 프로세스에서 여러 쓰레드가 작업을 돌아가면서 처리하는것
-   병렬성은 실제로 동시에 일을 처리하고, 별도의 스레드에서 쓰레드가 작업을 동시에 처리하는것

이렇게 병렬성과 동시성의 키워드 차이를 이해하고 코루틴을 공부하면 편할 것이다.

## 우선 Coroutine이란 무엇인가?

> 비동기적으로 실행되는 코드를 간소화하기 위해 Android에서 이용할 수 있는 동시 실행 설계 패턴

코루틴 이전에는 안드로이드 앱에서 비동기 처리를 위해서 `RxJava`를 사용했지만,

공식문서에도 `Coroutine`을 강조하고 있고, 최근에는 RxJava 보다는 Coroutine을 사용하고 있다.

공식문서에서 강조하는 코루틴의 기능 및 장점은 다음과 같다.

### 장점

-   가볍다
    -   코루틴은 실행 중인 스레드를 차단하지 않는 suspend를 지원하므로, 단일 스레드에서 많은 코루틴을 실행할 수 있다.
    -   suspend는 많은 동시성 작업을 지원하면서 blocking 보다 메모리를 절약한다.

왜 코루틴이 스레드를 사용하는 것보다 가벼울까?

위에서 `suspend`와 `blocking`이라는 단어가 등장한다. 이 단어의 차이를 주목하면 알 수 있다.

Blocking은 막다, 차단하다는 의미이다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFZSZI%2FbtsDKNHBBdK%2FSOzcJvm86dafn8xKwUgknK%2Fimg.png)

단일 스레드에서 네트워크 요청(A)과 같이 시간이 오래 걸리는 작업을 수행하다 보면 응답을 얻는데 긴 시간이 필요하다.

이 동안 스레드는 Block상태가 되고, 다음 작업(B)은 네트워크 요청(A)이 끝날 때까지 기다리게 된다.

그래서 일반적으로 하나의 스레드가 아닌 다중 스레드를 이용해서 비동기적으로 작업을 실행한다.

Suspending은 유예, 연기하다는 의미이다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FevirTo%2FbtsDIu3Dht7%2FriA5CZzcg72nh1WPSUu180%2Fimg.png)

함수 A가 시작된 후 정지할 수 있으며, 함수 B가 수행되고 끝난 뒤 함수 A가 다시 시작할 수 있다.

즉 이 스레드는 함수 A에 의해 블록 되지 않는다.

블록 하는 대신 하던 작업을 `정지`시킨다.

그리고 다른 작업이 해당 스레드를 사용할 수 있게 하기에 하나의 쓰레드에서 여러 작업을 수행할 수 있으니

쓰레드를 더 이상 만들 필요가 없다.

이러한 이유로 코루틴을 `경량화된 스레드`라고 표현하기도 한다.

-   메모리 누수 감소
    -   구조화된 동시 실행을 사용하여 scope 내에서 작업을 실행한다.
-   기본적으로 제공되는 취소를 지원한다.
    -   실행 중인 코루틴 계층 구조를 통해 자동으로 취소가 전달된다.
-   Jetpack 통합
    -   많은 Jetpack 라이브러리에서 코루틴을 오나전히 지원하는 확장 프로그램이 포함되어 있고,  
        일부 라이브러리리는 자체 코루틴 범위도 제공하고 있다.

> suspend 함수는 시작, 중단, 재개할 수 있다는 함수를 의미한다.

이렇게 보면 코루틴은 `suspend`라는 키워드를 통해서 동시성을 지원한다는 것을 알 수 있다.

코루틴은 Dispatcher를 통해서 실행에 사용되는 스레드를 확인한다.

코루틴에서 제공하는 Dispatcher는 3가지 종류가 있다.

-   Dispatcher.Main
    -   UI와 상호작용하고, 빠른 작업을 위해서 사용
    -   suspend 함수를 호출하고, UI 프레임 워크 작업을 실행하며 LiveData 객체를 업데이트한다.
-   Dispatcher.IO
    -   Main 스레드 외부에서 네트워크 I/O를 실행하는 것에 최적화되어있다.
    -   ex) Room Component를 사용해 파일에서 읽거나 파일에 쓰고, Retrofit을 통해 네트워크 작업을 실행
-   Dispatcher.Default
    -   CPU를 많이 사용하는 작업을 Main 스레드 외부에서 실행하도록 최적화되어 있다.
    -   ex) List를 정렬하거나, Json을 파싱 하는 작업

이렇게 각각의 작업에 따라 다른 스레드를 사용하는 것이 반드시 필요하다.

네트워크 작업을 하면서 `NetworkOnMainThreadException` 에러를 본 적이 있을 것이다.

이유는 Main Thread에서 네트워킹 작업을 실행했기 때문이다.

위에서도 설명했듯이 Main Thread에서는 `UI` 관련 작업을 해야 하고, 다른 작업에 의해 Main Thread가 블록 되면 해당 오류가 발생한다.

즉 Main Thread가 블록 되지 않는 것이 정말 중요하다.

안드로이드에서 UI는 사용자와 직접적으로 맞닿는 부분이고, 응답성, 사용자 경험과 직결되는 부분이기 때문에 정말 중요하다.

하지만 이러한 작업이 다른 작업(네트워크 요청, 처리) 때문에 방해되면 치명적인 문제가 생긴다.

그렇기 때문에 Main Thread가 항상 UI 역할을 할 수 있도록 보장해 주고, 설계해 주는 것이 중요하다.

이러한 작업을 코루틴을 통해서 보장하는 것이다.

물론 코루틴이 아니라 다른 동시성 프로그래밍을 지원하는 방법을 사용하면 좋지만

아래부터는 코루틴이 왜 적합한지에 대해서 설명할 예정이다.

## 코드로 비교하는 비동기 처리

크게 `Callback`과 `Coroutine`을 비교할 예정이다.

예를 들어서 설명하면 편할 것 같아서 하나의 시나리오를 통해서 설명해 보겠다.

미용실에 가서 파마와 염색을 할 때의 시나리오이다.

파마와 염색은 동시에 진행하면 머리에 치명적인 손상이 가하기 때문에, 파마를 하고 염색을 하는 과정을 거친다.

1.  머리를 자른다.
2.  파마약을 바른다.
3.  머리를 롤로 말고, 열을 가한다.
4.  머리를 감는다.
5.  염색약을 바른다.
6.  염색약을 씻는다.
7.  머리를 말린다.

파마약을 바르기 전에 머리를 롤로 말고, 열을 가하면 아무 효과가 없듯이 이러한 과정은 반드시 순서대로 진행되어야 한다.

코드로 간단하게 작성해 보면 다음과 같을 것이다.

### Callback

```
fun chageMyHair(hair : Hair){
    hairShop.cutHair{ hair -> 
        hairShop.paintPerm(hair){ -> permHair
            hairShop.doPerm(permHair) { -> permHair
                hairShop.washHair(permHair) { -> washHair
                    hairShop.paintColor(washHair){ -> colorHair
                        hairShop.dyeHair(colorHair){ -> dyeHair 
                            hairShop.washHair(dyeHair){ -> washHair
                                    hairShop.dryHair(washHair)->{
                                        return washHair //염색 끝
                                    }      
                             }
                        }
                    }
                }
            }
        }
    }
}
```

간단한 코드의 형태이지만 벌써 지옥을 맛볼 수 있다.

여기서 에러처리까지 한다면 가독성은 개나 줘 버린 코드가 될 것이다.

분명히 위 코드도 동시성을 지원하지만 코틀린을 사용한다면 가독성까지 챙길 수 있다.

### Coroutine

```
suspend fun chageMyHair(hair : Hair) {
    val cutHair = hairShop.cutHair()
    val permHair = hairShop.doPerm()
    val washHair = hairShop.washHair()
    val paintHair = hairShop.paintColor()
    val dyeHair = hairShop.dyeHair()
    val dyeWashHair = hairShop.washHair()
    val dryHair = hairShop.dryHair()
    return dryHair
}
```

들여 쓰기가 사라지고, 람다식을 사용하지 않아서 코드 가독성이 정말 좋아진 것을 확인할 수 있다.

저렇게 코드를 작성하는데 어떻게 동시성을 보장할 수 있을까?

suspend 키워드를 만날 경우 kotlin 컴파일러는 콜백코드를 생성해 주기 때문에, 코루틴은 콜백 없이 동시성을 보장할 수 있는 것이다.

## 느낀 점

```text
이번 글에선 코루틴의 자세한 코드보단 비동기 작업이 왜 중요한지에 대해서 초점을 맞췄고,
비동기 작업에서 왜 코루틴을 권장하는지에 대해서 알아봤다.
콜백패턴도 좋은 코드이고, RxJava도 좋은 방법이지만
코루틴의 매력은 "저걸로 비동기가 보장이 된다고?"라는 점인 것 같다.
그 정도로 가독성이 좋은 매력적인 방법이라고 생각이 든다.
그 외에도 Coroutine Context, Coroutine Builder, Coroutine Scope, Coroutine Context, Job 등 정말 많은 내용이 있고,
코루틴은 정말 방대한 내용이고 공부할 것이 많은 것 같다..
```