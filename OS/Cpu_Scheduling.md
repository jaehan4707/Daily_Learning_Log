# CPU Scheduling

CPU 이용률을 극대화하기 위해서는 `멀티 프로그래밍`이 필요하다.

CPU는 한번에 하나의 `프로세스`만을 실행할 수 있다.

그러기 위해서 필요한 방안이 `CPU 스케쥴링`이다.

<aside>
💡 CPU 스케쥴링은 최고의 성능을 내기 위해 
언제 어떤 프로세스에 CPU를 할당할지 결정하는 작업이다.

</aside>

최고의 성능을 내기 위해 프로세스를 결정하는 `기준`은 무엇일까?

우선 CPU에서 CPU 스케쥴링을 담당하는 녀석은 `CPU 스케쥴러`이다.

CPU 스케쥴러의 목적은 `사용자 측면`과 `시스템 측면`간의 차이가 있다.

### 사용자 입장에서는

- 사용자가 데이터를 입력하고 최대한 빠르게 응답 받기를 원하는 응답시간
- 프로그램을 제출 한 후 끝날때까지의 처리시간인 반환시간
- 프로세스들이 준비 상태로 대기열에서 기다린 시간인 대기 시간

### 시스템 입장에서는

- 총 경과 시간 대비 CPU가 순수하게 사용자 프로세스를 수행한 시간의 비율인 CPU 이용률
- 단위 시간 당 몇개의 프로세스를 처리했는지에 대한 처리량

CPU 스케쥴링은 이러한 목적을 가지고 있고, 다음과 같은 사건이 발생할때 일어난다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/cad46c2d-123c-431d-b81d-29c70ac14323)

- 프로세스가 I/O를 요구할 때
- 프로세스가 종료를 요구할 때
- 높은 우선순위의 프로세스가 나타날때
- 주어진 CPU 실행 시간을 초과했을때(이렇게 프로세스마다 CPU가 할당되는 시간을 Time Slice라고 한다.)

이러한 사건이 발생했을때, OS는 Ready Queue에 있는 프로레스들 중에 누구에게 CPU를 할당할지 결정해야 한다.

스케쥴링의 전략은 크게 2가지로 나뉜다.

## 비선점형 스케쥴링 [nonPreemtive]

<aside>
💡 실행중인 프로세스가 자율적으로 CPU를 반납할 때까지 CPU를 계속 점유하여 실행하는 운영체제 환경

</aside>

쉽게 얘기하면 할당된 프로세스가 끝날때까지 다른 프로세스는 기다려야 한다.

즉 `실행을 보장`해준다.

- Context Switching이 선점형에 비해 적게 일어나기 때문에 `오버헤드`가 적다.
- 프로세스의 배치에 따라 효율성 차이가 크다.
    - 예를 들어 오래 걸리는 프로세스 뒤에 있는 프로세스들은 적게 소요되는 시간임에도 불구하고 무작정 기다려야 한다.
- 모든 작업이 끝날때까지 기다리기 때문에 응답시간을 예측할 수가 있다.

## 선점형 스케쥴링 [Preemtive]

<aside>
💡 정해진 시간에만 CPU를 점유할 수 있다.
여기서 정해진 시간은 Time Slice라고 함

</aside>

- 비선점형 스케쥴링과 다르게 실행을 보장받지 못한다.
    - 정해진 시간이 지났거나, 인터럽트, 시스템 콜, 높은 우선순위의 프로세스가 발생되었을 경우 프로세스로부터 강제로 CPU를 회수 할 수 있다.
- CPU 처리 시간이 매우 긴 프로세스의 독점을 막을 수 있다.
- 높은 우선 순위를 가진 프로세스를 빠르게 처리할 수 있다.

# CPU Scheduling Algorithm

## FCFS(First come First Served)

<aside>
💡 말 그대로 먼저 들어오는 프로세스부터 처리하는 방법이고, 순서가 정해져있는 nonPreemtive 방식이다.

</aside>

- 직관적으로 이해하기가 쉽다.
- 프로세스의 평균 대기 시간,응답시간이 길어질 수 있다.
- CPU 사용시간이 긴 프로세스에 의해 짧은 프로세스들이 **오래 기다리는 현상**이 발생할 수 있고, 이는 CPU의 효율면에서 좋지 않다.

## SJF(Shortest Job First)

<aside>
💡 이용시간이 가장 짧은 프로세스부터 처리하는 방식이다.

</aside>

- 평균 대기 시간을 줄일 수 있다.
- `기아`가 생길 수 있다.
    - 이용시간이 짧은 프로세스부터 처리하기 때문에 이용시간이 긴 프로세스들은 영원히 대기하는 상황


## SRTF(Shortest Remaining Time First)

<aside>
💡 비선점형 스케쥴링 SJF와 다르게 SRTF는 선점형 알고리즘이고,
새로운 프로세스가 도착할때마다 스케쥴링이 일어난다.

</aside>

- 스케쥴링의 방식은 현재 실행하고 있는 프로세스의 잔여시간과 새로운 프로세스의 실행 시간을 `비교`해서 `짧은 프로세스`에게 할당해주는 방식이다.
- 여전히 `기아` 상태가 생길 수 있다.
- CPU 사용시간을 측정할 수 없다.

## RR(Round Robin)

<aside>
💡 각 프로세스가 CPU를 사용할 수 있는 시간을 특정 시간으로 제한해 이 시간이 경과하면 CPU를 회수하고, 레디큐에 있는 CPU에게 할당하는 방식

</aside>

- 정해진 시간이 지나면 CPU 자원을 회수하는 `선점형` 방식이다.
    - 정해진 시간은 time quantum, time slice등 다양한 방식으로 불린다.
- 응답 시간이 빠르다.
- `적당한 time quantum을 선정`하는것이 중요하다.
    - 너무 짧다면 CPU의 회수와 할당(Context Switching)이 빈번하게 일어나 `오버헤드 발생율`이 높다.
    - 너무 길다면 `FCFS` 방식과 다를것이 없다.

## Priority Scheduling

<aside>
💡 레디큐에서 기다리는 프로세스들 중 우선순위가 가장 높은 프로세스에게 CPU를 할당하는 방식

</aside>

- 우선순위는 여러 요인으로 정해지는데
    - `제한 시간`, `사용 파일 수`, `프로세스의 중요성` 등이 있다.
- 위에서 얘기했던 SJF 방식도 CPU 이용 시간이 짧은것이 먼저 처리되기 때문에 우선순위 스케쥴링에 속한다.
- 높은 우선순위인 프로세스에게 밀려서 낮은 우선순위인 프로세스가 CPU를 얻지 못하는 `기아`가 발생할 수 있다.
    - 해결법으로는 오랫동안 시스템에서 대기하는 프로세스들의 우선순위를 증가시켜주는것이고, 이를 `Aging(노화)`라고 한다.

## Multilevel Queue Scheduling

<aside>
💡 우선순위 스케쥴링과 라운드 로빈의 방식이 결합한 방식으로,
위에서 얘기했던 우선순위 스케쥴링의 문제점을 해결하기 위해 등장했다.

</aside>

![](https://blog.kakaocdn.net/dn/79ziv/btsEvc8RNWf/4X0IkHnU0Q0ozD3gj78Gp1/img.png)


- 프로레스의 특성 별로 레디 큐를 여러개 두어 우선순위를 부여하여 관리하는 방식이다.
- 각각의 큐는 각자의 스케쥴링 알고리즘을 가지고 있다.
- 특성 별로 프로세스를 그룹핑했기 때문에 다른 큐로 이동할 수 없다.
- foreground 큐와 background 큐로 분할
    - foreground 큐는 우선순위가 높고, background는 우선순위가 낮다.
        - 주로 foreground에는 cpu 사용시간이 짧은 작업, background는 사용시간이 긴 작업
    - foreground : RR, background : FCFS
- 각 Queue 간의 시간 점유의 격차가 클 수록 기아가 발생할 가능성이 높다.

## MLFQ(Multi Level Feedback Queue)
![](https://blog.kakaocdn.net/dn/19dwr/btsEtrS2bKY/52k25Z0U0CpwXwAPSEn3Ak/img.png)

MLFQ를 통해서 해결하고자 하는 점은 다음과 같다.

### 1. 짧은 작업을 먼저 실행 시켜 반환시간을 최적화하자!

### 2. 응답시간을 최적화하자!

하지만 짧은 작업을 먼저 실행시키는것은 실행 시간 정보를 판단할 수 없기 때문에 어려웠다.

그래서 MLFQ는 어떻게 정보 없이  스케줄을 할 수있고, 응답시간을 빠르게 할 수있을까?

timer를 걸어서 time slice를 초과할때까지 job이 계속 실행되면 long job,

그전에 끝나면 short job으로 판단한다.

long job이라면 아래의 큐로 강등시키고,  short job이라면 우선순위를 한단계 강등시킨다. 이런 방법으로 짧은 시간의 작업에 대해 우선순위를 부여한다.

MLFQ 방식은 우선순위가 높은 큐가 비면 아래 단계 큐의 작업이 실행된다.

하지만 이는 기아를 유발하지만, MLFQ에선 Priority Boost를 통해서 주기적으로 특정 시간이 지나면 모든 작업을 가장 높은 우선순위 큐로 올려주고,

거기서 RR을 실행시켜서 기아를 해결한다.

## HRN(Highest Response ratio  Next)

<aside>
💡 각 프로세스의 응답률을 계산하여 응답률이 가장 높은 프로세스를 먼저 처리하는 방식

</aside>

- 응답률이 같다면 CPU 이용시간이 짧은 프로세스가 우선순위가 높다.
- 대기시간과 작업시간을 통해 우선순위를 정하기 때문에 CPU 이용시간이 길더라도 무한정 대기하는 기아를 예방할 수 있다.

# 예상 질문

> Aging이란?
>

우선순위나 다른 이유로 자원을 점유하지 못했지만, 시간이 지날수록 우선순위가 높아져서 자원을 점유하게 만드는 기법

> 기아(Starvation)이란?
>

우선순위에 밀려 CPU 자원을 점유하지 못한 프로세스가 무한정 기다리는 상태

> **SJF를 preemptive한 방식으로 구현하기 위해서는 ready queue에 새로운 프로세스가 도착할 때마다 CPU에게 interrupt를 걸어야하나? 어떻게 새로운 프로세스가 도착했음을 알고, 그것이 더 짧은 프로세스임을 알고, CPU 제어권을 넘기는가?**
>

새로운 프로세스가 도착하면 도착한 프로세스의 CPU 시간과 현재 수행중이 프로세스의 잔여 시간을 비교해서 수행시킨다.

일정 시간마다 인터럽트를 거는 타이머 인터럽트를 발생시켜 현재 실행중인 프로세스를 중단하고, 레디큐에 있는 가장 짧은 실행 시간을 가진 프로세스를 실행하도록 한다.

> RR을 사용할때 Time silce에 따른 trade-off를 설명하라
>

Time slice가 높으면 잦은 Context Switching이 발생해 오버헤드가 크고,

너무 낮으면 FCFS와 유사하게 진행되기 때문에 기하가 발생할 수 있다.

그렇기 때문에 적절한 Time Slice를 선정하는것이 중요하다.

> 싱글 스레드 CPU 에서 상시로 돌아가야 하는 프로세스가 있다면, 어떤 스케쥴링 알고리즘을 사용하는 것이 좋을까요? 또 왜 그럴까요?
>

싱글 스레드에서 상시로 돌아가야하는 프로세스가 있다면 RR을 사용하는것이 효과적이다. RR 방식으로 짧은 time quantom을 부여한다면 프로세스가 동시에 실행하는것처럼 효과를 누릴 수있기 때문이다. 다른 방식으로는 우선순위를 기반으로 하는 스케쥴링 방식이 있을것이다.

> time slice 방식에서 각 queue에 CPU time을 비율로 할당하는것의 의미는 무엇인가?, 어떤것에 대한 비율인가?
>

Multi-level queue 에서 각 큐마다 tiem quantom을 다르게 주어 우선순위를 부여한다. CPU time을 다르게 주는 기준은 각 큐의 특성을 고려한다.

예시로는 foreground 큐에 80%, background 큐에 20%를 할당하는것이 있다.

> 별개의 queue를 두는 방식이 왜 load sharing과 관련이 있는가?
>

load sharing은 시스템 간에 작업이나 컴퓨터의 자원을 균형있게 분산시킨다는 개념이다.

이 개념과 별개의 queue를 두는 방식에 대한 연관성은 다음과 같다.

각 프로세스의 특성에 따라 queue를 두어서, 작업의 우선순위를 분류하고 처리할 수 있다.

부여한 우선순위에 대해서 높은 큐에 해당하는 프로세스들은 우선처리할 수 있다.

> 타 스케쥴러와 비교하여, Multi-level Feedback Queue는 어떤 문제점들을 해결한다고 볼 수 있을까요?
>

우선순위가 낮아 할당되지 않는 프로세스들의 우선순위를 증가시킬 수 있어

기아를 해결할 수 있다.

> FIFO 스케쥴러는 정말 쓸모가 없는 친구일까요? 어떤 시나리오에 사용하면 좋을까요?
>

대기열에 도착한 순서대로 처리되어야 하는 일괄처리환경에서 효과적이다.

그리고 스케쥴링의 비용이 최소하되는 방법이 FIFO이기 때문에 이를 고려한 작업이나, 처리 순서가 중요하지 않은 경우에도 효과적이다.