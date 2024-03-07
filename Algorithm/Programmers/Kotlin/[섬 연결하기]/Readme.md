## 문제 풀이

문제가 굉장히 길었지만, 실제로는 문제의 유형을 알고 있다면 쉽게 풀 수 있는 문제였다.

내가 느끼기에 문제의 유형을 파악할 수 있는 부분은 다음과 같았다.

**모든 섬을 최소한의 비용으로 연결해라!**

**다리를 여러번 건더더라도, 도달할 수 있으면 통행이 가능하다**

다음 정보를 통해서 해당 문제는 **MST(Minimum Spanning Tree)**라고 생각했다.

MST 문제도 주어진 N개의 정점을 n-1개의 edge로 최소한의 비용으로 연결하는 문제이다.

MST를  푸는 방법은 굉장히 정형화되어 있다.

1.  edge의 가중치를 기준으로 오름차순 정렬한다.
2.  간선을 하나씩 뽑아서, 연결 여부를 판단하고, 가중치를 더해준다.

2번을 진행하면서 연결 여부를 판단하는 것은 여러 방법이 있지만, 나는 Union & Find를 즐겨 쓴다.

두 Node의 부모 집합을 확인한다.

부모 집합이 같을 경우 두 노드는 다리를 여러 번 건너면 도달할 수 있다는 뜻이다.

다음 코드는 Union & FInd이다.

```
fun isConnected(from : Int, to : Int) : Boolean{ 
    val fromParent = findParent(from) //from의 부모 찾기
    val toParent = findParent(to) //to의 부모 찾기        
    if(fromParent==toParent){ //부모가 같다면 이미 연결된것.
        return false
    } 
    //Union
    if(fromParent<toParent){
        parent[toParent] = parent[fromParent]
    } else{
        parent[fromParent] = parent[toParent]
    }
    return true
}

fun findParent(vertex : Int) : Int{
    if(vertex == parent[vertex]){ //자기 자신이 부모일 경우
        return vertex
    }
    parent[vertex] = findParent(parent[vertex])
    return parent[vertex]
}
```

-   두 정점의 최상단 부모를 찾아준다.
    -   재귀함수를 통해서 부모의 값을 찾아낸다. 
    -   해당 값이 동일하다면 집합이 같다.
-   집합이 다르다면, 두 집합의 부모를 일치시켜 준다. 일치시켜 주는 작업은 연결을 의미한다.
    -   부모에 대한 일치는 항상 같은 집합이라면 작은 값이 최상단에 위치하도록 한다.
    -   따라서 큰 부모에 해당하는 값을 작은 부모로 일치시킨다.