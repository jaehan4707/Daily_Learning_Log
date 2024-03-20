## 문제 풀이

정말 오래 걸렸던 문제다. 특정 테케에서 통과하지 못해서 많이 고생했다.

내가 생각하는 **알고리즘 분류는 조합과 BFS**를 같이 사용하는 문제인 것 같다.

배양액을 뿌릴 수 있는 땅에 대해서 빨간색,초록색,배양액X에 조합을 통해서 경우의 수를 정해준다.

#### 사용한 자료구조 

```
private lateinit var setting: Setting
private lateinit var graph: Array<IntArray>
private val points = mutableListOf<Point>()
private var answer = 0
private val dx = arrayOf(1, -1, 0, 0)
private val dy = arrayOf(0, 0, 1, -1)
data class Setting(val n: Int, val m: Int, val r: Int, val g: Int)
data class Point(val x: Int, val y: Int, var color: Int = 0)
data class Garden(var time: Int, var energy: Int)
```

-   Setting : 처음 입력에 대한 n,m, r, g를 담는 data class
-   Point : x좌표와 y좌표, 배양액의 종류를 저장하는 data class
-   Garden : 탐색의 과정에서 시간과 배양액의 값을 저장하는데 필요한 data class
-   setting : 초기 인풋을 한꺼번에 처리하기 위해 받아줬다.
-   graph : 호수, 배양액을 뿌릴 수 있는 땅, 그렇지 않은 땅을 저장하는 변수
-   points : 배양액을 뿌릴 수 있는 땅을 저장하는 배열
-   answer : 꽃의 최대값을 저장
-   dx , dy : 상,하,좌,우 탐색을 위한 배열

#### 배양액 정해주기

```
private fun dfs(depth: Int, rCnt: Int, gCnt: Int) {
    if (depth == points.size) {
        if (rCnt != 0 || gCnt != 0) {
            return
        }
        answer = max(answer, bfs())
        return
    }
    if (rCnt - 1 >= 0) {
        points[depth].color = 1
        dfs(depth + 1, rCnt - 1, gCnt)
        points[depth].color = 0
    }
    if (gCnt - 1 >= 0) {
        points[depth].color = 2
        dfs(depth + 1, rCnt, gCnt - 1)
        points[depth].color = 0
    }
    points[depth].color = 0
    dfs(depth + 1, rCnt, gCnt)
    points[depth].color = 0

}
```

나는 재귀함수를 작성하면서 다음과 같은 점을 신경 썼다.

**가능한 조합만 넣어주자!**

우리는 **주어진 배양액을 모두 사용**해야 한다.

예를 들어 배양액을 뿌릴 수 있는 땅이 7개이고, 빨간색 배양액은 3개, 초록색 배양액은 2개일 때

우리는 7개의 땅에 대해서 빨간색 배양액과 초록색 배양액을 전부 사용해야 한다. (초과해서 사용하면 안 된다)

따라서 재귀 호출 전에 해당 배양액을 사용할 수 있는지를 검사하고, 재귀를 호출했다.

재귀는 배양액이 들어갈 수 있는 횟수만큼 돌고, points \[i\]는 배양액의 위치를 저장한 배열이다.

해당 idx에 배양액을 빨간색으로 사용하겠다!라고 명시하기 위해선 빨간색 배양액이 있어야 한다.

그래서 **if문을 통해서 특정 배양액에 대해서 검사를 하고, 땅에 배양액을 할당**했다.

당연하게도 dfs를  탈출했다면 땅에 배양액을 초기화해줘야 한다.

하지만 재귀호출에서 배양액을 선택하지 않는 선택지도 있기 때문에 끝까지 탐색했다면 배양액을 모두 검사했는지 확인해 준다.

이렇게 하면 **최대한 재귀 호출의 깊이를 제한**할 수 있다.

이렇게 땅에 배양액을 뿌렸다면, BFS를 진행해야 한다.

```
private fun bfs(): Int {
    var flower = 0

    val originPoints = mutableListOf<Point>()
    originPoints.addAll(points)
    val visit = Array(setting.n) { Array(setting.m) { Garden(0, 0) } }
    points.forEach { point ->
        visit[point.x][point.y] = Garden(0, point.color)
    }
    var time = 0
    while (true) {
        val tempPoints = mutableListOf<Point>()
        while (!points.isEmpty()) {
            val now = points.removeFirst()
            if (visit[now.x][now.y].energy == 3) {
                continue
            }
            if (now.color == 0) {
                continue
            }
            for (dir in 0 until 4) {
                val mx = now.x + dx[dir]
                val my = now.y + dy[dir]

                if (!checkRange(mx, my) || graph[mx][my] == 0) { //배열을 벗어나거나, 호수일 경우
                    continue
                }
                if (visit[now.x][now.y].energy == visit[mx][my].energy) { //양분이 같을 경우는 무시함
                    continue
                }
                if (visit[mx][my].energy == 3) { //이미 꽃을 형성한 경우
                    continue
                }
                if (visit[mx][my].energy == 0) { //양분이 없을 경우 시간이 달라도 넣어줌.
                    visit[mx][my].energy += visit[now.x][now.y].energy
                    visit[mx][my].time = time + 1
                    tempPoints.add(Point(mx, my, visit[now.x][now.y].energy))
                } else if (visit[now.x][now.y].energy != visit[mx][my].energy && visit[now.x][now.y].time + 1 == visit[mx][my].time) { //양분이 다르지만 시간이 같을 경우
                    visit[mx][my].energy += visit[now.x][now.y].energy //에너지 더해주기\
                    visit[mx][my].time = time + 1
                    tempPoints.add(Point(mx, my, now.color))
                }

            }
        }
        if (tempPoints.size == 0) {
            break
        }
        points.addAll(tempPoints)
        time++
    }
    for (i in 0 until setting.n) {
        for (j in 0 until setting.m) {
            if (visit[i][j].energy == 3) {
                flower++
            }
        }
    }
    points.addAll(originPoints)
    return flower
}
```

탐색에서 중요한 점은 동일한 시간에서 **서로 다른 배양액이 만나야 꽃이 핀다**라는 점이다.

그리고 탐색하려는 칸에 동일한 배양액이 있다면 무시해도 된다.

탐색을 하는 과정에서 if문을 많이 사용했다.  내가 생각한 case는 다음과 같다.

1.  배열의 범위를 벗어나거나,  호수일 경우 -> 무시
2.  탐색하려는 칸의 배양액이 기준 칸의 배양액과 같다면 -> 무시
3.  이미 꽃을 피웠다면 -> 무시
4.  양분이 없을 경우(최초 방문)
    -   토지에 배양액을 뿌리고, 시간을 +1 해준다. 시간을 +1 해준 이유는 다음 초에 탐색할 때 사용해야 하기 때문이다.
5.  최초 방문이 아니고(배양액이 뿌려져 있고), 시간이 같은 경우
    1.  해당 경우는 꽃을 피우는 경우이다.
    2.  빨간색의 경우 값은 1이고, 초록색의 경우는 2이기 때문에 해당 과저에서 visit의 energe 값은 3이 된다.

동시에 탐색이라는 조건 때문에 points를 계속해서 업데이트해줘야 했다.

나는 다른 points배열을 만들어서 거기에 추가해 주고, 탐색이 끝난 후 기존 points에 업데이트해 줬지만,

지금 와서 생각해 보면 points의 기존 사이즈만큼 탐색을 하고, points에 계속 추가하는 것도 나쁘지 않을 것 같다.

83%에서 계속 틀렸었는데 다음과 같은 코드를 추가하니 통과했다.

```
points.forEach { point ->
    visit[point.x][point.y] = Garden(0, point.color)
}
```

탐색을 시작 전에 배양액을 뿌릴 수 있는 땅에 배양액을 뿌려주는 작업을 해줘야 했다.

나는 큐에서 하나씩 꺼내면서 최초 방문이라면 배양액을 뿌려줬다.

하지만 배양액을 미리 뿌려주지 않고, 하나씩 꺼낼 때마다 배양액을 뿌려준다면, 이미 배양액이 뿌려졌을 땅에 대해서 계산이 중복해서 들어갔던 것 같다.

따라서 깔끔하게 탐색을 하기 전에 배양액을 뿌려줬다.

탐색이 끝나고 visit 배열을 순회하면서 값이 3이라면(꽃을 피웠다면) 꽃을 추가해 줬다.