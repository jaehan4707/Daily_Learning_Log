## 문제 풀이

단순하게 입력단계에서 A와 B를 구분하는 과정에서 B로는 들어왔지만 A로 한 번도 들어오지 못한 녀석이   
맨 뒤에 서야한다는것은 알았다.

![](https://blog.kakaocdn.net/dn/1ML1a/btsGksA2Ulq/XLCb86khgXa4zJD0AkYFSK/img.png)

입력에 대해서 도식화를 해보면 오른쪽과 같은 관계도가 나온다.

그림을 통해서 3가지 분류를 할 수 있다.

1.  선택을 받지 못한 사람 (1,2)
2.  선택을 하지 못한 사람(4)
3.  선택을 하고, 받은 사람(3)

당연하게도 선택을 받지 못한 사람이 가장 앞에 위치할 것이다.

문제의 조건을 보면 선택을 받지 못한 사람들 중에서의 우열은 구분할 필요가 없었다.

서야 할 위치가 결정되는 조건은 본인을 선택한 사람이 이미 줄을 선 경우였다.

그렇기에 들어오는 간선의 개수를 계산했다.

그리고 인접리스트의 형태로  A->B의 형태로 저장했다.

```
br.readLine().split(" ").map { it.toInt() }.apply {
    graph[this[0]].add(this[1]) //this[0] -> this[1]
    edges[this[1]]++ //this[1]로 들어오는 간선의 개수 증가
}
```

이후 우선순위가 가장 높은(들어오는 간선의 개수가 0인) 정점을 우선 출력해야 했기 때문에 Queue에 담았다.

```
val dq = ArrayDeque<Int>()
for(i in 1 ..  n){
    if(edges[i]==0){
        dq.add(i)
    }
}
```

ArrayDeque를 사용한 이유는 Queue가 Java 기반이기도 하고, Java Queue에 비해서 사용법이 다르지도 않고, 성능도 차이가 없어서 사용했다.

로직은 간단하다.

-    dq를 돌면서 해당 원소가 뻗고 있는 정점에 대한 간선을 검사하고 , 간선을 하나 빼준다.
    -   왜냐하면 dq를 돌면서 하나씩 줄을 세우기 때문이다.
-   간선의 개수가 0이라면 dq의 맨 앞에 넣어준다.

맨 앞에 넣어주는 이유는 간단하다. 예를 들어 1 2와 3 4가 입력으로 받았을때, dq에는 1과 3이 들어있다.

1과 3중 하나에 대해서 뻗어가는 정점의 간선을 검사하고, 2의 간선의 개수가 0이 된다.

그러면 dq에 2가 들어가야하는데, 이미 q에는 3이 들어있다.

따라서 1 -> 2의 순서를 유지하기 위해선 2가 3보다 앞에 위치해야 했고, 따라서 dq에 맨 앞에 넣어준다.

초기 앞에 넣지 않아서 1->3->2->4가 출력되었다. 따라서 dq에 넣는 순서를 보장해줘야 했고, 맨 앞에 넣어주니 해결되었다.
