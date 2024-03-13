## 문제 풀이

문제를 풀기 위해서 생각해야 할 점은 **외부 공기와 내부 공기의 분리를 어떻게 할 것인가 이다.**

#### 외부 공기와 내부 공기의 분리

외부 공기와 내부 공기의 다른 점은 어떤 것일까?

내가 생각한 점은 내부 공기는 치즈에 둘러싸여 밖으로 나가지 못하고, 외부 공기는 치즈에 둘러 쌓여 있지 않아서 자유롭게 움직일 수 있다는 점이다.

이를 구현하기 위해선 역발상이 필요하다.

내부공기가 밖으로 나가지 못하니까 외부에서 내부 공기로 가지 못한다.

따라서 가장자리인 (0,0)에서 BFS를 실시해서 치즈를 제외한 모든 공기를 방문한다.

치즈에 막혀서 방문하지 못하는 공기가 바로 내부공기가 될 것이다.

```
private fun divideAir() {
    val q = arrayListOf<Pair<Int, Int>>()
    val visit = Array(n) { BooleanArray(m) { false } }
    q.add(Pair(0, 0))
    while (q.isNotEmpty()) {
        val now = q.removeAt(0)
        if (visit[now.first][now.second]) {
            continue
        }
        visit[now.first][now.second] = true
        graph[now.first][now.second] = -1 //진짜 외부 공기
        for (d in dir.indices) {
            val mx = now.first + dir[d][0]
            val my = now.second + dir[d][1]
            if (!checkedRange(mx, my)) {
                continue
            }
            if(graph[mx][my]==1) {
                cheese.add(Pair(mx, my))
                continue
            }
            if (visit[mx][my]) {
                continue
            }
            q.add(Pair(mx, my))
        }
    }

}
```

공기를 찾는 과정은 배열 전체를 순회하기 때문에 치즈를 추가할 수도 있다는 생각이 들었다.

따라서 연산을 두 번 할 필요는 없기 때문에 공기를 찾는 과정에서 나는 치즈인 곳은 따로 더해줬다.

그리고 방문했던 공기는 q에 넣고, 방문했다면 -1로 초기화했다.

즉 모든 공기가 0으로 입력받았다면 내부 공기와 외부 공기를 분리하기 위해서 -1이라는 값을 추가해 줬다.

#### 치즈 녹이기

치즈는 상하좌우 4방향을 탐색하면서 외부 공기와 2 변 이상 접촉한다면 녹는다.

```
private fun isAroundAir(row: Int, col: Int): Boolean {
    var meetAir = 0
    for (d in dir.indices) {
        val mx = row + dir[d][0]
        val my = col + dir[d][1]
        if (!checkedRange(mx, my)) {
            continue
        }
        if (graph[mx][my] != -1) { //1과 -1은 치즈, -1은 외부공기
            continue
        }
        meetAir++
    }
    return meetAir < 2 //인접한 변이 2변 이상이어야 함 따라서 0과 1이면 false
}
```

다음과 같이 탐색을 하면서 -1이 아닌 경우는 치즈와 내부공기 이기 때문에 해당 케이스를 제외하면 외부공기이다.

2 변 이상 접촉한 경우 치즈는 녹일 수 있기 때문에 다음과 같이 return 한다.