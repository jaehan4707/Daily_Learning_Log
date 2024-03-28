# TCP vs UDP

# UDP

---

- 데이터의 신뢰성이나 연결 지향성을 보장해주지 않는다.
    - 따라서 빠른 전송이 필요하거나, 데이터 손실이 허용되는 경우에 주로 사용한다.
        - 실시간 멀티미디어 애플리케이션(음성, 비디오)
        - 간단한 조회 및 상태 요청, 네트워크 게임 (스타크래프르 UDP를 기억해라)
- 데이터 그램 단위로 전송
- 비연결성 프로그램 (connect 과정이 없음)

```kotlin
UDP는 신뢰적인 데이터의 전송을 보장해주지 않지만,
Transport Layer의 목적은 데이터의 신뢰성이 아닌 전송에 대한 신뢰성이다.
따라서 UDP도 사용가능하다.
```

- 어떤 수신자가 어떤 서비스를 받는지,  패킷이 중간에 망가지지 않았는지만 처리한다.

## UDP Segment

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbszjwq%2Fbtr9QUbzbBY%2FKasicfWSvREemUTWgLVNt0%2Fimg.png)

UDP는 세그먼트 단위로 데이터를 전송한다.

세그먼트는 다음과 같은 정보들로 이루어져있다.

- source port
    - 전송지에 대한 포트넘버로, 수신자는 데이터가 어떤 어플리케이션으로부터 온 것인지 식별할 수 있게 하는 정보
- dest port
    - 수신자는 데이터를 어떤 어플리케이션으로 전달해야 하는지 식별할 수 있게 하는 정보
- length
    - UDP 세그먼트의 길이를 의미한다.
        - 헤더와 페이로드 길이의 합
- checksum
    - 데이터 무결성 검사를 위한 값
        - 송신자는 데이터를 전송하기 전에 체크섬을 계산하고 수신자는 체크섬을 통해 데이터의 무결성을 확인한다.
            - 일치하지 않는다면 에러가 있다.
            - 하지만 일치하지 않는다고 에러가 없는 것은 아니다.
                - 체크섬은 오류를 감지할 뿐, 악의적인 변조를 감지하지 못해 추가적인 방법이 필요하다.
- application data(payload)

# TCP

---

- 신뢰성있게, 순서대로 전송
    - 데이터의 신뢰성을 보장하기에, 받는 사람 입장에서 패킷의 순서대로 재조립해야한다.
- Connection Setup : 데이터를 보내기 전에 상대방의 상태를 확인하고 보낸다.
- Congestion Control : 네트워크 상에서 패킷들이 몰려 발생하는 혼잡을 피하기 위해 패킷 양을 조절한다.
- Flow Control : 라우터와 받는 수신자의 상황을 봐서 보내는 패킷 양을 조절한다.
- 데이터의 `신뢰성`과 `연결지향성`을 보장해준다.
- 데이터의 `안정성을 보장`하는 용도로 사용된다.
- `연결성 프로토콜`이다.

## TCP의 특징

---

1. **하나의 sender는 하나의 receiver와 통신**한다.
2. 데이터는 순서대로 Application layer로 넘겨지고, 데이터를 byte 단위로 처리하기에,
   meesage의 맥락을 신경쓰지 않는다.
3. 데이터 통신을 할 때 `양방향`으로 흐른다.
4. `Cumulative ACKs`를 통해 **데이터의 신뢰성과 정확성을 확보, 안정적인 데이터 전송을 지원**한다.
5. **TCP Congetsion, Flow Control이 Window Size를 결정**한다.
6. `연결지향적`이기 때문에 통신을 하기전에 `Handshaking` 과정을 통해 **sender와 receiver**의 상태를 확인한다.
7. **sender는 recevier의 상태를 보면서 전송량을 조절**한다. → Flow Control

## TCP Segement

---

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FNliYw%2Fbtr91xBDeTO%2Fb3Fmk1qEEkxSpdivijdZe0%2Fimg.png)

- Source Port : 송신측의 포트 번호
- Destination Port  : 수신측의 포트 번호
- Sequence Number
    - 패킷은 여러 개의 서로 다른 경로를 거치면서 전달되기에, 순서가 보장되지 않을 수 있다.
    - TCP는 UDP와 다르게 신뢰성있는 통신으 보장하기에, 수신 측에서 재 조립을 하는 과정이 필요하다.
    - 이때 Sequence Number를 이용해 조립한다.
- Acknowledgement Number : 수신자가 예상하는 다음 시퀀스 넘버
- length : TCP 헤더 크기의 값
- Flag
    - E(ECN) : 송신 측에서 네트워크 혼잡을 감지했을 때
    - C(CWR) : 수신 측에서 ECN 필드를 확인하고, 혼잡 상태에 대한 대응, 1이면 송신측에게 혼잡상태가 해소되었음을 의미
    - S(SYN) : TCP 연결의 시작을 나타냄
    - F(FIN) : TCP 연결의 종료를 나타냄
    - R(RST) : TCP 연결을 강제로 초기화하고 리셋함.
      오류 발생 시 연결을 강제로 끊을 때 사용
- window size : 수신 윈도우의 크기
    - 해당 필드를 통해 데이터의 전송양을 조절
        - 네트워크 혼잡 현상을 방지
- checksum : 헤더 및 데이터의 에러 확인을 위해 사용되는 필드 값
- Urgent Pointer
    - 선택적 필드 값
    - URG 플래그가 설정된 경우, 마지막 긴급 데이터 바이트를 가리킨다.

## TCP Flow Control

---

`Network Layer`에서 상위 계층으로 운반하는 속도가 `TransPort Layer`에서 `Application Layer`으로 보내는 속도보다 빠르다면 어떻게 될까라는 고민에서 출발한 개념이 `TCP Flow Control`이다.

수신자가 **처리할 수 있는 데이터의 양을 초과해서 보내면 데이터 손실이 발생하거나, 네트워크 혼잡을 유발**한다.

따라서 **송신자와 수신자 사이의 데이터 전송 조절을 필수적**이다.

이러한 역할을 하는 것이 `TCP Flow Control`이다.

TCP는 segment에 `window`라는 필드값이 있는데, 이는 송/수신할 수 있는 양을 의미한다.

TCP는 segment에 나 이만큼 보낼/받을 수 있어라고 명시를 하고 전송을 주고받는다.

즉 TCP Flow Control은

수신 측의 **buffer 상황을 보고 송신 측의 window size를 조절**합니다.

TCP에서는 flow control은 수신 측의 **소켓에 존재하는 버퍼**를 기준으로 행동합니다.

버퍼에 계속 데이터가 쌓이면, 상위 계층에서 데이터를 꺼내갑니다.

하지만 꺼내가는 속도보다, 버퍼에 쌓이는 속도가 빠를 경우는 데이터가 손실될 우려가 있습니다.

**따라서 송신측에서는 수신측의 buffer 상황을 보고 자신의 window size를 조절해서 Flow Control을 합니다.**

## TCP Connection

---

TCP는 `연결 지향성 프로토콜`이다.

연결을 시작하기 전에 송신/수신의 상태를 확인하는 과정이 있다.

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FckqVMD%2Fbtsaevjgihj%2FuYKqTXsukrXkQJ1lZNkplK%2Fimg.png)

### 2-Way Handshake

TCP 연결을 수립하는 방법 중 하나이다.

**클라이언트에서 연결을 하고 싶다고 요청하면, 서버에서 응답**하는 방식이다.

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FAA6to%2FbtsabeP4GfC%2FD4aBKqBHVAXN3daxuJgu71%2Fimg.png)

**동작 방식**

- 클라이언트가 서버에게 연결을 요청한다.
    - 이때 Head의 Syn bit를 1로 설정한다.
    - Syn 비트가 1이라는 뜻은 TCP 통신의 시작을 의미한다.
- 서버가 연결이 가능할 경우 클라이언트에게 응답을 보낸다.
    - TCP header의 ACK, SYN 비트를 1로 설정하여 보낸다.
- 클라이언트가 서버로 부터 받은 ACK 비트와, SYN 비트를 확인하고 데이터를 전송한다.

**문제점**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FNqvfe%2FbtsahKF8UkF%2FR0kdvyke4st3jovqGl1GW1%2Fimg.png)

위 그림과 같이 A가 B에게 연결을 요청했지만, B가 처리할 응답이 많아 **지연이 된 상황**이고 A가 **재연결을 요청**하는 상황이다.

이 경우 B는 과거의 연결 요청에 대해 응답을 했지만, A의 재요청 이후에 응답이 왔고, **A는 재전송에 대한 응답패킷이 아니기에 B의 응답 패킷을 무시**한다.

B입장에서 A가  응답을 제대로 받았는지 확인할 수 없고, B혼자 연결된 상황이다.

`2-Way Handshake`는 TCP 프로콜의 특징인 `양방향 연결성`을 보장해주지 못했다.

이러한 문제점을 해결하기 위해 `3-Way Handshake`가 등장했다.

### 3- Way HandShake

**동작과정**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbE0gdU%2FbtsafrgkpDF%2FlflYZIT605G4ZYFcQkyNLK%2Fimg.png)

1. 클라이언트가 TCP 프로토콜에서 연결을 시작하자는 의미로 SYN 비트와 seq num를 서버로 보낸다.
2. 서버는 클라이언트의 seq num가 X인 것을 인지하고, SYN, ACK가 합쳐진 패킷을 보내면서 자신의 seq num가 Y임을 통보한다.
3. 클라이언트는 서버로 부터 잘 받았다는 의미로 ACK를 보낸다.

```
2-Way Handshake의 문제점이었던 연결이 성공되면 바로 응답을 보냈기에, 
서버도 연결 성공에 대한 여부를 알 수 있다는 것이 차이다.
```

### 4-Way HandShake

**연결 종료**

연결 종료도 마찬가지로 양방향 통신을 보장해야 하기에 `Handshake` 과정이 필요하다.

연결을 종료할 때는 **`4-Way Handshake`**과정을 거친다.

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbBtdO5%2Fbtsaepp80yQ%2FahZfR0dzIyTMTZ8dk7ERkk%2Fimg.jpg)

1. 클라이언트가 연결 종료를 요청하는 FIN 비트를 1로 설정후 패킷을 전송한다.
2. 서버는 응답으로 ACK 패킷을 클라이언트에게 보낸다.
    1. 서버의 응답은 나 이제 요청 안받는다라는 의미이고, 서버에서 보낼 데이터가 있다면, 데이터를 모두 전송한다.
3. 서버가 모든 데이터 전송을 마친 후, FIN 비트를 1로 설정후 클라이언트에게 전송한다.
    1. 나 이제 데이터 보낼거 없다라는 의미이다.
4. 클라이언트는 FIN 패킷을 받고, 연결을 종료하기 위해 ACK 패킷을 보낸다.
5. 서버는 ACK 패킷을 받고, 연결을 종료한다.

**서로 FIN 패킷과 ACK 패킷을 전송하여 연결을 종료하기에 4-Way Handshake라고 부른다.**

## TCP Congestion

---

### Congetstion이란?

```
사전적 의미로는 혼잡, 과잉이라는 뜻을 가지고 있다.
네트워크에서 많은 것들이 한정된 공간을 사용하려고 할 때 발생하는 상황을 가리킨다.
네트워크에서 혼잡이 일어나면 딜레이가 길어지고, 패킷이 유실되는 상황이 발생한다.
```

### 네트워크는 어떻게 혼잡을 알아챌 수 있을까?

**3 duplicate ACKs**

- 송신 측이 `3번 이상 중복된 ACK`를 받은 상황이다.
- 이는 정상적으로 데이터가 전송되지 못했다는 것을 의미한다.

**timeout**

- 보낸 데이터가 유실되었거나, 수신 측의 ACK 응답이 유실되어서 `일정 기간 송신 측이 응답받지 못한 상황`이다.

### Congestion vs Flow Control

`Congestion control`은 **네트워크의 트래픽이 많아져, 성능이 저하되는 혼잡 상태를 관리**하는 기술이다.

그 반면 `Flow Control`은 송신사, **수신자 간의 데이터 전송 속도를 조절하여 오버플로우를 방지**하는 기술이다.

물론 각각은 다른 개념이지만 **함께 사용되어 네트워크의 효율적인 데이터 전송**을 가능하게 해준다.

### Congestion Control

**End - End Congetsion Control**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FtXo9x%2FbtsalaSfVsp%2FcgSJ6Ir02oCDIVJkqX1mI1%2Fimg.png)

네트워크가 개입하지 않고, 송신자와 수신자 사이에서 혼잡을 처리하는 방식이다.

송신자는 네트워크의 혼잡을 감지하기 위해 packet loss, delay를 관찰하고, 혼잡이 발생했는지 인지할 수 있다.

이것은 ACK를 통해서 수신자가 혼잡상태를 송신자에게 알려준다.

**Network - Assisted Congetsion Control**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FMn2MS%2FbtsagszWtmS%2F77krz5q8qzx9nsYfoqpQiK%2Fimg.png)

네트워크 자체가 혼잡 제어를 지원하는 방식이며, 네트워크 내에서 혼잡을 감지하고 제어하는 기술이다.

라우터가 송신측에게 혼잡한 라우터를 통해서 데이터가 통과했다고 통보한다.

통보한 사실을 바탕으로 송신측은 다른 라우터를 이용해서 데이터를 전송한다.

### 혼잡 제어를 위한 방법

**AIMD**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fquuzn%2Fbtsak3FJfQX%2F3x3BOELZtYldKwA4gnKzF0%2Fimg.png)

패킷 로스가 발생하지 않는다면 **전송률을 점진적으로 증가**시키다가, **packet loss가 감지되면 전송률을 반으로 감소**시키는 방식이다.

**AIMD의 문제점으로는 점진적으로 증가하는 방식때문에
많이 보낼 수 있는 상황이더라도, 시간이 오래 걸린다는점이다.**

**TCP Slow Start**

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F7J0U7%2Fbtsagq3eSyx%2FMTmMHaNp3qL8V5jiNd6KY1%2Fimg.png)

**window 사이즈를 2배로 증가**시켜서 데이터를 전송한다.

그러다 만약 혼잡이 발생하면 다시 **window 사이즈를 1로 줄인다.**

### Congestion Avoidance

`네트워크 혼잡`을 피하기 위해 **송신자가 전송 속도를 조절**하는 기술이다.

대표적인 예로는 `Flow Control`처럼 송신자가 window의 크기를 조절하는 방식이다.

**TCP Tahoe**

처음에는 **`Slow start`** 방식으로 window 사이즈를 증가시키다가 **임계점** 이후 부터 `AIMD`방식을 사용한다.

만약 혼잡이 발생한다면 **임계점의 크기를 이전 윈도우 사이즈의 절반**으로 감소시키고, **현재 윈도우의 크기를 1**로 설정한다.

이는 혼잡이 발생했던 윈도우 크기에 빠르게 도달하지 않게 하기 위해 임계점의 크기를 줄여주는 것이다.

**해당 방식의 문제점도 혼잡 발생시 1로 초기화하기 때문에 적정 크기로 증가하기까지 오래 걸린다.**

**TCP Reno**

TCP Tahoe와 동일한 알고리즘이지만  TCP Tahoe는 **3 duplicate ACKs와 Timeout
모두 동일한 대처를 하지만 TCP Reno는 다르다.**

`3 duplicated ACKs`는 **가벼운 손실**로 여긴다.

- 임계점을 현재 윈도우 크기의 반으로 설정하고 현재 윈도우의 크기를 임계점으로 설정한다.

`Timeout`은 **큰 손실**로 여긴다.

- 임계점 값을 현재 윈도우 크기의 반으로 설정하고, 현재 윈도우의 크기를 1로 초기화한다.

# 예상 질문

---

> Q1: Checksum이 무엇인가요?
>

```kotlin
TCP 통신에서 데이터의 신뢰성을 보장해주기 위해서 데이터에 무결성을 판단해야 한다.
데이터에 무결성을 검증하기 위해 세그먼트에 체크섬 필드값이 존재한다.
송신측에서 전달한 체크섬과 수신측에서 계산한 체크섬의 일치여부에 따라
데이터의 무결성을 판단한다.
```

> Q2: TCP와 UDP중 어느 프로토콜이 Checksum을 수행할까요?
>

```kotlin
TCP와 UDP 모두 데이터 무결성을 확인하기 위해 체크섬을 수행한다.
송수신 과정에서 체크섬을 계산하고, 데이터의 무결성을 파악한다.
```

> Q3: 그렇다면,  Checksum을 통해 오류를 정정할 수 있나요?
>

```kotlin
체크섬을 통해서 오류를 정정할 수는 없다.
체크섬은 오류를 감지하는데 사용되는것이다.
오류가 발생할 경우 체크섬을 통해 해킷이 손실되었음을 감지할 수만 있지 정정기능은 없다.
```

> Q4 TCP가 신뢰성을 보장하는 방법에 대해 설명해주세요
>

```kotlin
TCP는 신뢰성을 보장하기 위해 세그먼트에 seq number 필드값이 있다.
패킷이 전달되는 과정에서 무작위로 전송된 패킷을 seq number를 통해서 순서를 일치시켜준다.
UDP는 데이터의 빠른 전달이 목적이기 때문에 seq number 필드가 없다.
```

> Q5 TCP의 혼잡 제어 처리 방법에 대해 설명해주세요.
>

```kotlin
TCP의 혼잡 제어 처리 방법은 여러가지가 있다.
1. Flow Contrl
송신측에서 수신측의 buffer 상황을 보고 데이터의 전송량을 조절하는 방법

2. AIMD
window 사이즈를 점진적으로 증가시키면서 통신을 유지하다가, 
혼잡이 발생한다면 window size를 절반으로 감소시켜 혼잡을 피한다.
해당 방식의 문제점은 점진적으로 증가하는 방법은 일정 상태에 도달하기까지 
굉장히 오래걸린다는 문제점이 있다.

3. Slow Start
윈도우의 사이즈를 2배씩 증가시키면서 통신을 유지하다가, 
혼잡이 발생한다면 window size를 1로 초기화한다.
해당 방식의 문제점은 1로 초기화하는 방식때문에 window를 회복하는데 오래 걸린다.
```

> Q6 왜 HTTP는 TCP를 사용하나요?
>

```kotlin
1. TCP는 UDP와 다르게 신뢰성 있는 전송을 보장한다.
TCP는 연결 지향적인 프로토콜로, 데이터가 손실되지 않고, 
순서대로 전송됨을 보장하기 때문에 사용된다.

2. 흐름 제어 및 혼잡 제어
TCP는 네트워크 혼잡이 발생한다면 Flow Control과 Congestion Control과 같은 대처를 통해
네트워크 상황에 따라 데이터 속도를 조절할 수 있다.
위 2가지 대처 방식을 통해 네트워크의 혼잡을 방지하고, 안정적인 데이터 전송이 보장할 수 있다.

하지만 HTTP/3에서는 TCP 기반이 아닌 UDP 기반으로 변경되었다.
```



> Q7 그렇다면, 왜 HTTP/3 에서는 UDP를 사용하나요? 위에서 언급한 UDP의 문제가 해결되었나요?
>

```kotlin
정확하게는 UDP를 사용하는것이 아닌 UDP 위에 동작하는 QUIC 프로토콜을 HTTP/3에서 사용한다.
이전 버전에서 HTTP에서 UDP를 사용하지 않았던 이유는 
1. 신뢰성을 보장해주지 못함
2. 흐름 제어 및 혼잡 제어에 대한 대처가 없음

TCP가 아닌 UDP를 사용해 다음과 같은 점을 개선했다.
1. TCP의 문제점이었던 연결과정에서 handshake라는 작업을 UDP를 사용함으로써 제거했다.

TCP는 연결 생성을 위한 1-RTT, 핸드쉐이크 3-RTT가 필요하다.
하지만 QUIC는 최초 연결 설정 1-RTT가 필요하다. 연결 설정에 소요되는 시간이 매우 빨라졌다.

1. HOL Bloking 문제를 해결했다.
각 데이터에 대해 독립적인 스트림을 사용하기 때문에
어떠한 데이터를 나타내는 패킷이 손상되거나 유실됐다고 해도,
다른 데이터들의 전송 스트림은 정상적이기 때문에 안전하게 받아 사용할 수 있다.
```

> Q8 그런데, 브라우저는 어떤 서버가 TCP를 쓰는지 UDP를 쓰는지 어떻게 알 수 있나요?
>

```kotlin

```

> Q9 본인이 새로운 통신 프로토콜을 TCP나 UDP를 사용해서 구현한다고 하면, 어떤 기준으로 프로토콜을 선택하시겠어요?
>
```kotlin
UDP를 고를것 같다. TCP는 필요한 기능 전체가 들어간 느낌이지만, 부가 기능도 많아서 무겁다.
하지만 UDP는 최소한의 기능만 들어가고, 나머지는 없는것이지, 불가능한것이 아니다.
신뢰성을 보장하는 방법이나, 혼잡 제어, 흐름 제어와 같은 알고리즘도 탑재를 안한것이지 
구현이 불가능한것은 아니다. 
따라서 필요한것만 추가적으로 탑재를 할 수 있는 UDP를 사용할 것 같다.
추후 추가적인 기능이 생기더라도 TCP보다 UDP가 확장하기 편할것 같다고 생각한다.
```