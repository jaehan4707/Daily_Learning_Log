## 이전 글

> [\[코루틴의 정석\] 스레드 기반 작업의 한계와 코루틴의 등장(Chapter1)](https://jja2han.tistory.com/501)

## CoroutineDispatcher란 무엇인가?

디스패처는 dispatch와 -er의 합성어로 dispatch의 보내다는 뜻에 -er이 붙어 무언가를 보내는 주체라는 뜻이다.

즉 **CoroutineDispatcher는 코루틴을 보내는 주체**라는 뜻이다.

CoroutineDispatcher 객체는 스레드로 코루틴을 보낸다.

코루틴은 **일시 중단이 가능한 작업**이기 때문에 스레드가 있어야 실행될 수 있으며, CoroutineDispatcher는 코루틴을 스레드로 보내 실행시키는 역할을 한다.

CoroutineDispatcher는 코루틴을 스레드로 보내는 데 사용할 수 있는 스레드나 스레드풀을 가지며, 코루틴을 실행 요청한 스레드에서 코루틴이 실행되도록 만들 수 있다.

### CoroutineDispatcher의 내부 동작

![](https://blog.kakaocdn.net/dn/dlZZyy/btsKQyj4i4K/XIj6StAEenxFASafVPPhjk/img.png)

CoroutineDispatcher 객체는 실행돼야 하는 작업을 저장하는 **작업 대기열**을 가지며, CoroutineDispatcher 객체가 사용할 수 있는 스레드풀에는 thread-1, Thread-2라는 2개의 스레드가 포함된다.

이런 CoroutineDispatcher 객체에 Coroutine2 코루틴의 실행이 요청되면 CoroutineDispatcher 객체는 **실행 요청받은 코루틴을 작업 대기열**에 적재한다.

![](https://blog.kakaocdn.net/dn/betfDo/btsKQC7x0Fk/XafrNkISHJgtGN8VFShlg0/img.png)

다음, CoroutineDispatcher 객체는 자신이 사용할 수 있는 **스레드가 있는지 확인**하고, 해당 스레드로 코루틴을 보내 실행시킨다.

![](https://blog.kakaocdn.net/dn/bLqejG/btsKQ9KPBAK/3GHPBtnrDLyvOSJGdVodpK/img.png)

그렇다면 사용할 수 있는 스레드를 코루틴이 모두 점유하고 있는 경우는 어떻게 동작할까?

**스레드풀과 동일하게, 사용가능한 스레드를 확인하고 없다면 작업대기열에 대기할 것이다.**

![](https://blog.kakaocdn.net/dn/dNnhu9/btsKP9SeOYj/NmVTKsbh0IK9VaJyMM9Dv0/img.png)

그러다가 코루틴이 스레드로 보내지는 시점은 스레드풀의 스레드 중 하나가 자유로워졌을 때이다.

![](https://blog.kakaocdn.net/dn/1YTfo/btsKO7HPdyW/JBxJZbfVa8QisP6Kzr2Nv0/img.png)

**즉 CoroutineDispatcher 객체는 자신에게 실행 요청된 코루틴을 우선 대기열에 적재한 후 사용할 수 있는 스레드가 생기면 스레드로 보내는 방식으로 시작한다.**

#### **CoroutineDispatcher의 역할**

```
CoroutineDispatcher는 코루틴의 실행을 관리하는 주체로 자신에게 실행 요청된 코루틴을
작업 대기열에 적재하고, 자신이 사용할 수 잇는 스레드가 새로운 작업을 실행할 수 있는 상태라면
스레드로 코루틴을 보내 실행될 수 있게 만드는 역할을 한다.
```

### 제한된 디스패처와 무제한 디스패처

CoroutineDispatcher에는 두 가지 종류가 있다.

1.  제한된 디스패처 : 사용할 수 있는 스레드나 스레드풀이 제한된 디스패처
2.  무제한 디스패처 : 사용할 수 있는 스레드나 스레드풀이 제한되지 않은 디스패처

### 부모 코루틴과 자식 코루틴

코루틴은 구조화를 제공해 코루틴 내부에서 새로운 코루틴을 실행할 수 있다. 이때 바깥쪽의 코루틴틴을 부모 코루틴이라 하고, 내부에서 생성되는 새로운 코루틴을 자식 코루틴이라고 한다.

구조화는 코루틴을 계층 관계로 만드는 것뿐만 아니라 부모 코루틴의 실행 환경을 자식 코루틴에 전달하는 데도 사용된다.  
**만약 자식 코루틴에 Coroutine Dispatcher 객체가 설정되지 않았으면 부모 코루틴의 CourtineDispatcher 객체를 사용한다.**

## 미리 정의된 CoroutineDispatcher

코루틴 라이브러리는 개발자가 직접 CoroutineDispatcher 객체를 생성하는 문제의 방지를 위해 미리 정의된 CoroutineDispatcher의 목록을 제공한다.

-   [Dispatchers.IO](http://Dispatchers.IO) : 네트워크 요청이나 파일 입출력 등의 입출력(I/0) 작업을 위한 CoroutineDispatcher
-   Dispatchers.Default : CPU를 많이 사용하는 연산 작업을 위한 CoroutineDispatcher
-   Dispatchers.Main : 메인 스레드를 사용하기 위한 CoroutineDispatcher

## [Dispatchers.IO](http://Dispatchers.IO)

멀티 스레드 프로그래밍이 가장 많이 사용되는 작업은 **입출력(I/O) 작업**이다. 애플리케이션에서는 **네트워크 통신**을 위해 HTTP 요청을 하거나 **DB 작업** 같은 입출력 작업 여러 개를 동시에 수행하므로 이런 요청을 동시에 수행하기 위해서는 많은 스레드가 필요하다.  
이를 위해 코루틴 라이브러리에서는 입출력 작업을 위해 미리 정의된 Dispatchers.IO를 제공한다.

**Dispatcher.IO는 입출력 작업을 위해 사용되는 CoroutineDispatcher 객체이다.**

-   최대로 사용할 수 있는 스레드의 수는 Kotlin(1.7.2)버전을 기준으로 사용 가능한 프로세서의 수와 64중 큰 값

```
fun main() = runBlocking<Unit>{
	launch(Dispatchers.IO){
		
	}
}
```

### Disptachers.Default

**대용량 데이터를 처리**해야 하는 작업처럼 **CPU 연산**이 필요한 작업(CPU 바운드)이 있다.  
이럴 때 사용하는 CoroutineDispatcher가 Dispatchers.Default이다.

```
fun main() = runBlocking<Unit>{
	launch(Dispatchers.Default){
		
	}
}
```

#### 입출력 작업 vs 바운드 작업

주된 차이점은 작업이 실행됐을 때 **스레드를 지속적으로 사용하는 지 여부**이다.

일반적으로 입출력 작업은(네트워크 요청, DB조회 요청) 등을 실행한 후 결과를 반환받을 때까지 스레드를 사용하지 않는다.  
반면에 CPU 바운드 작업은 작업을 하는 동안 스레드를 지속적으로 사용한다.

이로 인해 CPU 바운드 작업과 입출력 작업에서의 **효율성 차이**가 발생한다.

입출력 작업을 코투린을 사용해 실행하면, 입출력 작업 실행 후 스레드가 쉬지 않고 다른 입출력 작업을 수행하는 반면,  
CPU 바운드 작업은 코루틴을 사용해 실행하더라도 스레드가 지속적으로 사용되기때문이다.

### 공유 스레드풀을 사용하는 IO와 Default

코루틴 라이브러리는 스레드의 생성과 관리를 효율적으로 할 수 있도록 애플리케이션 레벨의 **공유 스레드풀을 제공**한다.  
이 공유 스레드풀은 스레드를 무제한으로 생성할 수 있으며, 관련 API를 제공한다.

공유 스레드풀을 시각화하면 아래와 같다.

![](https://blog.kakaocdn.net/dn/WN1TG/btsKPFxmUS6/vGGNoguUQTKPXRBJti2js1/img.png)

### Dispatchers.Main

Dispatchers.Main은 코루틴 라이브러리만 추가하면 사용할 수 있도록 설계된 Dispatchers.IO나 Dispatchers.Default와 다르게  
**일반적으로 UI가 있는 애플리케이션에서 메인 스레드의 사용을 위해 사용되는 특별한 CoroutineDispatcher객체이다.**

## 요약

1.  CoroutineDispatcher 객체는 코루틴을 스레드로 보내 실행하는 객체이다. 코루틴을 작업 대기열에 적재한 후 사용이 가능한 스레드로 보내 실행한다.
2.  자식 코루틴은 기본적으로 부모 코루틴의 CoroutineDispatcher 객체를 상속받아 사용한다.
3.  코루틴 라이브러리는 미리 정의된 CoroutineDispatcher 객체인 [Dispatchers.IO](http://Dispatchers.IO), Dispatchers.Default, Dispatchers.Main을 제공한다.
4.  Dispatchers.IO는 입출력 작업을 위한 객체로, 네트워크 요청이나 DB 조회, 파일 I/O 등에 사용된다.
5.  Dispatchers.Default는 CPU 바운드 작업을 위한 객체로 대용량 데이터 처리 등을 하는 데 사용된다.
6.  Dispatchers.IO와 Dispatchers.Default는 코루틴 라이브러리에서 제공하는 공유 스레드풀을 사용한다.
7.  Dispatchers.Main은 일반적으로 UI가 있는 애플리케이션에서 UI를 업데이트하는 데 사용된다.

## 참고

https://product.kyobobook.co.kr/detail/S000212376884