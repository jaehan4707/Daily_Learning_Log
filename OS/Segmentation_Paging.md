# Segmentation & Paging

## segment

```
운영체제에서 세그먼트는 메모리 관리를 위한 단위로 사용된다.
세그먼트의 유형은 코드, 데이터, 스택 세그먼트가 있다.
세그먼트의 크기는 일정하지 않습니다.
```

세그먼트를 메모리에 할당하는 방법은 MMU를 통해서 이루어진다.

![](https://blog.kakaocdn.net/dn/A8yym/btsF9GzQ6QG/cjAU4XXq6ulvkNEwSMIUe0/img.png)

MMU를 통해 logical address → physical address로 변환해주는 방식이다.

segment table의 정보를 바탕으로 physical memory에 매핑한다.

![](https://blog.kakaocdn.net/dn/brkSmW/btsF9eDOQEK/K87k2YIReu0GNQpytJ8P90/img.png)

segement 테이블은 limit와 base 두가지 정보가 있다.

limit는 세그먼트의 길이를, base는 시작 위치 주소를 의미한다.

즉 base ~ base+limit의 범위를 세그먼트가 가지게 된다.

이렇게 세그먼트 테이블을 통해 프로세스가 메모리에 들어왔지만, 빈공간이 생긴것을 알 수 있다.

들어온 경우 공간이 정해져있고, 다른 프로세스가 들어올 경우, 만약 정해진 공간보다 큰 프로세스가 들어온다면 메모리에 들어올 수 없는 상황이 생긴다.(외부 단편화 발생)

당연하게도 세그먼트 테이블을 통해 프로세스를 메모리에 딱 맞게 적재하기 때문에 내부 단편화는 발생하지 않는다.

이러한 외부 단편화를 방지하는 방법으로 Paging이 있습니다.

## Paging

Paging에는 2가지 용어가 등장한다.

### pages

-   logical memory를 일정한 사이즈로 분할한 블록

### frames

-   physical memory를 고정된 크기로 분할한 블록

![](https://blog.kakaocdn.net/dn/c2fOfJ/btsGaagjsVW/P2lEtRVouUn30V5nQF5rq0/img.png)

logical memory는 동일한 크기의 page로, physical memory는 동일한 frame으로 쪼개져있다.

Paging을 통해서는 외부 단편화가 발생하지 않는다. 왜일까?

모든 사용 가능한 frame과 page를 필요로 하는 process에게 할당할 수 있기 때문에 외부 단편화는 발생하지 않는다.

하지만 내부 단편화는 어떻게 될까?

page와 frame들은 동일한 크기로 구성되어있고, Page 크기에 나눠 떨어지지 않을 가능성이 있다. 블록의 크기가 메모리의 크기보다 크다면, 남는 공간이 생기고, 내부 단편화가 발생한다.

Page Table에는 다음과 같은 데이터가 담겨져있다.

![](https://blog.kakaocdn.net/dn/ZGutq/btsF7welwrq/Jg1uVsJP04ufAQ6MluCkDk/img.png)

-   reference bit : 해당 페이지가 참조된 적이 있는지 판단하는 정보
-   valid bit(present bit) : 현재 페이지가 물리 메모리에 있는지, 페이징 파일에 있는지 여부에 대한 정보
-   dirty bit(modified bit): 현재 페이지가 수정된 적이 있는지에 대한 정보

```
Page의 size가 감소할수록 내부 단편화가 적게 발생하지만, 
Page의 개수가 증가하기 대문에 Page table entry가 많아지고, 이는 오버헤드 발생으로 이어진다.
```

## TLB (Translation Look-aside Buffer)

메모리에 프로세스를 적재하기 위해선 페이지 테이블에 매핑된 프레임의 정보를 읽고, 실제 메모리에 접근해서 데이터를 읽어야 했다. 적재하기 위해서 2번의 메모리 접근이 필요하고, 오버헤드가 굉장히 컸다. (메모리에 적재하기 위해 2번의 메모리 접근이 발생하기 때문) 이러한 오버헤드를 해결하기 위해 TLB를 사용한다.

![](https://blog.kakaocdn.net/dn/d1pEGi/btsGayaeFip/Ohjx5L0lqOfUoAWl2IYuwk/img.png)

TLB는 인덱스를 이용해 매핑된 데이터를 저장하고, 다음에 동일한 접근일 때 해당 데이터를 빠르게 반환하는 캐시와 동일한 기능을 한다.

page table에 있는 frame number에 대해 TLB에 있다면 TLB Hit로 빠르게 반환이 가능하고, TLB에 없다면 page table에 있는 프레임 번호를 TLB에 저장한다.

## Hierarchical paging(Multi-level)

![](https://blog.kakaocdn.net/dn/ddxVIx/btsGabTQnf9/CnM1vivpkqzaWUqlod2TA1/img.png)

모든 페이지 테이블을 보유하지 않아도 되기 때문에 메모리 공간의 효율을 챙길 수 있지만, 레이어가 많아질 경우 메모리 접근에 대한 오버헤드 이슈가 발생할 가능성이 있다.

## Hashed Page Table

![](https://blog.kakaocdn.net/dn/edD3sM/btsF9z11gkO/NDBQUxuIXkoMUPHkds56FK/img.png)

주소 공간이 32 bit보다 클 경우를 대응하기 위해 사용한다.

논리적 주소 공간의 페이지 번호인 p 값을 해시한 결과값으로 테이블을 검사한 뒤, 해당 물리적 메모리를 찾아가게된다.

같은 해시값으로 연결리스트를 이루기 때문에 문제를 해결할 수 있다.

## Inverted Page Table

![](https://blog.kakaocdn.net/dn/ebIMw6/btsF7RWRKiu/zik9aZ17tnVMJ962KXpFP0/img.png)

모든 프로세스가 하나의 페이지 테이블을 참조하고 있는것이 특징이다.

페이지 테이블의 entry 개수는 물리 메모리의 frame 개수와 동일하며 고정되어 있다.

모든 프로세스가 Inverted page table을 참조하기 때문에, 프로세스를 구분하기 위해 pid를 함께 사용하여 참조한다.

페이즈를 고정 크기로 관리하며, 테이블의 크기를 줄일 수 있다는 장점이 있지만, 기존 page table에서 entry를 인덱싱하는 방식과 달리 pid와 page number를 비교하는 순차탐색이기 때문에 시간적으로 불리하다.

# 참고

[https://resilient-923.tistory.com/390](https://resilient-923.tistory.com/390)