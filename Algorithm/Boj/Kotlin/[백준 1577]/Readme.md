## 문제 풀이

확률과 통계를 하셨던 분이라면 자주 접했을 문제라고 생각합니다.

바로 도로를 주고, 도로를 통해 이동할 수 있는 경우의 수를 계산하는 문제입니다.

문제의 특이한 점은 이동하지 못하는 도로를 만들어서, 경우의 수를 제한한다는 점입니다.

확률과 통계에서 풀었던 것처럼 기본적으로 맨 위와 맨 왼쪽은 경로의 수가 1로 고정입니다.

최단 거리의 경우는 직진으로 갈 수밖에 없기 때문입니다.

나머지는 위에서 오는 경우의 수와 왼쪽에서 오는 경우의 수를 더해주면 됩니다.

하지만 우리는 이동할 수 없는 도로도 계산해줘야 하기 때문에 추가적인 작업이 필요합니다.

#### 자료 구조

```
private val roads = hashSetOf<Road>()
data class Point(val x: Int, val y: Int)
data class Road(val start: Point, val end: Point)
```

우선 저는 2개의 data class와 자료구조 1개를 만들었습니다.

Point는 x와 y를 뜻하는 하나의 class입니다.

Road는 Start와 End를 생성자로 가지며, Start->End로 가는 경로가 막혀있다는 뜻입니다.

roads는 Road를 element로 가지는 자료구조이며, 중복을 허용하지 않기 때문에 Set으로 설정했습니다.

#### 이동할 수 없는 도로 입력받기

```
repeat(k) {
    br.readLine().split(" ").map { it.toInt() }.apply {
        val (x1, y1, x2, y2) = this
        if (y1 == y2) { //세로 방향
            roads.add(Road(Point(Math.min(x1, x2), y1), Point(Math.max(x1, x2), y1)))
        } else if (x1 == x2) { //가로
            roads.add(Road(Point(x1, Math.min(y1, y2)), Point(x1, Math.max(y1, y2))))
        }
    }
}
```

문제에서 별 다른 안내가 없어서 무조건 처음 좌표 -> 뒤  좌표로 도로를 건설했는데, 입력의 순서가 랜덤이어서 추가적인 작업이 필요했습니다.  
예를 들어 1 2 0 1 의 입력이 들어왔을 때처럼 (1,2) -> (0,1)이 아닌 (0,1) -> (1,2)로 바꿔줘야 했습니다.

따라서 도로의 방향에 따라서 분기처리를 해줬습니다.

추가적인 작업이 필요한 이유는 아래 DP 계산식에서 설명하겠습니다.

#### 기본값 세팅하기

여기서 기본값이란 맨 위와 맨 왼쪽의 도로를 채우는 작업입니다.

![](https://blog.kakaocdn.net/dn/Wa0zp/btsGcM2BKOQ/qVhFLXGGNn3iRpERvMvTXk/img.png)

하지만 위 그림처럼 만약 이동할 수 없는 도로가 있는 경우의 수가 있기 때문에 이동할 수 없는 경로가 등장하는 순간 끊어줘야 합니다.

```
for (i in 1..n) {
        if (roads.contains(Road(Point(i - 1, 0), Point(i, 0)))) {
            break
        }
        dp[i][0] = 1
    }
for (i in 1..m) {
    if (roads.contains(Road(Point(0, i - 1), Point(0, i)))) {
        break
    }
    dp[0][i] = 1
}
```

#### 경우의 수 계산하기

```
for (i in 1..n) {
    for (j in 1..m) {
        val up = Point(i - 1, j)
        val left = Point(i, j - 1)
        val target = Point(i, j)
        if (!roads.contains(Road(up, target))) {
            dp[i][j] += dp[i - 1][j]
        }
        if (!roads.contains(Road(left, target))) {
            dp[i][j] += dp[i][j - 1]
        }
    }
}
```

탐색하려는 좌표에 대해서 진입할 수 있는 방향은 왼쪽과 위입니다.

왼쪽 -> 현재 좌표, 위 -> 현재 좌표로 이동할 수 있는지 확인을 한 뒤, 경우의 수를 더해주면 됩니다.