## 문제 풀이

주어진 배열에서 최소한의 크기의 종류로 배열을 구성하는 문제이다.

그러기 위해서 크기별 개수를 기록할 필요가 있다.

#### 크기별 개수 기록

```
fun makeSubTotal(tangerine : IntArray){
    tangerine.forEachIndexed{ index, _ ->
        fruits[tangerine[index]]++
    }        
}
```

나는 귤의 크기를 index로 가지는 배열을 만들어서 귤의 크기별 개수를 기록했다.

다음으론 귤의 크기별 개수가 많은 순서대로, 즉 크기별 내림차순으로 정렬을 했다.

#### 크기 별 내림차순 정렬

```
private val q = PriorityQueue<Pair<Int,Int>>(compareBy{-it.first})
fun sortFruitBySum(){
    fruits.forEachIndexed{ index, sum->
        if(sum!=0){
            q.add(Pair(sum,index))
        }
    }
}
```

크기 별 내림차순으로 정렬되는 우선순위큐에 기록한 귤의 크기와 개수를 넣어줬다.

지금 와서 생각해 보니 새로운 큐를 생성하는 것보단 기존 fruits 배열을 정렬하는 코드가 더 괜찮았을 것 같다.

다음으론 이제 박스에 담을 일만 남았다.

당연하게도 담을 수 있는 양만큼 최대한으로 귤을 담아줘야 한다.

여기서 담는 귤은 개수가 가장 많은 종류에 해당될 것이다.

예를 들어서 크기가 1인 귤이 5개, 크기가 2인 귤이 4개, 크기가 3인 귤은 3개라고 가정할 때,

해당 과정에선 1을 먼저 담고, 다음은 2, 다음은 3처럼 개수가 많은 귤부터 담는다.

최대한 많은 개수의 귤을 처리해야 뽑을 수 있는 귤의 개수가 줄고 이는 곧 담을 귤의 종류가 적어지기 때문이다.

#### 귤 담기

```
while(!q.isEmpty()){             
    val now = q.poll()
    if(remain <= 0){                
        break
    }          
    remain -= now.first
    answer++            
}
```

귤을 담고, 뽑을 수 있는 개수에서 뺀 뒤 answer(종류)를 +1 한다.

만약 잔여개수가 0 이하라면 더 이상 뽑지 못하므로 종료한다.