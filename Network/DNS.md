# DNS

**컴퓨터가 원하는 방식과 사람이 원하는 방식은 다르다**고 할 수 있다.

컴퓨터는 정확한 정보를 원하고, 사람들은 정확한 수치보다는 추상적인 정보를 원한다.

우리가 사용하고 있는 인터넷도 [www.naver.com](http://www.naver.com) 과 그에 해당하는 ip 주소가 있다.

일반 사용자들은 www.naver.com을 입력하지만 컴퓨터는 www.naver.com을 그에 해당하는 ip 주소로 변경해서 동작한다.

이걸 가능하게 해주는것이 `DNS`이다.

만약 DNS가 없다면, 우리는 [www.naver.com](http://www.naver.com)을 입력하는 대신 그에 대응하는 IP 주소를 매번 입력해야 할 것이다.

즉 쉽게 말하면

```kotlin
인터넷에서 사용하는 주소(www.naver.com)을 해당 도메인 이름에 대응하는 
IP주소로 매핑해 주는 시스템이다.
```

DNS의 특징으로는 `중앙 집중화된 데이터베이스`가 아닌 `계층화된 데이터베이스`를 사용한다는 점이다.

중앙 집중화된 구조의 단점은 명백하다.

**중앙에서 오류가 생길 시 전체의 오류가 생긴다는 점과, 유지보수, 확장 면에서 분산된 데이터베이스보다 불리하다는 점이다.**

따라서 DNS는 많은 서버를 이용해서 계층화된 구조를 이루고 있다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/b1bbf753-7969-4607-a2d0-a2d95de0994b)

3가지의 계층으로 나뉜것을 알 수 있다.

- Root Dns 서버
    - TLD 서버의 IP를 제공
- TLD(Top Level Domain) 서버
    - Authoritative name 서버의 IP를 제공
- Authoritative name Dns 서버

예를 들어 www.amazon.com을 주소창에 입력한다면

1. Root Dns Server가 .com에 해당하는 Dns Servers에 IP를 제공한다.
2. .com DNS servers가 [amazon.com](http://amazon.com)에 해당하는 amazon.com DNS Server에 IP를 제공

## Local Dns Server

---

- Local DNS 서버는  DNS 서비스를 이용할 때, 도와주는 역할을 한다.
- ISP(Internet Service Provider)가 Local Dns Server를 가지고 있다.
- 사용자가 Dns Query를 날리면 local DNS 서버가 가장 먼저 받는다.

## DNS Query

---

```kotlin
Dns 서버들을 검색해서 해당 사이트의 IP 주소를  찾는데에 있다.
IP 주소를 찾을 때 까지 Dns 서버에서 다른 Dns 서버를 오가며 
에러가 날 때까지 검색한다.
```

Query의 종류는 `Iterated query`, `Recursive query`가 있다.

### Iterated Query

---

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/0ac5258f-cef2-4252-a5ca-08f06069ec74)

1. local dns 서버가 사용자의 요청을 받는다.
2. local dns 서버는 root dns 서버에 쿼리를 보내고, root Dns Server는 .edu에 해당하는 TLD를 제공한다.
3. edu에 해당하는 TLD 서버에 쿼리를 보내고, TLD 서버는 nyu.edu에 대항하는 Authoritative Dns server의 IP를 제공한다.
4. Authoritative Dns server에 쿼리를 보내고, 해당 DNS 서버는
   최종적인 IP 주소를 제공한다.

**해당 방식은 직접적인 답을 가르쳐주는 방식이 아닌, 답을 알고 있는 서버를 제공하는 방식이다.**

### Recursive Query

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/d0b51aa6-5ec3-49d1-98b8-453708ac3159)

- Iterative Query와 다르게 계층적인 구조로 타고 타고 들어가는 방식이다.

Dns Query의 동작방식을 요약하면 다음과 같다.

1. Local Dns  서버가 Root Dns 서버에 요청
2. TLD Dns 서버로 요청
3. Authoritative Dns 서버로 요청
4. 최종적으로 DNS 기록에서 매칭되는 IP 주소를 찾아서 Local Dns 서버로 반환

### DNS Caching

---

- Local DNs 서버 이전에 사용했던 DNS 매핑 정보를 저장하고, 같은 쿼리가 오면 빠르게 처리하는 방식이다.
- TTL 개념을 사용해, 특정 기간 동안 사용되지 않는 정보는 자동으로 삭제한다.

### DNS Records

---

**(name, value, type,ttl)의 형태로 records를 저장한다.**

type의 종류

- type = A
    - 특정 도메인에 매핑하는 IP주소(IPv4)를 알려준다.
    - name = Hostname
    - value = IP Address

| name | value |
| --- | --- |
| www.example.com | xxx.yyy.zzz.kkk |
- type  = AAAA
    - A 타입과 같지만 IPv4 주소체계가 아닌 IPv6 주소 체계를 알려준다.
- type = NS(Name Server)
    - 특정 도메인을 관리하는 네임 서버를 알려준다.
    - name = domain(naver.com)
    - value = Authoritative Name Server
- type = Cname(Canomial Name)
    - 도메인 이름을 다른 도메인 이름으로 매핑
    - type = A 레코드가 있어야 설정이 가능하다.
    - name : 실제 이름
    - value : 별칭
- type = mx(Main Exchange)
    - 메일서버에 도달할 수 있는 메일 서버를 제공한다.
    - value : MailServer의 이름

## Hosts?

---

- Hosts 파일은 로컬 호스트용 DNS이다. 도메인을 요청하고 DNS 서버로 가기전에 hosts 파일을 거쳐서 간다. 도메인 주소가 IP 주소로 바뀔 수 있도록 해주는 것이 hosts 파일의 역할이다.
- 즉 hosts 파일에 매핑한 IP 주소와 도메인은 DNS 서버보다 우선시 한다.
- DNS를 놔두고 hosts 파일이 존재하는 이유
    1. DNS를 거치지 않고 로컬에서 변환하므로 속도가 향상
    2. 리소스 사용량이 줄어듬
    3. DNS 서버의 보안 문제를 예방할 수 있다.

# 예상 질문

---

> Q1 : DNS는 몇 계층 프로토콜인가요?
>

---

```kotlin
Application Layer에 속한다.
```

> Q2: UDP와 TCP중 어떤 것을 사용하나요?
>

---

```kotlin
DNS는 신뢰성보다 속를 더 중요시하고, 많은 클라이언트를 수용하는것이 목적이다.
따라서 속도가 빠르고, 연결 상태를 유지하지 않고, 정보 기록을 최소화해서
많은 클라이언트를 수용할 수있는 UDP를 사용한다.
```

> Q3: Dns Recursive Query vs Iterative Query
>

---

```kotlin
두 방법 모두 클라이언트가 DNS 서버에게 요청을 보내면 
도메인에 대한 IP 주소를 반환한다.
하지만 그 방법에서의 차이를 두고 있다.

Recursive Query는 Local Dns 서버가 요청을 할 경우 주소를 찾을때까지
하위 계층의 DNS 서버를 탐색해서 결과를 반환한다.

Iterative Query는 Local DNS 서버가 요청을 할 경우
루트 DNS 서버는 TLD DNS 서버를 반환하고,
TLD DNS 서버는 Authrotive Dns 서버를 반환해서 최종적인 IP 주소를 찾는다.

즉 직접적인 주소를 반환하는것이 아닌 참조를 반환해서 클라이언트가 참조를 ㄷ
따라가서 결과를 얻도록 한다.
```

> Q4: DNS 쿼리 과정에서 손실이 발생한다면 어떻게 처리하는가?
>

---

```kotlin
2. 재시도
- 요청에 대한 응답을 일정 시간 수신하지 못하면 재시도
3. TTL
- DNS에는 TTL(Time to Live) 값이 있다.
해당 값은 DNS query의 유효기간을 나타내며,
유효기간이 지난 정보는 자동으로 삭제해서 데이터 손실 문제를 방지한다.
```

> Q5: 캐싱된 DNS 쿼리가 잘못될 수 도 있는데, 어떻게 에러를 보장하는가?
>

---

```kotlin
TTL 값을 통해서 잘못된 정보가 없도록 보장한다.
```

> Q6: DNS 레코드 타입 중 A,CNAME, AAAA의 차이에 대해서 설명해라
>

---

```kotlin
A : 호스트의 도메인 이름에 해당하는 IPv4 주소로 매핑
CNAME : 호스트의 도메인 이름을 다른 도메인 이름으로 매핑
AAAA : 호스트의 도메인 이름을 해다 호스트의 IPv6 주소로 매핑
```

> Q7: hosts 파일은 어떤 역할을 하나요? DNS와 비교할 때 어떤것이 우선순위가 더 높은가?
>

```text
hosts 파일은 Local DNS이다.
도메인을 요청하고 DNS 서버로 가기전에 IP 주소로 거쳐가는 파일이다.
즉 우선순위가 DNS보다 높다.
컴퓨터는 DNS 서버에 쿼리를 보내기전에 hosts 파일을 거쳐서
해당 도메인에 대항 IP 주소가 매핑되어있다면,
쿼리를 보내지 않고 IP 주소를 사용한다.
```

# 참고
https://github.com/devSquad-study/2023-CS-Study/blob/main/Network/network_dns_and_network_flow.md
https://devfancy.github.io/Network-DNS/
https://jja2han.tistory.com/301