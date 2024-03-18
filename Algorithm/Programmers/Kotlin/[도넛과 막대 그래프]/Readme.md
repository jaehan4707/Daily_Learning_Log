## 문제 풀이

처음에는 방문한 정점의 개수와 간선을 바탕으로 그래프의 모양을 구하려고 했지만, 그래프의 크기를 측정할 수가 없었다.

생각해 보니 각 그래프의 특징이 있었고, 해당 그래프의 특징을 파악하면 풀 수 있는 문제였다.

### 도넛 그래프
![](https://blog.kakaocdn.net/dn/bT3cha/btsFUEhEDwm/9SBSbneyFPKNEHbJSM4cx1/img.png)
**도넛 그래프는 사이클을 형성하고 있으며 N-1개의 간선으로 구성되어 있기 때문에 모든 정점이 하나의 간선을 가지고 있다.**

### 막대그래프

![](https://blog.kakaocdn.net/dn/ohYyB/btsFUDwg2ZH/kkoWT0AQ1sjz9KRtRqVA8K/img.png)

**막대그래프는 간선마다 하나씩 간선으로 연결되어 있지만, 마지막 정점은 간선이 없다는 것이 특징이다.**

사이클을 구성하지 않기 때문에 도넛 그래프의 간선보다 하나 적다.

### 8자 그래프

![](https://blog.kakaocdn.net/dn/cEzIZQ/btsFTfpdPK4/ZKKCXHZV8rWYqJTFYxe4r0/img.png)

8 자 그래프는 도넛 그래프를 여러개 합친 모양으로 도넛 그래프의 특징을 가지고 있다.

그래서 도넛 그래프와 8 자 그래프를 구별하는것이 해당 문제에서 가장 어려운점이라고 할 수 있다.

**내가 생각했던 차이는 8자 그래프도 사이클을 구성하지만 사이클을 구성하는 지점(중간지점)에서 뻗어나가는 간선이 2개라는 점이다.**

이제 각 그래프의 특징을 살폈기 때문에 추가된 임의의 한 점을 구해보자.

**원래 형성된 그래프에서 추가로 하나의 정점을 생성하고, 연결하였기에 추가된 정점으로 들어오는 간선은 없을 것이다.**

초기에는 뻗은 간선이 가장 많은 정점을 새로운 정점으로 계산했는데 특정케이스를 통과하지 못했다.

무조건 추가로 생성된 정점의 간선이 많은 건 아닌 것 같다..ㅠ (이거 때문에 1시간은 삽질했다)

추가된 정점을 구하는 방법은 간단하다.

들어오는 간선이 0인 것!

```
fun makeGraph(edges: Array<IntArray>){
    edges.forEach{ edge->
        val start = edge[0]
        val end = edge[1]
        from[end]++
        graph[start].add(end)
        maxVertex = max(maxVertex,max(end,start))
    }
}

fun findStartVertex() {
    var maxEdges = 0
    for (i in 1..maxVertex) {
        if (maxEdges < graph[i].size && from[i] == 0) {
            maxEdges = graph[i].size
            newVertex = i
        }
    }
}
```

입력받은 edge를 바탕으로 인접리스트를 구현하고, 들어오는 간선의 개수를 체크했다.

maxVertex는 이후에 최대 크기만큼의 반복문이 아닌 최대 정점의 번호만큼만 돌려주고 싶어서 따로 계산해 줬다.

다음은 연결된 그래프의 모양을 계산해야 한다.

연결된 그래프의 정점들은 각각 추가된 정점에서 연결된 지점이 출발점이다.

따라서 다음과 같이 코드를 작성했다.

```
for(i in 0 until graph[newVertex].size){
    answer[decideGraph(graph[newVertex][i])]++
}
```

출발지점에서 뻗은 정점들을 출발점으로 하여 그래프를 결정했다.

```
fun decideGraph(vertex : Int) : Int{
    if(graph[vertex].size==0){    //막대
        return 2
    }
    if(graph[vertex].size==2){ //두개로 뻗어나가는 점이 있을 경우 8자 그래프
        return 3
    }
    if(visit[vertex]){ //사이클을 돈다면 도넛
        return 1
    }
    visit[vertex]=true
    return decideGraph(graph[vertex][0])
}
```

그래프를 결정하는 것은 다음과 같다.

-   재귀를 탐색하면서 정점을 이동한다.
-   그러다 정점에 해당하는 간선의 개수가 0이라면 막대그래프일 것이다.
-   마지막으로 도넛과 8자를 가려내는 것이다.
-   도넛과 8자 모두 사이클을 형성하지만, 차이점은 사이클을 형성하는 지점의 간선의 개수가 다르다는 점이다.
-   따라서 간선의 개수를 먼저 체크하고, 사이클을 체크한다면 도넛과 8자 모양의 그래프를 구별할 수 있다.
-   사이클 체크는 visit 배열을 통해서 했다.