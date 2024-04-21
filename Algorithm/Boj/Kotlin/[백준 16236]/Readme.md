## 문제 풀이

문제를 이해하는데 시간을 굉장히 많이 쓴 문제였다.

상어가 이동하는 조건과, 상어가 물고기를 먹는 조건, 그리고 상어를 이동시키는 방법 모두 중요했던 문제였다.

우선 내가 풀이를 위해 구현한 자료구조는 다음과 같다.

```
data class Shark(val x: Int, val y: Int, val size: Int = 2, var eat: Int = 0, var time: Int = 0)
data class Fish(val x: Int, val y: Int, val distance: Int = 0)
```

-   Shark는 아기상어를 뜻하며, 좌표와 크기, 먹은 물고기 수, 이동한 시간을 파라미터로 가진다.
-   Fish는 물고리를 뜻하며, 좌표와 거리를 파라미터로 가진다.

나는 두 데이터 클래스를 이용해서 좌표를 탐색하고, 물고기를 먹고, 상어를 이동시켰다.

상어의 이동을 결정하는 조건은 다음과 같다.

1.  더 이상 먹을 수 있는 물고기가 없다면 중지
2.  먹을 수 있는 물고기가 1마리라면 해당 칸으로 이동
3.  먹을 수 있는 물고기가 여러마리라면, 거리가 가장 가까운 물고기를 먹는다.
4.  거리가 같은 물고기가 여러개라면, 가장 위, 왼쪽의 물고기를 먹는다

여기서 내가 헷갈린 포인트는 다음과 같다.

상어의 이동방향은 상하좌우인데, 거리가 다른 물고기가 있을까였다.

내가 처음에 이해한 문제는 상어를 이동시키면서, 현재 방향에서 먹을 수 있는 물고기를 찾는 문제라고 이해했지만,

이 문제는 상어의 현재 위치에서 이동할 수 있는 모든 곳을 탐색하면서 먹을 수 있는 물고기를 찾는 문제였다.

따라서 나는 물고기의 대한 정보를 인접리스트로 저장했다.

예를 들어 Fish\[1\]\[1\]은 크기가 1인 물고기중 1번 인덱스에 해당되는 물고기의 정보이다.

```
for (i in 1..6) {
    if (i >= now.size) {
        break
    }
    for (j in 0..<fishes[i].size) {
        eatFish.add(fishes[i][j])
    }
}
```

다음과 같이 현재 상어의 정보를 바탕으로 먹을 수 있는 물고기의 수를 계산했다.

이렇게 구현한 이유는 상어가 움직이면서, 먹을 수 있는 물고기를 판단하는것은 불필요하다고 생각했다.

해당 과정을 통해서 현재 상어가 먹을 수 있는 물고기가 있는지, 없는지를 판단했다.

만약 먹을 수 있는 상어가 없다면 그때가 종료 포인트인것이다.

하지만 해당 방법의 문제점은 먹을 수 있는 물고기를 판단만하지, 거리를 계산하지 못했다.

그러기 위해서 먹을 수 있는 물고기에 대해서 BFS를 통해 거리를 계산했다.

```
val realFish = PriorityQueue<Fish>(compareBy({ it.distance }, { it.x }, { it.y }))
    while (eatFish.size != 0) {
        val fish = eatFish.removeFirst()
        val eatTime = canEating(now, fish)
        if (eatTime != Int.MAX_VALUE) {
            realFish.add(fish.copy(distance = eatTime))
        }
}
```

최종적으로 상어가 먹어야할 물고기의 우선순위는 거리가 가깝고, 위, 왼쪽에 있기 때문에 우선순위큐를 다음과 같이 구현했다.

**fish**에 대해서 현재 상어의 정보(위치, 크기)를 바탕으로 먹을 수 있는지를 판단했다.

```
private fun canEating(shark: Shark, fish: Fish): Int {
    val q = arrayListOf<Shark>()
    q.add(shark)
    var result = Int.MAX_VALUE
    val visit = Array(n) { BooleanArray(n) }
    while (!q.isEmpty()) {
        val now = q.removeFirst()
        if (now.x == fish.x && now.y == fish.y) {
            result = Math.min(result, now.time)
        }
        if (visit[now.x][now.y]) {
            continue
        }
        visit[now.x][now.y] = true
        for (d in 0..<4) {
            val mx = now.x + dx[d]
            val my = now.y + dy[d]
            if (checkRange(mx, my).not() || graph[mx][my] > now.size || visit[mx][my]) {
                continue
            }
            q.add(now.copy(x = mx, y = my, time = now.time + 1))
        }
    }
    return result
}
```

canEating은 기본적인 BFS이고, 반환값으로 물고기까지의 거리를 반환한다.

이렇게 realFish가 현재 상어가 먹을 수 있는 물고기의 후보군들이다.

여기서 먹을 수 있는 상어가 없다면 종료포인트이고, 먹을 수 있는 상어가 있다면 우선순위큐의 Top에 해당하는 물고기일것이다.

```
val fish = realFish.poll()
fishes[graph[fish.x][fish.y]].remove(fish.copy(distance = 0))
graph[fish.x][fish.y] = 0
now.eat += 1
t += fish.distance
if (now.eat == now.size) {
    q.add(now.copy(x = fish.x, y = fish.y, eat = 0, size = now.size + 1))
} else {
    q.add(now.copy(x = fish.x, y = fish.y, eat = now.eat, time = 0))
}
```

fish를 먹었다면 fishes의 배열에서 물고기를 지워주고, 전체 그래프에서 물고기의 위치를 초기화했다.

그리고 상어가 물고기를 크기만큼 먹었다면 상어의 크기를 증가시켰고, 아니라면 먹은 숫자를 그대로 q에 저장했다.

![](https://blog.kakaocdn.net/dn/eghbbu/btsGMG1EoWj/GKwaoljL6rTbqoavTPc1Hk/img.png)

코드의 결과는 2152ms였고, 생각보다 너무 느렸다.

아마 먹을 수 있는 물고기의 후보군들에 대해서 BFS를 매번 했기 때문이라고 생각했다.

시간을 줄일수있기 위해서는 한번의 탐색으로 먹을 수 있는 물고기의 후보군을 계산하고, 가장 유망한 물고기를 결정시켜줘야 했다.

따라서 canEating의 변화를 통해 해결할 수 있었다.

```
private fun canEating(shark: Shark): Fish? {
    val q = arrayListOf<Shark>()
    q.add(shark.copy(time = 0))
    val fish = PriorityQueue<Fish>(compareBy({ it.distance }, { it.x }, { it.y }))
    val visit = Array(n) { BooleanArray(n) }
    visit[shark.x][shark.y] = true
    while (!q.isEmpty()) {
        val now = q.removeFirst()
        for (d in 0..<4) {
            val mx = now.x + dx[d]
            val my = now.y + dy[d]
            if (checkRange(mx, my).not() || graph[mx][my] > now.size || visit[mx][my]) {
                continue
            }
            if (graph[mx][my] != 0 && graph[mx][my] < now.size) { //0이 아니고 사이즈가 작을때 먹을 수 있음
                fish.add(Fish(x = mx, y = my, distance = now.time + 1))
            }
            visit[mx][my] = true
            q.add(now.copy(x = mx, y = my, time = now.time + 1))
        }
    }
    if (fish.size == 0) {
        return null
    } else {
        val resultFish = fish.poll()
        graph[resultFish.x][resultFish.y] = 0
        return resultFish
    }
}
```

한번의 BFS로 현재 상어가 갈 수 있는 모든곳을 탐색했고, 먹을 수 있는 물고기를 fish에 저장했다.

fish는 우선순위큐로 거리>X좌표>Y좌표 순으로 우선순위를 결정했다.

먹을 수 있는 물고기는 fish에 저장하고, 최종적인 fish의 결과에 따라 없다면 null, 있다면 top을 반환하고, 해당 위치를 0으로 초기화 했다.

```
val result = canEating(now)
if (result != null) {
    now.eat += 1
    if (now.eat == now.size) {
        q.add(
            now.copy(
                x = result.x,
                y = result.y,
                size = now.size + 1,
                eat = 0,
                time = now.time + result.distance
            )
        )
    } else {
        q.add(
            now.copy(
                x = result.x,
                y = result.y,
                eat = now.eat,
                time = now.time + result.distance
            )
        )
    }
} else {
    return now.time
}
```

만약 result값이 null이라면 종료포인트이고, 아니라면 물고기를 먹었다는 뜻이다.

물고기를 먹었을 때, 먹은 수가 현재 상어의 크기와 일치하다면, 상어의 크기를 증가시켜주고, 아니라면 그대로 상어의 위치를 변환하고, 시간을 증가시켰다.

![](https://blog.kakaocdn.net/dn/bM7Fpx/btsGQlukROP/0wtr0b0gAi7a76Q5O1k00K/img.png)

수정한 코드로 시간을 많이 단축시켰다 ㅎㅎ..

맞힌 사람들의 코드를 보니 시간이 굉장히 빨랐다.ㅠ

아마 BFS의 로직을 최적화 하신것 같다.