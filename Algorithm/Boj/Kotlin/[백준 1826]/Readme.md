## 문제 풀이

오랜만에 풀어본 그리디 문제였다.

그리디문제는 문제에서 요구하는 최적의 결과를 도출하기 위한 접근방법을 잘 생각해야 하는 것 같다.

![](https://blog.kakaocdn.net/dn/bTLIPy/btsFYrjuRtf/YiPyzKPI8cXO3HikkMR1Ak/img.png)

버스 정류장을 지날 때마다 2가지 선택지가 있다.

멈춰서 연료를 채우거나, 그냥 지나가거나

따라서 나는 모든 정류장을 지나갈 때마다 2가지 선택지를 우선순위큐에 넣어줬다.

이렇게 해도 괜찮을거라 생각했던 이유는 우선순위큐의 정렬 조건을 멈춘 횟수가 가장 적은 것이 top에 오게끔 해서

항상 최선의 선택을 하게끔 만들려고 했다.

결과는 메모리초과..

아마 2가지 선택지를 매번 큐에 넣어줬기 때문이라고 생각한다.

어떻게 보면 그리디하게 풀지는 않았다. 우선순위큐를 통해서 매 선택마다 그리디 한 선택을 하려고 했지만 실패했다.

다음에는 더 그리디 한 풀이로 접근해 봤다.

그렇다면 현재 지점에서 갈 수 있는 정류장중 연료가 가장 많은 지역을 가면 안 될까라는 생각이 들었다.

연료가 가장 많은 지역을 계속 PQ에 추가하고, 거리가 모자를 때 PQ에 저장된 정류장을 꺼내서 연료를 더해주면 될 것 같다고 생각했다.

하지만 이 경우도 매번 가장 많은 연료를 가지고 있는 정류장에만 멈추다 보니, 충분히 갈 수 있음에도 도착하지 못했다.

![](https://blog.kakaocdn.net/dn/cTVCC6/btsF0E9tMf0/KA9m610gZm8KCfLQLUg941/img.png)

초기 연료는 10이고, 도착지점은 25인 테스트케이스에서 다음과 같은 주요소의 위치가 있다.

내 풀이대로라면 10이라는 연료를 가지고 갈 수 있는 주유소중 가장 최선의 선택지인 (5,1)에 갈 것이다.

그 후엔 11을 가지 못하기 때문에 PQ에 저장된 (5,1)의 연료량을 충전한다.

그러면 현재 지점은 거리가 5이고 연료는 6인 상황이다.

현재 지점에서 갈 수 있는 주유소중 최선의 선택은 (11,3)이므로, PQ에 저장한다.

다음 정류장인 (15,10)을 가지 못하기 때문에 PQ에 저장된 (11,3)을 꺼내고 3을 연료에 추가한다.

그러면 현재 지점은 거리가 11이고, 연료양이 3이다. 이는 15까지 가기에 모자라고, -1이라는 답이 출력된다.

하지만 해당 테스트케이스의 경우 (4,1) , (5,1) , (11,3) , (15,10)에 멈춘다면 도착할 수 있다.

따라서 지나친 주유소중 연료가 많은 순으로 방문을 해서 도착을 할 수 있는지 한번 더 검사해야 한다.

(4,1)은 지나갔지만, PQ에서 빼주지 않았다. 왜냐하면 그 당시 (5,1)이 최선의 선택이었기 때문이다.

이제 이렇게 PQ에 넣었지만 배제된 주요소의 연료량을 충전해서 갈 수 있는지 검사하면 된다.

#### 자료 구조

```
private val br = System.`in`.bufferedReader()
private var n = 0

data class Station(val distance: Int, val amount: Int)

val pq = PriorityQueue<Int>(compareBy { -it })

private lateinit var stations: Array<Station>
private var endDistance = 0
private var initAmount = 0
```

-   Station : 주유소를 의미하며 거리와 기름 양을 가진다.
-   pq는 연료량을 저장하기 위해 사용되며, 연료량이 많은 순서로 정렬된다.
-   endDistance는 도착지점, initAmount는 초기 연료량을 의미한다.

#### 갈 수 있는 주유소 찾기

```
private fun findStation(dist: Int, idx: Int): Int { //현재 갈 수 있는 주유소중 가장 연료가 많은 곳
    if (stations[idx].distance > dist) {
        return -1
    }
    var maxIdx = -1
    for (i in idx until stations.size) {
        if (stations[i].distance > dist) {
            maxIdx = i
            break
        }
        pq.add(stations[i].amount)
    }
    return maxIdx
}
```

-   현재 위치에서 갈 수 있는 정류장의 위치를 반환한다.
    -   없다면 -1

#### 움직이기

```
private fun move(): Int {
    var stationsNumber = 0
    var myDistance = initAmount
    var stop = 0
    while (stationsNumber < stations.size) {
        if (myDistance >= endDistance) {
            return stop
        }
        val result = findStation(myDistance, stationsNumber)
        if (result == -1) { //더이상 가지 못하는 경우
            val maxAmount = pq.poll() ?: break
            myDistance += maxAmount
            stop++
        } else {
            stationsNumber = result
        }
    }
    //끝까지 갔는데 앞에 충전을 안해서 못간 경우가 있음.
    while (!pq.isEmpty()) {
        myDistance += pq.poll()
        stop++
        if (myDistance >= endDistance) {
            return stop
        }
    }
    return -1
}
```

-   반복 조건은 정류장을 모두 지나치기 전까지이다.
-   만약 현재 기름양과 거리를 통해 도착지점에 갈 수 있다면 멈춘 횟수인 stop을 반환한다.
-   findStation을 통해서 갈 수 있는 주유소를 모두 저장하고, 가장 멀리 있는 Idx를 반환한다.
    -   만약 idx가 -1이라면 현재 거리에서 연료양을 가지고 방문할 수 있는 주유소가 없다는 뜻이다.
        -   그렇다면 PQ(주유소) 중에서 가장 연료가 많은 주유소의 연료량을 더해준다.
        -   이는 해당 주유소의 멈췄다는 의미이므로, stop을 증가시켜 준다.
    -   갈 수 있다면 정류장을 지나쳤다는 의미로 stationsNumber를 최신화한다.
-   마지막엔 이미 지나친 정류장에 대해서 한번 더 검사를 해준다.