## 문제 풀이

한마디로 요약하면 조건이 많은 그래프 탐색 문제이다.

내가 작성했던 메서드들은 크게 3가지로 분리된다.

#### 가장 가까운 승객 찾기

```
private fun findGuest(): Triple<Point, Point, Int>? {
    val visit = Array(n) { BooleanArray(n) { false } }
    var result: Triple<Point, Point, Int>
    val pq = PriorityQueue<Pair<Int, Point>>(
        compareBy(
            { it.first },
            { it.second.x },
            { it.second.y }
        )
    )
    pq.add(Pair(0, start))
    while (!pq.isEmpty()) {
        val now = pq.poll()!!
        if (visit[now.second.x][now.second.y]) {
            continue
        }
        visit[now.second.x][now.second.y] = true
        val temp = guest.find { it.first == now.second }
        if (temp != null) {
            result = Triple(temp.first, temp.second, now.first)
            return result
        }
        for (d in 0 until 4) {
            val mx = now.second.x + dx[d]
            val my = now.second.y + dy[d]
            if (!checkRange(mx, my) || graph[mx][my] == 1 || visit[mx][my]) {
                continue
            }
            pq.add(Pair(now.first + 1, Point(mx, my)))
        }
    }
    return null
}
```

출발지에서 가장 가까운 승객을 찾는 함수이다.

가장 가까운 승객을 찾기 위해서 우선순위큐를 사용했고, 조건에 맞게 정렬 조건을 변경했다.

정렬 조건은 다음과 같다.

-   거리가 가장 가까운 승객
-   거리가 가장 가깝다면 행의 값이 더 적은 승객
-   위 조건도 일치하다면 열의 값이 더 적은 승객이다.

출발지에서 BFS를 진행하면서 방문한 지역에 손님이 있을 경우를 find를 통해서 검사했다.

만약 손님이 있을 경우는 손님의 좌표와 이동한 거리를 result에 담아서 반환한다.

이렇게 우리는 findGuest를 통해서 승객의 좌표와 승객까지의 거리라는 정보를 얻을 수 있다.

#### 승객을 도착지점까지 옮기기

```
private fun moveGuest(from: Point, to: Point): Int {
    val pq = PriorityQueue<Pair<Int, Point>>(compareBy { it.first })
    val visit = Array(n) { BooleanArray(n) { false } }
    pq.add(Pair(0, from))
    while (!pq.isEmpty()) {
        val now = pq.poll()
        if (visit[now.second.x][now.second.y]) {
            continue
        }
        visit[now.second.x][now.second.y] = true
        for (d in 0 until 4) {
            val mx = now.second.x + dx[d]
            val my = now.second.y + dy[d]
            if (!checkRange(mx, my) || graph[mx][my] == 1 || visit[mx][my]) {
                continue
            }
            if (mx == to.x && my == to.y) {
                return now.first + 1
            }
            pq.add(Pair(now.first + 1, Point(mx, my)))
        }
    }
    return Int.MAX_VALUE
}
```

승객의 위치를 from , 승객이 도착해야 하는 지점을 to로 받는 함수이다.

최적의 동선으로 도착지를 방문해야 하기 때문에 우선순위 큐를 사용했고, 정렬 조건은 이동한 거리이다.

탐색을 진행하다가 도착지점에 방문한다면 이동거리를 반환하고, 도착지점에 방문하지 못한다면 방문하지 못했다는 뜻으로

MAX\_VALUE를 반환한다.  배열의 크기가 정해져 있기 때문에 출발지에서 도착지점까지 도착하더라도 최댓값은 Int.MAX\_VALUE를 넘지 못한다.

해당 함수를 통해서 승객이 위치한 지점에서 도착지점까지의 거리 비용을 구할 수 있다.

#### 승객의 이동 여부를 판단하기

```
while (oil != 0 && guest.isNotEmpty()) {//데려다 줄 수 있는지 파악하고 데려다 줘야함.
    val find = findGuest() //손님 찾기
    if (find == null) {
        oil = -1
        break
    }
    val dist = moveGuest(find.first, find.second) //손님 데려다주기
    oil -= find.third
    if (dist <= oil) { //갈 수 있는 경우
        oil = oil - dist + 2 * dist
        start = find.second //출발점 옮기기
        guest.remove(Pair(find.first, find.second))
    } else {
        oil = -1
        break
    }
}
```

택시기사는 연료가 0이 되거나, 태울 승객이 없을 경우 반복문을 종료한다.

find를 통해서 손님을 찾고, 손님이 없다면 그 즉시 반복문을 중단한다.

moveGuest를 통해서 손님을 이동시키는데의 거리 비용을 구할 수 있다.

우선 기름의 양을 조정한다.

택시의 출발지점에서 손님까지의 이동 거리인 find.third를 빼서 손님의 위치에서 도착지점까지 갈 수 있는지를 판단해야 한다.

만약 갈 수 있다면 기름의 양을 계산하고, 출발지를 옮겨주고 손님을 없애준다.

만약 도착지점까지 갈 수 없다면 그 즉시 중단한다.

문제가 복잡하고, 코드가 길었지만 문제와 조건을 천천히 따져가면 쉽게 풀 수 있는 문제다.

다음부터는 계산에 용이하게 하기 위해 point 클래스에 dist 값을 추가하면 더 깔끔한 코드가 될 것 같다.