# HTTP 1.0/2.0/3.0

```java
Hyper Text Transfer Protocol은 HTML 문서와 같은 리소스들을 가져올 수 있도록 해주는 
프로토콜이며, 웹 브라우저와 웹 서버간의 통신을 위해 만들어졌다.
```

## HTTP/0.9

- 원-라인 프로토콜이며, 서버 ↔ 클라이언트 구조를 따른다.
- 요청 : 단일 라인으로 구성
- 리소스 경로 : GET 메서드만 가능

특징

- HTTP 헤더가 없어서 HTML 파일만 전송 가능하다.
- 상태 혹은 오류 코드가 없다.

## HTTP/1.0

- HTTP/0.9 버전의 한계가 많아서 확장성을 높였다.

특징

- 버전 정보가 각 요청에 포함된다.
- 요청 메서드가 GET,HEAD,POST 세가지로 확장되었다.
- 상태 코드가 각 응답의 시작 부분에 포함되어, 브라우저가 해당 요청에 대한 성공, 실패를 알 수 있었고 동작할 수 있었다.
- 브라우저의 메타데이터 전송을 허용하며 프로토콜을 유연, 확자 가능하게 만든 HTTP 헤더가 등장했다.
- 새로운 HTTP 헤더의 등장으로 HTML 파일 이외의 다른 파일도 전송할 수 있게 되었다.

## HTTP/1.1

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/ed098ea9-41eb-44ab-b5a0-2924640eef76/Untitled.png?id=0126d148-80e3-49c5-a6e3-746ddd9fbe1a&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708682400000&signature=vSTT99t_l9Sl_RT8bFy1wMgixFZTZn3putNmwRTul5g&downloadName=Untitled.png)

Connection당 하나의 요청을 처리하도록 설계되었다.
요청의 순서는 순차적으로 FCFS로 진행한다.하지만 이러한 방식은 순서가 명확하게 정해져있기에 문제가 있었다. 처리하기 쉬운 오브젝트도 순서가 후순위라면 기다려야하는 단점이 생긴다.

이러한 지연을 우리는 HOLB(Head Of Line Blocking)이라고 한다.

한번의 커넥션에 한번의 요청과 응답을 처리하다보니 HandShaking 과정이 반복되면 RTT가 증가되고, 성능이 저하된다.

RTT(Return To Time) 

> 패킷이 목적지에 도달하고 나서 해당 패킷에 대한 응답이 출발지로 돌아오기까지의 시간
> 

## keep-alive

```java
TCP 연결을 재사용할 수 있는 기능
TCP 연결을 하나의 요청이 아닌 여러 요청에 대해서도 사용을 할 수 있게 되었다.
연결의 지속성은 요청마다 3-way handshake를 하지 않아도 되기 때문에
요청시간을 대폭 감소시켰다.
```

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/6c706686-384f-4da0-86d9-b100f5590ba8/Untitled.png?id=c6fd2ba0-7c9b-4901-bfe2-cb870b8aa8c3&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708682400000&signature=DkyB6tpJFvyjdEcgvwVvT4gL_MUgW_5Ij_bl1Zf51Cs&downloadName=Untitled.png)

## HTTP/2

### HTTP2 의 목적

- 여러개의 오브젝트를 HTTP Request 할때 delay를 줄이자!

HOLB를 줄이는것을 목적으로 등장했다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/7cd00b4a-e406-4ca4-ae29-3722534d0979/Untitled.png?id=bd3fb48e-a3d2-45ce-99c9-d40323d61192&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708682400000&signature=nx7gOW5y51TekJ1WHw5yeSQQ14hZjpPb-OXqrD9Vsws&downloadName=Untitled.png)

그림을 보면 O1에 의해 빨리 끝낼 수 있는 작업인 O2,O3,O4가 기다리고 있는 모습을 확인할 수 있다.

이러한 기다림과 지연을 HOLB라고 한다.

HTTP는 HOLB를 막기 위해서 프레임 단위로 작업을 처리합니다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/730db06c-1a07-492d-8a7c-3ccfaa95c7ce/Untitled.png?id=509899db-1ccf-4ee2-9c0f-5cd263c2241e&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708682400000&signature=yQzkwgUOO9jbaKRBDIIPoGo3Criq64QtuOf0pjxB6To&downloadName=Untitled.png)

이렇게 프레임 단위로 작업을 나눠서 진행함으로써 다른 작업들이 큰 작업에 의해 기다리는 지연시간이 짧아졌다.

## HTTP/3

가장 주된 특징은 TCP가 아닌 UDP를 사용한다는 점이다.

HTTP3는 QUIC라는 프로토콜을 기반으로 동작하는데, QUIC가 UDP를 기반으로 한다.

UDP는 신뢰성을 보장하지 않는 통신이지만, 자체적인 신뢰성 보장을 제공하는 기능을 추가하고, 성능을 개선한 프로토콜인 QUIC를 사용해서, 신뢰성을 보장받을 수 있다.

![Untitled](https://file.notion.so/f/f/bea1f681-e907-4ad0-8d9e-c46aa582a35d/5c22bb99-042d-4ce5-afc5-1ad99e72602d/Untitled.png?id=bac40419-5d84-4751-9388-c42f0d742d52&table=block&spaceId=bea1f681-e907-4ad0-8d9e-c46aa582a35d&expirationTimestamp=1708682400000&signature=zSw-iMsxYolB3QFR_woYRX7uDM563THptTDmdr0iFCg&downloadName=Untitled.png)

- 연결 내 스트림이 완전히 독립적으로 동작해서 하나가 실패해도 다른 스트림에 영향을 주지 않는다.
- IP 기반이 아니라 연결 별 고유 UUID를 이용해 각 연결들을 식별한다.

# 예상 질문

### **Q1) HTTP1.1/과  HTTP/2의 차이점은?**

- HOL Blocking에 대해 설명해라

```java
HTTP 1.1에서는 TCP Connection 하나당 하나의 요청을 처리할 수 있었다.
이러한 방식의 문제점은 들어온 요청마다 순서가 정해져있었고, 
이 순서가 정해졌기에 생기는 문제점이 바로 HOLB이다.
더 자세하게 설명하면 요청된 작업이 여러개 있다고 가정할 때, 앞의 작업때문에 뒤에 
요청받은 작업이 끝날때까지 기다리는 지연이 발생했는데, 이를 Head Of Line BLocking이라고 한다.
이를 해결하기 위해서 HTTP/2에서 작업을 프레임 단위로 처리해서 HOLB를 완화하고,
지연시간을 줄일 수 있었다.
```

- HTTP/3.0의 주요 특징
