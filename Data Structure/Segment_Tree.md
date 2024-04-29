# Segment Tree

- 여러 개의 데이터가 존재할 때 특정 구간의 합(최솟값,최대값, 곱 등)을 구하는데 사용하는 자료구조이다.
- 트리 종류에 하나로 이진 트리의 형태이며, 특정 구간의 합을 가장 빠르게 구할 수 있다는 장점이 있다.[ Olog(N) ]

예시로 [1,2,3,4,5,6,7,8,9,10] 배열이 있다고 가정하자.

위 배열에 대해 특정 구간의 합을 구하기 위해서는 인덱스를 더하면서 누적합을 계산하기 때문에 시간복잡도는 O(N)이다. O(N)이라는 시간은 빠른 속도가 아니고, 이를 위해 등장한것이 세그먼트 트리이다.

세그먼트 트리는 이진트리이며, 이는 간단한 1차원 선형배열로 구현할 수 있다.

- parent index : i
- left child index : 2 x i
- right child index : 2 x i + 1

각 노드에는 배열의 구간 합이 저장되어 있다.

리프노드 (자식이 없는 노드)는 배열의 값들이 저장되어있고, 다른 노드에는 자식 노드의 합이 저장되어 있다.

그렇다면 트리의 크기는 어떻게 할당할까?

우리는 원소가 N개인 배열을 이진트리로 구현하고자한다.

N과 가장 가까운 제곱수로 트리의 크기를 할당해야하지만, 통상적으로는 트리의 크기를 4N으로 할당한다.

세그먼트 트리에는 세가지 작업이 있다.

### Build

- 세그먼트 트리를 만드는 작업을 한다.
- 재귀를 사용해 구현한다.
- 트리를 순회하면서 작업을 진행하는데, 리프 노드라면 배열의 원소를 저장하고, 아니라면 구간의 정보를 저장한다.

코드

```jsx
private fun buildSegmentTree(nodeIdx:Int, start:Int, end: Int){
		if(start==end){
			sTree[nodeIdx] = ary[start]
			return
		}
		
		val mid = (start+end) / 2
		buildSegmentTree(nodeIdx*2,start,mid) // -> left child
		buildSegmentTree(nodeIdx*2+1,mid+1,end) // -> rightChild
		sTree[nodeIdx] = sTree[nodeIdx*2] + sTree[nodeIdx*2+1]
		return
}
```

### Query

- 세그먼트 트리에서 구간 정보를 가져온다.
- 트리를 순회하면서 노드가 나타내는 범위를 검사한다.
    - 노드가 나타내는 범위가 지정된 범위 밖에 있다면 0을 반환한다.
    - 노드가 나타내는 범위가 지정된 범위 내에 있다면 값을 반환한다.
    - 노드가 나타내는 범위가 지정된 범위 일부만 포함한다면 왼쪽 자식과 오른쪽 자식의 합을 반환한다.

```jsx
private fun query(nodeIdx:Int, start:Int, end:Int, left: Int, right: Int){			
			if(right < start || end < left){
					return 0
			}
			
			if(left<=start && end <= right){
					return sTree[nodeIdx]	
			}
			
			val mid = (start + end) / 2
			val leftChild = query(nodeIdx*2, start, mid, left, right)
			val rightChild = query(nodeIdx*2+1, mid+1, end, left, right)
			return leftChild+rightChild			
}
```

### Update

- A[index] 값을 변경하는 작업을 한다.
- update는 index가 포함된 구간을 담당하는 노드들만 변경한다.
- 3번 인덱스의 값을 변경한다면 트리를 순회하면서 구간에 대해 3번을 포함하는 값들을 수정해야한다.

```jsx
private fun update(nodeIdx:Int, start:Int, end: Int, targetIdx:Int, value : Int){
	if(start==end){
		sTree[nodeIdx] = value
		return
	}
	val mid = (start+end) / 2
	if(start<=mid && index<=mid){
		update(2*nodeIdx, start,mid,targetIdx,value)
	} else {
		update(2*nodeIdx+1,mid+1,end,targetIdx,value)
	}
	
	sTree[nodeIdx] = sTree[nodeIdx*2] + sTree[nodeIdx*2+1]
	return
}
```

변화량을 기준으로 Update

- A[index] 값의 변화 차이를 Index를 포함한 노드에 모두 더해준다.
- 위 코드보다 훨씬 간단함

```jsx
private fun updateWithDiff(nodeIdx:Int, start:Int, end:Int, targetIdx:Int, diff:Int){
	if(targexIdx < start || targetIdx>end){ // targetIdx를 포함한 구간이 아닐 경우
			return
	}
	sTree[nodeIdx] += diff
	
	if(start!=end){ // 자식 노드가 아닌 경우 1
			val mid = (start + end) / 2
			update(nodeIdx*2,start,mid,targexIdx,diff)
			update(nodeIdx*2+1,mid+1,end,targetIdx,diff)
	}
	return
```