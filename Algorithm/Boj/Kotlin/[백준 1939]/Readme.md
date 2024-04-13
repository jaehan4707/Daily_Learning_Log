## 문제 풀이

출발지부터 도착지점까지 옮길 수 있는 중량의 최댓값을 구하는 문제이다.

해당 문제를 풀 수 있는 방법은 여러가지가 있다.

프림과 크루스칼을 통해서 start -> end까지의 최소 비용을 계산하는 방법도 있고,

이분탐색을 통해서 중량을 설정하고, 옮길 수 있는지에 대한 여부를 통해 mid 값을 조절하는 방법이 있다.

나는 이분탐색을 통해서 해당 문제를 풀었다.

#### 간선 저장하기

처음 풀이에는 n*n 배열의 간선의 가중치를 저장했지만, 메모리 초과를 확인할 수 있었다.

메모리 초과를 피하기 위해서 인접행렬을 사용해 간선의 정보를 저장했다.

인접행렬에 원소에는 정점과 가중치가 필요로 했기에, 추가적으로 데이터 클래스를 구현했다.

```
data class Edge(val vertex: Int, val weight: Long)
private lateinit var graph: Array<ArrayList<Edge>>
```

graph[i]의 Edge는 정점 i에서 Edge의 vertex까지의 가중치가 weight를 의미한다.

```
repeat(m) {
    br.readLine().split(" ").map { it.toInt() }.apply {
        graph[this[0]].add(Edge(this[1], this[2].toLong()))
        graph[this[1]].add(Edge(this[0], this[2].toLong()))
        maxWeight = max(maxWeight, this[2].toLong())
    }
}
```

입력과정에서 양방향 그래프이기 때문에 다음과 같이 넣어줬다.

그리고 이분탐색의 최댓값을 설정하기 위해 maxWeight를 계속 구해줘서 뒤 이분탐색을 실행할 때, 최적화를 했다.

#### 이분탐색으로 거리 조절하기

```
private fun solve(): Long {
    var left = 1L
    var right = maxWeight
    while (left <= right) {
        val mid: Long = (left + right) / 2
        //운반할 수 있는지 없는지 찾아야 함
        if (isTransfer(mid)) { //배달가능
            left = mid + 1
        } else { // 배달 불가능
            right = mid - 1
        }
    }
    return right
}
```

우리는 출발지에서 도착지점까지 옮길 수 있는 중량을 찾기 위해서 나는 이분탐색을 선택했다.

시간의 여유가 있다면 각 엣지마다의 무게를 전체 리스트로 사용해서 검사할 수 있지만, 해당 방법은 시간이 오래 걸린다.

그러기 위해서 중량을 선택하는 과정을 logN 알고리즘인 이분탐색으로 결정하는 것을 택했다.

while문이 종료된다면, 중량에 대한 탐색이 끝났다는 의미이고, 그때의 right값을 반환한다.

여기서 right는 운반할 수 있는 최대의 중량을 의미한다.

#### 옮길 수 있는지 체크하기

```
private fun isTransfer(weight: Long): Boolean {
    val q = arrayListOf<Int>()
    val visit = BooleanArray(n + 1)
    visit[startFactory] = true
    q.add(startFactory)
    while (q.isNotEmpty()) {
        val now = q.removeFirst()
        for (i in 0 until graph[now].size) {
            val nowVertex = graph[now][i]
            if (nowVertex.weight < weight || visit[nowVertex.vertex]) { //비용보다 비싸다면 no
                continue
            }
            if(nowVertex.vertex==endFactory){
                return true
            }
            visit[nowVertex.vertex] = true
            q.add(nowVertex.vertex)
        }
    }
    return false
}
```

출발지부터 도착지까지 옮길 수 있는지 체크하는 것은 BFS를 사용했다.

옮길 수 있는 조건은 최대 중량인 weight를 통해서 해당 간선의 가중치가 weight 이하인 정점들만 큐에 넣어줬다.

단순하게 도착할 수 있는지, 아닌지만 확인하면 되기 때문에 도착지점을 발견한다면 true를 반환하도록 했다.