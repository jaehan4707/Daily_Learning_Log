### Forwarding &  Routing

```
Forwarding은 여행을 하는데 고속도로를 통과하는것처럼 도로를 나아가는 작업
Routing은 출발지에서 목적지까지의 경로를 계획하는 과정
```

1. Forwarding
    1. 패킷이 들어왔을 때, 보내주는 기능을 담당한다.
    2. Data Plane이 담당
2. Routing
    1. 들어온 패킷을 어디로 보낼 지 판단하는 기능
    2. 판단하는 과정을 Routing table을 보고 판단한다.
    3. Control Plane이 담당

### Data Plane & Control Plane

Data Plane

- 적용범위가 local이며, 각각의 라우터마다 동작한다.
- input port로 도착한 datagram이 어떻게 output port로 가야 하는지 결정한다.

Control Plane

- 적용범위는 네트워크 전체
- 출발지에서 도착지까지 라우팅 되는 방법을 결정한다.
    - 라우팅 알고리즘
        - Routing Table을 통해 경로를 정한다.
    - SDN(Softwatre-Defined-Networking)
        - 원격의 서버를 이용한다.

### Routing 알고리즘

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4AWnI%2Fbtsak3eMMty%2FA44vqEV0EZx9XKgxJLcWYK%2Fimg.png)

Control plane은 모든 라우터에 들어있고, 라우팅을 해준다.

forwarding table을 이용해서 control plane이 경로를 정해준다.

User Data가 도착하면 data plane에서 다음 경로로 forwarding 한다.

forwarding은 destination ip address만 보고 fowarding 한다.

### SDN

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbM93KU%2Fbtsai5YuDQu%2F0x9sP7pTVW6zTTFAWbQcsk%2Fimg.png)

중앙에서 네트워크를 제어하고 관리하는 방법

각 라우터의 Control Plane에서 다음 경로를 제어하고 관리해준다.

### Forwarding table

```
Control Plane에서 Routing을 진행할 때 이용하는 테이블이다.
목적지를 보고 경로를 결정하기 위한 정보를 제공한다.
```

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbfb06E%2Fbtsajc4KVHS%2FZy1DtiXwL0xRhyFk21uDa1%2Fimg.png)

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcSZ42S%2FbtsafORjArI%2FYQHuQf6Ev8NFucL7HILvY1%2Fimg.png)

Forwarding table은 목적지 주소에 해당하는 Link Interface로 보내준다.

경로를 결정해주는 방법은 Longest Prefix Matching 알고리즘을 사용한다.

즉 가장 길게 일치하는 Link Interface로 결정한다.

![Untitled](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbwPgKl%2FbtsaiqvsiBp%2FNlvfxketZ2NkJ0zKubONV1%2Fimg.png)

즉 다음과 같은 Destination Ip Address일때, 가장 길게 일치하는 1번 Interface로 전달한다.

## Routing Protocols

```
라우팅 프로토콜의 목표는 송신측에서 수신측 까지의 경로를 네트워크를 통해서 잘 결정하는것이 목표다.
```

여기서 잘은 상황에 따라 다르지만, 비용이 적거나, 빠르거나, 혼잡을 덜 발생시키는것이다.

어떤 경로로 전송하는것이 가장 효율적인지 결정하는 방법은 Link state, Distance Vectors가 있다.

### Link State

- 중앙집중형 라우팅 알고리즘
- 모든 라우터가 연결 상태와 링크 비용을 알고 있다.
    - 네트워크 전체에 대한 완전한 정보를 이용해서 출발지에서 목적지까지의 최소 비용 경로를 계산한다.
- 다익스트라 알고리즘을 이용
- 출발지에서 다른 도착지까지의 최적 경로를 계산해서 routing table에 저장한다.

### Distance Vector

- 분산형 알고리즘
- 최소 비용 경로의 계산이 라우터들에 의해 반복적이고 분산된 방식으로 수행된다(DP)
- 어떤 라우터도 모든 링크 비용에 대해 완전한 정보를 갖고 있지 않지만, 각 라우터는 자신에게 연결된 인접 노드에 대한 링크 비용 정보를 알고 있다.
- 따라서 벨만포드 알고리즘을 이용해 최적의 경로를 계산하고 있다.

벨만 포드 알고리즘

```
노드 x부터 y까지의 최소 비용 경로의 비용을 d𝙭(y) = minᵥ{c(x, v) + dᵥ(y)}
v는 x에 인접한 모든 이웃을 의미한다.
```

동작 방식

- 주기적으로 각 노드는 자신의 거리 벡터 추정값을 이웃 노드에게 전송한다.
- x가 어떤 이우승로부터 새로운 거리 벡터 추정값을 받으면 벨만 포드 방정식을 사용하여 자신의 거리 벡터를 업데이트한다.
- 각 노드는 자신의 거리벡터가 변경될 때에만 이웃 노드에게 알린다.
- 그리고 이웃 노드는 필요한 경우에만 자신들의 이웃에게 알린다.
- 알림을 받지 않는 경우는 어떠한 조치도 취하지 않는다.

문제점

- 링크의 속도가 빨라지는 좋은 소식은 네트워크 전역에 퍼지지만, 링크 속도가 느려지는 나쁜 소식은 매우 천천히 알려지게 된다.
- 노드 사이에 변경된 정보가 전파되지 않는 한 계속해서 알고리즘이 반복되고, 최소비용 경로에 대한 정보가 무한대로 수렴

# Expected Questions

> Q1 : 라우팅과 포워딩의 차이는 무엇인가요?
>

```
라우팅은 패킷이 출발지에서 목적지까지의 경로를 결정하는 과정이다.
라우터는 라우팅 테이블을 사용하여 Destination Ip Address를 분석하고, 다음으로 이동할 경로를 선택한다.

포워딩은 라우팅에 의해 선택된 경로를 따라 패킷을 보내는 과정이다.
라우팅 테이블에서 얻은 정보를 바탕으로 패킷을 올바른 Link Interface로 보낸다.
```

> Q2: 라우팅 알고리즘에 대해 설명해주세요
>

```
라우팅 알고리즘에는 두가지가 있다.

Distance Vector

벨만포드 알고리즘을 이용해 이웃 라우터로부터 수신된 거리 벡터 정보를 기반으로 결정한다.

Link State 
모든 라우터가 다른 라우터의 연결상태와 링크 비용을 알고 있다.
해당 정보를 토대로 다익스트라 알고리즘을 이용해 출발지에서 목적지까지의 최소 비용을 계산한다.
```

> Q3 : 포워딩 테이블에 대해 설명해주세요
>

```

포워딩 테이블은 라우터에서 데이터 패킷을 전달할 때 사용되는 정보를 담고 있다.
즉 라우터에서 forwarding 할 때 참고하는 정보이다.
이 테이블은 Destination Ip Address를 기반으로 적절한 출력 포트를 제공한다.

포워딩 테이블에 주요 정보는 다음과 같다.
1. Destination Ip Address
패킷의 목적지 IP 주소
2. Subnet Mask
네트워크의 크기와 범위를 결정하는데 사용한다.
3. Next Hop
다음으로 이동해야 할 next hop의 IP 주소
4. Interface
패킷을 전달하기 위해 사용될 네트워크 인터페이스
```