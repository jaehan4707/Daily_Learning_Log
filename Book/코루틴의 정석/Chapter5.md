# async와 Deferred

## 개요

launch 코루틴 빌더를 통해 생성되는 **코루틴은 기본적으로 작업의 결과를 반환하지 않는다.**

코루틴으로부터 결과를 수신해야 하는 상황이 생길 것이다.
예를 들어 네트워크 통신 이후, 응답을 받아 처리해야 할 경우 네트워크 통신을 실행하는 코루틴으로부터 결과를 수신받아야 한다.

코루틴 라이브러리는 비동기 작업으로부터 결과를 수신해야 하는 경우를 위해 **async 코루틴 빌더**를 통해 코루틴으로부터 **결괏값을 수신**받을 수 있도록 한다.

launch 함수와 다르게 async 함수는 **결과값이 있는 코루틴 객체 Deferred**가 반환되며 해당 객체를 통해 결괏값을 수신할 수 있다.

## async

### async를 사용해 Deffered 만들기

launch와 async는 코루틴 빌더로, **매우 비슷한 동작 구조**를 가진다.

![](https://blog.kakaocdn.net/dn/LoZfs/btsKUjz5uvq/uJnLp80KNJbmRQqqAeyxE1/img.png)

**async 선언부와 launch 선언부**

-   공통점
    -   **CoroutineDispatcher**를 설정할  수 있다.
    -   start 인자로 지연시작을 설정해 코루틴이 지연시작되도록 할 수 있다.
    -   코루틴에서 실행할 코드를 작성하는 block 람다식을 가진다.
-   **차이점**
    -   **launch는 결과값을 직접 반환할 수 없다.**
    -   **async는 코루틴이 결괏값을 직접 반환할  수 있다.**

async는 코루틴에서 결과값을 담아 반환하기 위해 Deferrred <T> 타입의 객체를 반환한다.

Deferred는 Job과 같이 코루틴을 추상화한 객체이지만, 추가적인 확장으로 결괏값을 감싸는 기능이 있고, 해당 결괏값의 타입은 제네릭 타입인 T로 표현된다.

#### await

Deferred 객체는 결과값 **수신의 대기**를 위해 await 함수를 제공한다.

join 함수와 유사하게 해당 **코루틴이 실행 완료될 때까지 일시 중단**하며, 실행 완료되면 **결괏값을 반환하고 호출부의 코루틴을 재개**한다.

```
fun main() = runBlocking {
    val networkDeferred : Deferred<String> = async(Dispatchers.IO) {
        delay(1000L)
        return@async "Network Response"
    }
    val result  = networkDeferred.await()
    println(result)
}
```

![](https://blog.kakaocdn.net/dn/bDPDzI/btsKS2GvQ3W/9lxY2K3kRXiaxFD1wIa170/img.png)

위 코드는 다음과 같이 동작한다.

![](https://blog.kakaocdn.net/dn/bhK7u3/btsKTYQtoVK/aCVPOkAdJv5MekAWw5daKk/img.png)

await 함수를 호출하면 networkDeferred 코루틴이 완료될 때까지 runBlocking 코루틴이 일시 중단된다.  
이후 코루틴으로부터 네트워크 응답이 반환되면 runBlocking 코루틴이 재개되며, result 변수에 결과가 할당된다.

### Deffered

모든 코루틴 빌더는 Job 객체를 생성한다는 것은 앞장에서 다룬 내용이다.  
하지만 async 코루틴 빌더는 Job 객체가 아닌 Deferred 객체를 생성해 반환한다.  
이러면 앞장에서 다룬 내용과 틀리지 않나? 라는 생각이 들 수 있는데, Deferred도 Job 객체이다.  
조금 더 자세히 말하면**Deferred 객체는 Job 객체의 특수한 형태로 몇 가지 기능이 추가된, 서브 타입이다.**

![](https://blog.kakaocdn.net/dn/cKi13P/btsKUxSKE4P/Ke4CtXnbFOKFkWFggtotVK/img.png)

Job 객체의 서브타입이기 때문에, Job 객체의 **모든 함수와 프로퍼티에 접근이 가능**하다.

-   ex) join, cancel, isActive 등등

### 복수의 코루틴으로부터 결과값 수신하기

#### await를 사용해 복수의 코루틴 결괏값 수신

야구장의 티켓을 여러 사이트에서 구매할 수 있다고 가정해 보자.

그런 경우 각 사이트에 등록된 구매자를 조회한 후 병합해, 모든 구매자를 확인해야 할 것이다.

데이터를 로드할 사이트가 2개 있으므로 각 사이트의 서버로부터 등록된 구매자들의 데이터를 가져와 병합한 코드는 아래와 같이 작성할 수 있다.

```
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val aDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("이지은", "김민정")
    }
    val buyPeopleA = aDeferred.await()
    val bDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("유재석", "유지민")
    }
    val buyPeopleB = bDeferred.await()
    println("[${getElapsedTime(startTime)}] 참여자 목록 : ${listOf(buyPeopleA,buyPeopleB)}")
}
```

![](https://blog.kakaocdn.net/dn/cgKFKe/btsKTWSGZw4/cdqareBS9dwvlVLVTzZPg0/img.png)

위 코드는 다음과 같은 과정으로 동작한다.

1.  startTime 시작 시간 기록
2.  aDeferred를 통해 A 구매자 데이터 로드
3.  A의 서버로부터 결과가 수신될 때까지 대기
4.  bDeferred를 통해 B 구매자 데이터 로드
5.  B의 서버로부터 결과가 수신될 때까지 대기
6.  모든 작업을 마친 후, 걸린 시간과 참여자 목록을 병합해 출력

결과가 정상적으로 나온 것에 주목하지 말고, **시간이 2초 걸렸다는 것에 주목을 해야 한다**.

A 데이터 로드와 B 데이터 로드는 **순차적인 작업이 아니므로, 동시에 진행되는 것이 효율적인 작업**일 것이다.

하지만 **해당 코드는 동시에 진행되는 것이 아니라, 순차적으로 진행**되기 때문에 2초가 걸렸다.

![](https://blog.kakaocdn.net/dn/bXgDvv/btsKSQ67QOv/cckIjzPsFKDuHDZZFSd88K/img.png)

왜 순차적으로 진행이 되었을까에 집중해 보자.

그 이유는 **await를 호출해 결괏값이 반환될 때까지 코루틴을 일시 중단**시켰기 때문이다.

따라서 A의 결과를 로드할 때까지 대기가 B 작업 요청보다 먼저 실행되었기 때문에

**A 요청 -> A 대기 -> B요청 -> B대기의 작업순서**가 결정되었다.

그렇다면 두 작업을 동시에 처리할 수 있는 방법은 무엇일까?

위에 말했듯이 **await의 호출을 뒤로 보내면 된다.**

```
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val aDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("이지은", "김민정")
    }
    val bDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("유재석", "유지민")
    }
    val buyPeopleA = aDeferred.await()
    val buyPeopleB = bDeferred.await()
    println("[${getElapsedTime(startTime)}] 참여자 목록 : ${listOf(buyPeopleA,buyPeopleB)}")
}
```

![](https://blog.kakaocdn.net/dn/VITLD/btsKTHVKxL9/alipr1DmDKQMH2YXwoKNG1/img.png)

위 코드에서는 aDeferred.await가 호출되기 전에 bDeferred 코루틴이 실행되므로 두 코루틴이 동시에 실행된다.

![](https://blog.kakaocdn.net/dn/Xx1Kp/btsKS7Of9bQ/x6a6uCKOC2KLMMFAcRmxsk/img.png)

1.  aDeferred.await()를 호출하면 코루틴이 일시 중단된다.
2.  A 작업의 결과를 반환받으면 코루틴이 다시 시작되고, bDeferred.await가 호출되고, 다시 일시 중단 된다.
3.  B 작업의 결과를 반환받으면 코루틴은 다시 시작되고, 결과를 병합한다.

이때 aDeferred와 bDeferred 코루틴이 **동시에 실행**되기 때문에 결과를 수신할 때까지 1초 정도만 소요됨을 확인할 수 있는 것이다.

이렇게 각 코루틴이 동시에 실행될 수 있도록 만드는 것이 코루틴 성능 측면에서 매우 중요하다.  
위 예시는 단편적인 예시로 delay를 1초로 걸었지만, 만약 1분, 5분과 같은 걸리는 **작업에선 매우 치명적인 결함이다.**

#### awaitAll을 사용한 결괏값 수신

만약 N개의 사이트가 있다고 가정할 때, 결과값 수신 대기를 위해서 **N개의 await를 작성하기에는 정말 비효율적**일 것이다.

**join도 joinAll이 있듯이, await도 awaitAll이라는 복수의 결괏값을 수신하기 위한 함수를 제공**하고 있다.

![](https://blog.kakaocdn.net/dn/o8Jyo/btsKTyrjBZg/MOWCIKU4kLyvvJ50Mrm7l1/img.png)

awaitAll 함수는 가변인자로 Deferred <T> 타입의 객체를 받아, 해당 객체의 결과가 수신될 때까지 호출부의 코루틴을 일시 중단하고, 결괏값이 수신되면 List로 만들어 반환한다.

```
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val aDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("이지은", "김민정")
    }
    val bDeferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("유재석", "유지민")
    }
    val result = awaitAll(aDeferred,bDeferred)
    println("[${getElapsedTime(startTime)}] 참여자 목록 : $result}")
}
```

![](https://blog.kakaocdn.net/dn/bCvC9Z/btsKTrsfAcT/ZVKyboLfpVgIh7ATKvwZZ1/img.png)

위 코드는 아래와 같이 동작한다.

![](https://blog.kakaocdn.net/dn/b3ORLr/btsKUgwWj6B/k2eVq7cvR95XWBxVzJ1nn1/img.png)

1.  runBlocking 코루틴에서 awaitAll 함수가 호출되면, aDeferred, bDeferred 코투린들의 실행이 모두 완료될 때까지 runBlocking 코루틴을 일시 중단한다.
2.  두 코루틴의 실행이 완료되면 결과가 반환되고, 중단된 코루틴이 재개된다.

### WithContext

코루틴 라이브러리에서 제공되는 **withContext 함수를 사용하면 async-await 작업을 대체할 수 있다.**

![](https://blog.kakaocdn.net/dn/NR0Zw/btsKVwlbomM/6iS2ak0Dxt1CgL8UmxYiR0/img.png)

withContext가 호출되면 함수의 인자로 설정된 CoroutineContext 객체를 사용해 block 람다식을 실행하고, 완료되면 결과를 반환한다.

어떻게 async-await를 대체할 수 있을까? 

1.  withContext 함수를 호출한 코루틴은 인자로 받은 CoroutineContext 객체를 사용해 block 람다식을 실행한다.
2.  block 람다식을 모두 실행하면 다시 기존의 CoroutineContext 객체를 사용해 코루틴이 재개된다.

이러한 동작은 **async-await를 연속적으로 실행했을 때와 매우 비슷하다.**

우선 async-await를 활용한 코드를 살펴보자.

```
fun main() = runBlocking {
    val deferred: Deferred<List<String>> = async(Dispatchers.IO) {
        delay(1000L)
        return@async listOf("이지은", "김민정")
    }
    val result = deferred.await()
    println(result)
}
```

![](https://blog.kakaocdn.net/dn/dhYMjE/btsKT6HMRR2/0rmlBOZLKGCjaQpWa8Lwu0/img.png)

위 코드에서는 async 함수를 호출해 Deferred 객체를 만들고, await 함수를 호출한다.

이처럼 async -> await의 구조는 결괏값 수신을 대기하는 코드이고, 이를 withContext로 대체할 수 있다.

```
fun main() = runBlocking {
    val result = withContext(Dispatchers.IO){
        delay(1000L)
        listOf("이지은","김민정")
    }
    println(result)
}
```

![](https://blog.kakaocdn.net/dn/dv0K3I/btsKTrFMwuu/lM89A97Y7wcCxKJ7Ngk750/img.png)

async-await 쌍이 withContext 함수로 대체되면 중간에 **Deferred 객체가 생성되는 부분이 없어지고, 결과가 바로 반환된다.**

async-await에 비해 정말 깔끔한 코드이지만, **특정 상황에서 의도치 않게 동작할 수 있다.**

#### withContext의 동작 방식

두 함수(withContext, async-await)는 겉보기에 비슷하게 동작하지만, **내부적으로는 다르게 동작**한다.

-   async-await는 **새로운 코루틴을 생성해 작업을 처리**
-   withContext는 실**행 중이던 코루틴을 유지**한 채 코루틴의 **실행 환경만 변경해 작업을 처리**한다.

```
fun main() = runBlocking {
    println("[${Thread.currentThread().name}] runBlocking 블록 실행")
    withContext(Dispatchers.IO){
        println("[${Thread.currentThread().name}] withContext 블록 실행")
    }
}
```

![](https://blog.kakaocdn.net/dn/S28fI/btsKUC0G1Qi/c7fZVFko6KaLGj6fAGssB0/img.png)

코드의 실행 결과를 보면 **runBlocking** 함수의 block 람다식을 실행하는 **스레드**와 **withContext** 함수의 block 람다식을 실행하는 **스레드는 다르지만**, **코루틴은 같은 것**을 볼 수 있다.

즉, **withContext 함수는 새로운 코루틴을 만드는 대신 기존의 코루틴에서 CoroutineContext 객체만 바꿔서 실행된다.**

내부 동작 방식을 조금 더 자세히 알아보면 다음과 같다.

-   withContext 함수가 호출되면 실행 중인 코루틴의 실행 환경이 withContext 함수의 context 인자 값으로 변경돼 실행되며, 이를 컨**텍스트 스위칭**이라고 부른다.
-   만약 context 인자로 CoroutineDispatcher  객체가 넘어온다면 코루틴은 해당 객체를 사용해 실행된다.
-   따라서 위 코드에서 withContext(Dispatcher.sIO)가 호출되면 해당 코루틴은 다시 Dispatchers.IO의 작업 대기열로 이도한 후, Dispatchers.IO가 사용할 수 있는 스레드 중 하나로 보내져 실행된다.

![](https://blog.kakaocdn.net/dn/PO1SP/btsKTESwNzG/RT213Nw38XJUvuQKZ92SYk/img.png)

이처럼 withContext 함수는 함수의 **block 람다식이 실행되는 동안 코루틴의 실행 환경을 변경**시킨다.

async-await 방식의 내부 동작을 코드를 통해 살펴보자.

```
fun main() = runBlocking {
    println("[${Thread.currentThread().name}] runBlocking 블록 실행")
    async(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] async 블록 실행")
    }.await()
}
```

![](https://blog.kakaocdn.net/dn/xXWTX/btsKVxYWcTB/WYD9KxQDyOiKWcp5cTJNsK/img.png)

코드의 실해 결과를 보면 async 블록을 실행하는 **코루틴은** runBlocking **코루틴과** **다른** 것을 볼 수 있다.

즉 async-await를 사용하면 **새로운 코루틴을 만들지만 await 함수가 호출돼 순차 처리가 돼 동기적으로 실행되는 것이다.**

![](https://blog.kakaocdn.net/dn/cJhOAi/btsKUfSze8M/crI9MXHIwfkkilZgdYcbwK/img.png)

정리하면,

**withContext를 호출하면 코루틴이 유지된 채로 코루틴을 실행하는 스레드만 변경되기 때문에 동기적으로 실행되는 것이고,**

**async-await를 사용하면 새로운 코루틴을 만들지만 await를 통해 순차 처리가 돼 동기적으로 실행되는 것이다.**

이렇게 새로운 코루틴을 만드는 것과 만들지 않는 것에 대한 차이로 발생하는 주의점이 있다.

#### 복수의 독립적인 작업이 병렬적으로 실행돼야 하는 상황

withContext 함수는 새로운 코루틴을 만들지 않기 때문에 하나의 코루틴에서 withContext 함수가 여러 번 호출되면 순차적으로 실행된다. 즉, **복수의 독립적인 작업에서 성능 문제가 발생할 수 있다.**

```
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val str1 = withContext(Dispatchers.IO) {
        delay(1000L)
        "My Name is"
    }
    val str2 = withContext(Dispatchers.IO) {
        delay(1000L)
        "jaehan"
    }
    println(
        "[${getElapsedTime(startTime)}] $str1 $str2"
    )
}
```

![](https://blog.kakaocdn.net/dn/vJzmp/btsKVc1UgV9/cnpABGAFvutErXtVFk7hDK/img.png)

위 코드는 1초간 대기 후 문자열을 반환하는 2개의 작업을 실행한다.

각 작업은 withContext를 통해 백그라운드 스레드에서 **병렬적으로 실행**되는 것처럼 보이지만 **실제로는 순차적으로 실행**된다.

따라서 **코드의 실행 결과로 2초가 소요됨**을 확인할 수 있다.

![](https://blog.kakaocdn.net/dn/dIP2Ql/btsKVDkqM9r/3WUQRV9Bo0SmaKuefZWtO0/img.png)

이 코드에서는 **runBlocking 코루틴 하나만 생성**된다. 

처음에는 메인 스레드에서 실행되는데, **withContext를 사용하면 코루틴을 유지한 채 실행 스레드**만 변경된다.

즉, 각 withContext 블록의 코드를 실행하는데 1초가 걸리지만 순차적으로 처리돼 2초의 시간이 걸리게 된다.

**이는 withContext 함수가 새로운 코루틴을 생성하지 않기 때문에 생기는 문제이다.**

이 문제는 새로운 코루틴을 만드는 async-await를 통해 해결할 수 있다.

```
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val str1 = async(Dispatchers.IO) {
        delay(1000L)
        "My Name is"
    }
    val str2 = async(Dispatchers.IO) {
        delay(1000L)
        "jaehan"
    }

    val result = awaitAll(str1, str2)
    println("[${getElapsedTime(startTime)}] $result")
}
```

![](https://blog.kakaocdn.net/dn/cHbcq3/btsKVaQwfd8/9FfZwjc2zKMCBw1k32jJV0/img.png)

위 코드에서는 두 작업 모두 실행 뒤 **awaitAll** 함수가 호출됐다.

따라서 2개의 코루틴이 **병렬적으로 실행**돼 코드를 실행하는 데 **1초가 소요됨**을 확인할 수 있다.

![](https://blog.kakaocdn.net/dn/oyiKw/btsKU7TGZlE/vZGHK3fFIP3sPQlaXUrsN1/img.png)

withContext 함수를 사용한 코드가 깔끔해 보이긴 하지만, **잘못 사용할 경우 코루틴을 동기적으로 실행하도록 만들어, 실행 시간이 배로 증가할 수 있다.** 

## 요약

-   async 함수를 사용해 코루틴을 실행하면 코루틴의 결과를 감싸는 Deferred 객체를 반환받는다.
-   Deferred는 Job의 서브타입으로, 결괏값을 반환하는 기능이 추가된 객체이다.
-   Deferred는 await 함수를 통해 결과값을 반환할 때까지 코루틴을 일시 중단시킬 수 있다.
-   awaitAll 함수를 사용해 복수의 Deferred 코루틴의 결과값을 수신할 수 있다.
-   withContext 함수는 async-await를 대체할 수 있다.
-   withContext는 코루틴을 새로 생성하지 않는다.  
    코루틴의 실행 환경을 변경해 코루틴을 실행하므로, 이를 활용해 코루틴이 실행되는 스레드를 변경할 수 있다.
-   withContext로 인해 실행 환경이 변경돼 실행되는 코루틴은 작업을 모두 실행하면 다시 이전의 실행 환경으로 돌아온다.

## 참고

[https://product.kyobobook.co.kr/detail/S000212376884](https://product.kyobobook.co.kr/detail/S000212376884)