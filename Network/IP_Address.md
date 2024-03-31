# IP Address

## IP Address

```
일반적으로 IP 프로토콜에서 호스트와 라우터 인터페이스를 구분하기 위해 사용된다.
IP 주소는 32 bit로 이루어져있으며, 4개의 bite를 .으로 구분하여 표시한다.
```

223.1.1.1 =  `11011111` `00000001` `00000001` `00000001`

## IP Address의 구성

> IP Address는 `Network` 와 `Host`로 구성되어있다.
>

Ip Address는 `Network`.`Host`   로 표현할 수 있다.

예를 들어 223.1.1.1이라면 네트워크는 223.1 이고, 호스트는 1.1이다.

## IPv4의 Class

IPv4는 대역에 따라 A, B, C, D, E 클래스로 나누어진다. 해당 클래스들은 네트워크 크기와 호스트 수에 따라 나누어지게 된다.  클래스 구분을 위해 첫째 옥텟의 맨 왼쪽 bit에 A클래스는 0, B클래스는 10, C클래스는 110, D클래스는 1110, E클래스는 1111을 지정한다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcTOMRt%2FbtrwZ0WnruP%2FwRJvnMCFAkeOClBzPguc5k%2Fimg.png)

### A

- 첫번째 옥텟의 상위 비트가 0으로 시작한다.
- 클래스 A 주소는 0.0.0.0 에서 127.255.255.255까지의 범위를 갖는다.
- 네트워크 주소는 첫번째 옥텟인 8비트고, 호스트 주소는 24비트를 갖는다.

### B

- 첫번째 옥텟의 상위 비트가 10으로 시작한다.
- 클래스 B의 주소는 128.0.0.0 ~ 191.255.255.255 까지의 범위를 갖는다.
- 네트워크 주소는 두번째 옥텟인 16비트고, 호스트 주소는 16비트를 갖는다.

### C

- 첫번째 옥텟의 상위비트가 111로 시작한다.
- 클래스 C 주소는 192.0.0.0 ~ 223.255.255.255까지의 범위를 갖는다.
- 네트워크 주소는 세번째 옥텟인 24비트고, 호스트 주소는 8비트를 갖는다.

### D

- 첫번째 옥텟의 상위 비트가 1110으로 시작한다.
- 클래스 D 주소는 224.0.0.0 ~ 239.255.255.255까지의 범위를 갖는다.
- 멀리캐스트 그룹 주소를 지정하는데 사용되며, 네트워크 부분이 아닌 특정 그룹을 식별하는데 사용된다.

### E

- 첫번째 옥텟의 상위 비트가 1111로 시작한다.
- 클래스 E 주소는 240.0.0.0 ~ 255.255.255.255까지의 범위를 갖는다
- 예약된 주소로, 실험 및 특수 목적을 위해 사용된다.

## Subnet

```
IP 네트워크를 더 작은 네트워크로 분할하는 과정이거나, 그 결과로 생성된 작은 네트워크
```

Subnet Part

- 라우터를 통하지 않고도, 서로 물리적으로 닿을 수 잇는 네트워크를 나타내는 bit들
- 네트워크 구조에서 라우터를 제거해보면 어느 부분이 subnet인지 알 수 있음

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fcav31J%2FbtsjpEZWNPM%2FBYwcqlx6iU4i1U7UBUpB40%2Fimg.png)

subnet mask는 IP 주소와 함께 사용되어 IP 주소의 네트워크 ID식별하고, 호스트 ID를 구분한다.

표현은 subnet mask :/N  → 상위 N개의 비트가 네트워크 주소라는것을 의미한다.

서브넷 마스크는 네트워크 부분을 나타내는 1의 연속과 호스트 부분을 나타내는 0의 연속으로 구성된다.

따라서 subnet mask와 IP 주소를 AND 연산하면 네트워크 부분을 얻을 수 있다.

IP 주소 : 192.168.1.100, subnet mask : 255.255.255.0인 경우

네트워크 : 192.168.1 , 호스트 : 100

서브넷 마스크를 사용해 IP 주소를 서브넷으로 분할하고, 네트워크와 호스트를  식별해

효율적인 관리와 네트워크 구성을 가능하게 해준다.

## CIDR(Classless Inter Domain Routing)

클래스를 활용해서 주소공간을 나눌 경우, 주소 공간을 비효율적으로 사용하고, 작은 네트워크에 많은 주소를 할당하는 등의 문제가 있었다.

CIDR은 이러한 제약을 극복하기 위해 클래스를 활용하지 않고, IP 주소를 표현하는 방법이다.

A.B.C.D/N → 상위 n이 subnet 부분의 비트수를 나타내고, 나머지 비트가 host 주소를 의미한다.

## DHCP(Dynamic Host Configuration Protocol)

```
네트워크에서 컴퓨터나 기타 네트워크 장치에게 IP 주소 및 관련 네트워크 구성 정보를
자동으로 할당하는 프로토콜이다.
```

일반적으로 데스크톱과 같이 고정된 컴퓨터는 이동할 일이 거의 없어서 고정된 IP 주소를 사용하는것이 유리하다.
하지만 노트북, 모바일과 같이 장소를 이동해 사용하는 경우 하나의 IP가 아닌 여러개의 IP 주소를 사용해야한다.

### 동작 과정

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbD33NV%2FbtsjGw61Z1d%2Fw58VvBeyD3HudrnlJJORQk%2Fimg.png)

1. DHCP discover
    1. 호스트가 DHCP Discover에게 DHCP를 요청한다.
2. DHCP offer
    1. DHCP 서버가 DHCP Offer 메시지로 IP 주소를 할당한다.
3. DHCP request
    1. 호스트가 IP 주소를 얻고, 허락을 요청한다.
4. DHCP ACK
    1. 요청 받은 IP 주소를 사용하는것에 문제가 없으면 ACK 메시지를 보낸다.

DHCP 사용을 통해 네트워크에서 IP 주소를 효율적으로 관리할 수 있으며, IP 주소가 고갈되는것을 방지하고,
네트워크 자원을 절약하는데 도움이 된다.

## NAT(Network Address Translation)

```
네트워크에서 IP 주소를 변환하는 프로세스를 의미한다.
여러 장치가 하나의 IPv4 Address를 공유하며 인터넷에 연결할 수 있도록 도와준다.
```

인터넷이 발전함에 따라 IPv4 주소 체계로 표현할 수 있는 IP 주소의 개수인 2^32가 모자르기 시작했다.
이러한 IPv4 주소 고갈의 문제를 해결하기 위해 등장했다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbsMwMx%2FbtsjGwTMGro%2F3O9MLXKIptezRW9BUu2uuk%2Fimg.png)

138.76.29.7이라는 IP 주소를 필요로 하는 3명에게 local network에서 임의의 IP 주소를 부여한다.

실제 네트워크로 나갈때는 다시 변환해서 나가는 방법이다.

내부망의 host들은 port number로 구분한다.

### 장점

1. 여러 장치가 하나의 IP 주소를 공유할 수 있기에, 효율적인 사용이 가능하고, IPv4 주소의 고갈 문제를 완화할 수 있다.
2. 로컬 네트워크 내 호스트의 주소를 외부에 알리지 않고 변경할 수 있다.

### 동작 과정

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FwWMTG%2FbtsjEnwC7vS%2FPcMD2MuSuOwfgPkUjMgEFk%2Fimg.png)

1. 10.0.0.1 host가 Datagram을 보낸다. 이때 des IP는 128.119.40.186이고 포트번호는 80이다.
2. Nat router가 NAT 주소로 바꾸고, 호스트를 식별할 수 있도록 포트번호를 새로 부여한다.
    1. 138.76.29.7로 바꾸고 새로운 포트번호인 5001을 부여한다.
    2. NAT 라우터에서 패킷을 변환했다는것을 기록하기 위해 NAT tranlation table에 기록한다.
3. server는 다시 source Ip 주소로 datagram을 보낸다.
4. NAT 라우터에서는 des IP 주소와 포트번호를 table과 비교해 다시 변환하여 내부망으로 보낸다.

NAT에 대한 논란

- IPv4 주소 부족의 문제는 NAT가 아닌 IPv6으로 해결되어야한다.
- IP 주소가 중간에 변환되었기 때문에 End to End 원칙을 위반한다.

## IPv6

```
IPv4 만으로는 IP 주소를 할당하는것이 한계가 있었고, 이를 확장하기 위해 등장했다.
IPv4가 32bit 였다면, IPv6는 128bit의 주소값을 사용해서 많은 기기를 연결할 수 있다.
```

![IPv4 헤더구조](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fcq6jkE%2FbtsjH16HK9v%2Fe3JpTQddZ8s37dqaEsVig1%2Fimg.png)|![IPv6 헤더구조](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fv5JxZ%2FbtsjAv2BDzp%2FwEAsWMMJjOh5BrmqZDFHc1%2Fimg.png)
---|---|
IPv4 헤더 구조|IPv6 헤더 구조


### 주된 변화

1. 고정된 길이의 40 바이트의  헤더를 사용한다.
    1. 기존의 IPv4는 20 바이트에서 60바이트까지의 가변적인 길이를 가졌다.
2. checksum의 유무
    1. IPv4는 헤더와 데이터 오류를 감지하기 위해 체크섬 필드가 있지만, IPv6는 체크섬 필드가 없다.
    2. 오류 감지는 데이터 링크 계층에서 처리한다.

### Datagram 구성요소

- pri : 데이터 그램의 우선순위 또는 품질
- flow label : 패킷의 흐름을 식별하기 위한 값
- payload length : 데이터 그램에 실려 최종 목적지까지 전달되는 데이터의 크기
- next header : 페이로드 다음 계층 프로토콜을 의미(TCP,UDP 식별)
- hop limit : 네트워크에서 전파할 수 있는 최대 홉수 (수명을 의미함)
    - 0이 되면 패킷은 폐기된다.
- source address : 데이터 그램의 출발지
- destination address : 데이터 그램의 목적지

# 참고

https://miraekwak.tistory.com/71

https://jja2han.tistory.com/335

https://jja2han.tistory.com/336