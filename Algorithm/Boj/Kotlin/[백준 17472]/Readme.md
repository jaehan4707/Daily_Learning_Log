## 문제 풀이

골드 1이라는 난이도답게 **BFS와 여러 가지를 섞었던 문제**였다.

우선 **문제의 핵심은 주어진 섬을 모두 연결할 수 있는 다리를 건설하는데 발생하는 최소비용**을 구하는 것이다.

해당 문구를 통해서 **섬이라는 정점을 모두 연결해야 하는 MST**라고 유추할 수 있다.

원래 MST는 모든 정점을 연결하는 문제이지만, 해당 문제는 정점을 확대한 섬으로 계산을 해야 했기 때문에 조금은 까다로웠다.

#### 자료 구조

```
private val br = System.`in`.bufferedReader()
private var n = 0
private var m = 0
private lateinit var graph: Array<IntArray>
private val points = mutableListOf<Point>()
private val islands = mutableListOf<Island>()
private val dx = arrayOf(1, -1, 0, 0)
private val dy = arrayOf(0, 0, 1, -1)
private val pq = PriorityQueue<Bridge>(compareBy { it.length })
private lateinit var parent: IntArray

data class Point(val x: Int, val y: Int)
data class Island(val number: Int, val areas: MutableList<Point> = mutableListOf())
data class Bridge(val from: Int, val to: Int, val length: Int)
```

-   Point는 x와 y를 가지는 데이터 클래스로 좌표를 위해서 구현했다.
-   Island는 섬에 대한 라벨링을 명시해주기 위해 만들었으며 number는 라벨 넘버이고, areas는 섬에 해당되는 좌표들이다.
-   Bridge는 섬과 섬을 연결하는 다리를 의미하며 from과 to는 Island의 number값으로 출발지점과 도착지점을 나타내고, length는 다리의 길이를 나타낸다.
-   graph는 바다와 섬을 입력받는 2차원 배열이다.
-   points는 색칠되어 있는 칸을 저장하기 위한 배열이다.
-   islands는 라벨링한 섬을 저장하기 위한 배열이다.
-   dx와 dy는 4방향 탐색을 위한 방향좌표
-   pq는 MST를 위해 사용할 우선순위큐이며, 다리의 길이가 적은 순으로 정렬된다.
-   parent는 크루스칼 알고리즘을 위해 사용된다.

#### 섬 분리

우선 섬을 분리시켜줘야 한다.

![](https://blog.kakaocdn.net/dn/0SLca/btsFYnuzWaC/yN3QUekwIa3UYMdZ6NPkak/img.png)

섬은 색칠되어있는 칸이고, 바다는 다리를 통해서 건널 수 있으므로, 우리는 색칠되어 있는 칸을 **BFS로 라벨링**해야 한다.

```
private fun makeGroup() {
    var group = 2
    points.forEach { point ->
        if (graph[point.x][point.y] == 1) {
            grouping(point, group)
            group++
        }
    }
    parent = IntArray(group + 1) { it }
}
```

나는 섬에 대한 분리를 makeGroup이라는 함수를 통해 구현했다.

group이 2부터 시작하는 이유는 기존 배열이 0과 1로 시작되었고, visit 배열을 통한 방문처리보다는 

아직 라벨링 하지 않았다는 의미인 좌표값이 1로 방문처리를 하고 싶었기에, 그룹 넘버를 2부터 시작했다.

grouping 함수는 point에 대해서 BFS를 시작한다.

```
private fun grouping(point: Point, group: Int) {
    val movePoints = mutableListOf<Point>()
    movePoints.add(point)
    val cur = Island(group)
    while (!movePoints.isEmpty()) {
        val now = movePoints.removeFirst()
        if (graph[now.x][now.y] == group) {
            continue
        }
        graph[now.x][now.y] = group
        cur.areas.add(now)
        for (dir in 0 until 4) {
            val mx = now.x + dx[dir]
            val my = now.y + dy[dir]
            if (checkRange(mx, my).not() || graph[mx][my] == 0) {
                continue
            }
            if (graph[mx][my] == group) {
                continue
            }
            movePoints.add(Point(mx, my))
        }
    }
    islands.add(cur)
}
```

grouping함수는 point를 bfs하면서 진행하지 못할 때까지 모든 정점을 방문한다.

특이한 점은 방문한 모든 정점은 Island의 areas 배열에 추가해 줬다.

최종적으로 cur은 그룹넘버와 그룹넘버에 해당되는 모든 좌표 배열을 가지고 있고, islands 배열에 추가해 줬다.

이렇게 섬을 분리했다면 다음은 다리를 놓아야 한다.

#### 다리 놓을 좌표 정하기

다리를 놓기 위한 최적화를 위해 많은 고민을 했다.

1번 섬에 대한 정점을 기준으로 모든 정점을 방문하는 것이 최선일까에 대해서 고민을 했다.

기본적으로 다리는 일직선으로 놓을 수밖에 없기 때문에 **4가지의 방향으로 DFS**를 진행해야 한다.

하지만 **4가지의 방향으로 가도 의미가 없는 탐색일 수 있고, 이를 최대한 줄여주고자** 했다.

그러기 위해서 **바다와 맞닿은 지역만 DFS를 진행**하기로 했다.

![](https://blog.kakaocdn.net/dn/yTpOC/btsFX7rQnBg/IDGhKlC33ioXGllwGafKl1/img.png)

다음과 같이 섬이 정해졌을 때, 섬 내의 좌표에 대해서 DFS를 진행하기 전에 해당 방향으로 바다가 있는지 체크를 했다.

만약 바다가 없고, 섬이 있다면 그 방향에 대한 DFS는 할 필요가 없기 때문이다.

```
private fun isNearSea(x: Int, y: Int, dir: Int): Boolean { // 해당 방향으로 한칸 전진 했을때 바다가 있는지
    val mx = x + dx[dir]
    val my = y + dy[dir]
    return !(checkRange(mx, my).not() || graph[mx][my] != 0)
}
```

좌표와 방향을 파라미터로 받아서, 배열의 범위를 벗어나거나, 바다가 아닐 경우 false를 반환한다.

```
for (i in 0 until islands.size) { // 섬
    for (j in 0 until islands[i].areas.size) { //섬의 내부 좌표
        val now = islands[i].areas[j]
        for (d in 0 until 4) {
            if (isNearSea(now.x, now.y, d)) { // 방향으로 갔을 때 바다인지 아닌지
                makeConnection(now, d)
            }
        }
    }
}
```

이렇게 섬 내부의 모든 좌표들에 대해서 DFS를 진행한다.

#### 다리 놓기

```
private fun makeConnection(from: Point, dir: Int) { //from -> to
    val move = arrayListOf<Point>()
    move.add(from)
    val visit = Array(n) { BooleanArray(m) { false } }
    var cnt = -1
    while (!move.isEmpty()) {
        val now = move.removeFirst()
        if (visit[now.x][now.y]) {
            continue
        }
        if (graph[now.x][now.y] != graph[from.x][from.y] && graph[now.x][now.y] != 0) { //다른 땅을 만났을 경우
            if (cnt >= 2) { //길이가 2이상일 경우
                pq.add(Bridge(graph[from.x][from.y], graph[now.x][now.y], cnt))
            }
            break
        }
        visit[now.x][now.y] = true
        val mx = now.x + dx[dir]
        val my = now.y + dy[dir]
        if (checkRange(mx, my).not()) {
            continue
        }
        if (graph[mx][my] == graph[from.x][from.y]) { // 같은 땅을 만나면 중단
            continue
        }
        move.add(Point(mx, my))
        cnt++
    }
}
```

다리의 조건은 길이가 2 이상이어야 한다.

따라서 출발점과 다른 그룹넘버를 확인하고, 길이가 2 이상이라면 PQ에 다리를 추가해 줬다.

유효한 탐색이 아닌 조건은 같은 땅을 만나거나, 배열의 범위를 벗어나는 경우일 것이다.

#### MST 

이렇게 모든 다리의 후보지를 구했다면 다리를 통해서 최소한의 비용으로 섬을 연결해야 한다.

나는 **MST의 크루스칼 알고리즘**을 사용했다.

그러기 위해 **Union & Find**를 구현해서 같은 집합 내의 연결인지를 확인하고 연결했다.

```
private fun mst(): Int {
    var answer = 0
    var edge = 0
    while (!pq.isEmpty()) {
        val cur = pq.poll()
        if (edge == islands.size - 1) { // 종료 조건
            break
        }
        if (find(cur.from - 2) != find(cur.to - 2)) {
            union(cur.from - 2, cur.to - 2)
            answer += cur.length
            edge++
        }
    }
    if (answer == 0 || edge < islands.size - 1) answer = -1
    return answer
}
private fun union(o1: Int, o2: Int) {
    val parent1 = find(o1)
    val parent2 = find(o2)
    if (parent1 == parent2) {
        return
    }
    if (parent1 < parent2) {
        parent[parent2] = parent[parent1]
    } else {
        parent[parent1] = parent[parent2]
    }
}

private fun find(idx: Int): Int {
    if (idx == parent[idx]) {
        return idx
    }
    parent[idx] = find(parent[idx])
    return parent[idx]
}
```

find에서 -2를 해준 이유는 섬이 추가되는 idx는 0번부터인데, 섬의 그룹 넘버는 2부터 시작하기 때문에 전처리를 해줬다.