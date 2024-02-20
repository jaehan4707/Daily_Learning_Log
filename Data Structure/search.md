# Search

## Sequential Search(순차 탐색)

### 정의

```java
정렬되지 않은 리스트의 항목들을 처음부터 마지막까지 순차대로 검사하여 탐색하는 방법
단방향으로 진행하는 탐색 방법이기에 선형탐색(linear Search)라고 불린다.
```

### 코드

```java
public static void sequentialSearch(int goal) {
	for(int i=0;i<n; i++) {
		if(ary[i]==goal) {
			return i;
		}
	}
	return -1;
}
```

- 시간 복잡도 : `O(n)`

## Binary Search(이진 탐색)

- 사용하려면 반드시 배열이 정렬되어있는 상태여야한다.
- 배열을 시작과 끝 값을 이용해 반으로 쪼개서 값이 왼쪽에 있는지 오른쪽에 있는지를 판단해서
  탐색 범위를 반으로 좁힌다.
- 추가되거나 삭제되지 않고 변하는 고정된 데이터에서 탐색을 할 때 유용하다.

```java
public static int binarySearch(int goal) {
	int start = 0;
	int end = ary.size();
	
	int mid;
	
	while(start<=end) {
		mid = (low+high)/2;
		if(key==ary[mid])
			return mid;
		else if(key>list[mid])
			start = mid+1;
		else 
			end = mid-1;
	}
	return -1;
}
```

시간 복잡도

- 탐색 : `O(logn)`