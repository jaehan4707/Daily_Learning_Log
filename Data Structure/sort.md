# Sort

# 비교 정렬 알고리즘

---

## Comparison sort(비교 정렬)

- 데이터 간의 상대적 크기 관계만을 이용해서 정렬하는 알고리즘

## 기본 정렬 알고리즘

- `Selection Sort` , `bubble sort`, `insertion sort` 가 있다.
- 시간 복잡도는 `O(n^2)`에 해당한다.

### Selection Sort(선택 정렬)

> 전체 입력 배열에 대해 차례대로 최대값 또는 최소값을 선택 하여 마지막 원소와 자리를 교환하여 정렬하는 알고리즘
>

```jsx
public static void selectionSort(){
	for(int i=0; i<n; i++){
			int minIdx=i;
			for(int j=i+1; j<n;j++){
					if(ary[j]<ary[minIdx]){
							minIdx=j;
					}
			}
			int temp = ary[i];
			ary[i] = ary[minIdx];
			ary[minIdx] = temp;
	}
}
```

동작 과정

- 각 루프마다 조건에 부합하는 원소를 찾는다
    - 오름차순이면 최소 원소, 내림차순이면 최대 원소
- 찾은 원소와 자리를 교환한다.
    - 오름차순이면 맨 앞, 내림차순이면 맨 뒤

수행 시간

- 시간복잡도는 `O(n^2)`이다.
    - n-1 + n-2 + … 1 = n(n-1)/2

---

### Bubble Sort(버블 정렬)

> 연속된 인덱스의 숫자를 비교하여 큰 수를 뒤 쪽으로 이동시키는 과정을 반복하여 정렬하는 알고리즘
>

```java
public static void bubbleSort() {
		for(int i=n-1; i>0; i--) {
			for(int j=0; j<i; j++) {
				if(ary[j]>ary[j+1]) {
					int temp = ary[j];
					ary[j] = ary[j+1];
					ary[j+1] = temp;
				}
			}
		}
	}
```

동작 과정

- 현재 비교하려는 인덱스와 다음 인덱스 값을 비교한다.
- 오름차순인지, 내림차순인지에 따라 정렬조건이 바뀐다.
    - 오름차순 : 수가 더 작다면 교환한다.
    - 내림차순 : 수가 더 크다면 교환한다.

수행 시간

- n-1 + n-2 + … 1 → `O(n^2)`

---

### Insertion Sort(삽입 정렬)

> 배열을 정렬된 부분(앞)과 정렬이 되지 않은 부분(뒤)로 나누고, 정렬이 되지 않은 부분의 원소를 정렬된 부분의 적절한 위치에 삽입하여 정렬하는 알고리즘
>

```java
public static void insertionSort() {
		for (int i = 1; i <= n; i++) {
			for (int j = i; j > 0; j--) {
				if (ary[j] > ary[j - 1]) {
					int temp = ary[j-1];
					ary[j-1] = ary[j];
					ary[j] = temp;
				} 
				else {
						break;
				}
			}
		}
	}
```

- 삽입을 위한 비교는 최악의 경우 i-1번 발생할 수 있고, 최고의 경우는 1번만 비교하는것이다.
- 최악의 경우는 `O(n^2)`
- 최고의 경우는 `O(n)`

---

## 고급 정렬 알고리즘

- `Merge Sort` , `Quick Sort` , `Heap Sort` 가 있다.
- 시간 복잡도는 `O(nlogn)`에 해당한다.

### Merge Sort

> 분할정복을 이용한 정렬로, 배열의 절반을 나눠서 정렬하고, 정렬된 두 개의 배열을 합쳐서 전체를 정렬하는 방식이다.
>

```java
public static void mergeSort(int left, int right) {
		if(left<right) {
			int mid = (left+right)/2;
			mergeSort(left,mid);
			mergeSort(mid,right);
			merge(left,mid,right);
		}
	}
	public static void merge(int left, int mid, int right) {
		int l = left, m = mid+1;
		int idx = left;
		
		while(l<mid+1 && m<right+1) {
			if(ary[l]<ary[m]) {
				mergeAry[idx++] = ary[left++];
			} else {
				mergeAry[idx++] = ary[mid++];
			}
		}
		
		while(i<mid+1) {
			mergeAry[idx++] = A[l++];
		}
		while(m<right+1) {
			mergeAry[idx++]= A[m++];
		}
		for(int i=left; i<right+1; i++) {
			ary[i] = mergeAry[i];
		}
	}
```

동작 과정

- 재귀를 기준으로 배열을 반으로 나눈다.
    - 반으로 반으로 나눠서 더이상 나누지 못할 경우 merge 함수를 호출한다.
- 정렬은 하나의 덩어리 내에서 왼쪽과 오른쪽이 최대 범위를 넘을때까지 정렬한다.
- 그 후 남은 부분에 대해서 배열을 채워준다.

수행 시간

- `O(nlogn)`

---

### Quick Sort(퀵 정렬)

```java
public static int partition(int left, int right) {
		int pivot = ary[right], idx = left-1;
		for(int i=left; i<right; i++) {
			if(ary[i]>pivot) {
				idx++;
				if(i !=idx) {
					swap(ary[i],ary[j]);
				}
			}
		}
		swap(ary[right],ary[idx+1]);		
	}

	public static void quickSort(int left, int right) {
		if(left<right) {
			int mid = partition(left, right);
			quickSort(left, mid-1);
			quickSort(mid+1, right);
		}
	}

```

동작 과정

1. pivot을 하나 선택한다.
2. pivot을 기준으로 양쪽에서 pivot보다 큰 값, 혹은 작은 값을 찾는다.
    - 왼쪽에서부터는 pivot보다 큰 값을 찾고, 오른쪽에서부터는 pivot보다 작은 값을 교환한다.
3. 양 방향에서 찾은 두 원소를 교환한다.
4. 종료조건은 왼쪽과 오른쪽이 교차하는 구간이다.
5. 엇갈림 기점을 기준으로 두개의 배열로 나눠서 분할하고 합친다.

특징

- 최악의 경우 `O(N^2)`으로 다른 정렬 알고리즘보다 느리지만, 평균적으로 Merge Sort, Heap Sort보다 빠르다
    - 최악의 경우는 이미 정렬된 배열을 정렬하려고 할 때이다.
- 평균적으로 `O(nlogn)` 의 시간복잡도를 가진다.
- 추가적인 배열을 사용하지 않아 메모리 소비양이 적다.

```java
Merge Sort와 동일하게 분할 정복 알고리즘을 기반으로 진행되지만, 다른점은 
Merge Sort는 하나의 리스트를 절반으로 나누는 것이고, 퀵 정렬의 경우 선택된 pivot의 값에 
따라 작은 부분, 큰 부분으로 나눈다.
```

---

### Heap Sort(힙 정렬)

> Heap 자료구조를 이용한 정렬 알고리즘으로 주어진 입력 데이터를 힙으로 만든 다음 하나씩 힙에서 제거하면서 정렬한다.
>

Heap?

- 완전 이진 트리이면서 아래의 조건을 만족하는 자료 구조
    - min heap : 각 노드의 값이 자식 노드의 값보다 작거나 같다.
    - max heap : 각 노드의 값이 자식 노드의 값보다 크거나 같다.

![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/d13e9a60-01e7-4f42-8270-806ee50fcee1)

- 최악의 경우 `O(nlogn)`의 시간복잡도를 가진다.

```java
static void hearypSort(int n) {
		marykeHearyp(n);
		for (int i = n; i > 1; i--) {
			swaryp(ary[i], ary[1]);
			hearypify(1, i - 1);
		}
	}

	static void marykeHearyp(int n) {
		for (int i = n / 2; i > 0; i--) {
			hearypify(i, n);
		}
	}

	static void hearypify(int d, int n) {
		int left = 2 * d, right = 2 * d + 1, max;
		
		if(right<=n) {
			max = (ary[left]>ary[right]) ? left : right;
		}
		else if(left<=n) {
				max=left;
		}
		else 
			return;
		
		if(ary[maryx]>ary[d]) {
			swaryp(ary[max],aryry[d]);
			hearypify(max,n);
		}
	}
```

- 최악의 경우에도 `O(nlogn)`으로 유지가 된다.
- 힙의 특징상 부분 정렬을 할 때 효과가 좋다
- 일반적인 `O(nlogn)` 정렬 알고리즘에 비해 성능은 약간 떨어진다.


![image](https://github.com/jaehan4707/Daily_Learning_Log/assets/99114456/ad7e6bf1-77c8-47a2-9952-252efb6b415f)