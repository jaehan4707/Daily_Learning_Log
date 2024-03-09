## 문제 풀이

처음 문제를 보고 이렇다 할 접근 방법이 떠오르지 않았다.

초기에는 하나의 원소를 기준으로 left를 고정시키고, right를 옮겨가면서 투포인터 알고리즘으로 합이 s가 되는 조합을 찾으려고 했었다.

-7 -3 -2 5 8이 있을 경우

-   -7을 기준으로 오른쪽을 8로 고정시키고, 투포인터를 시작한다.
-   현재 포인터는 8이고, sum은 1이다.
    -   합은 s보다 크기 때문에 포인터를 left +1 인 1로 옮겨준다.
-   sum + ary[pointer] -> -2가 된다.
    -   여기서 합은 s보다 작으므로, 포인터를 현재 right보다 한 칸 적은 5로 옮겨준다.
-   sum + ary[pointer] -> 3이 된다.
    -   여기서 합은 s보다 크므로, 포인터를 left +1 인 -2로 옮겨준다.
-   sum + ary[pointer] -> 1이 된다.
    -   여기서 합은 s보다 크므로, right를 -1 하고, 포인터를 right로 옮겨준다.
-   여기서 right와 left가 교차하기 때문에 탐색을 중지한다.

이렇게 각 원소마다 n번의 right를 선정하는 과정이 있고, 선택된 right마다 투포인터를 진행하므로

시간복잡도는 O(N^2 * logN)이라고 판단했지만, 답은 아니었다.

아마다 부분집합을 계산하는 과정에서 투 포인터가 적절한 방법이 아니었거나, 모든 경우의 수를 탐색하지 못한 것으로 판단되었다.

### 초기 풀이

```
private fun selectCandidate() {
    for (i in ary.indices) {
        if (s == ary[i])
            answer += 1
        for (j in ary.size - 1 downTo i) { //i를 기준으로 j원소를 포함시켜서 합을 계산
            answer += binarySearch(i, j)
        }
    }
}

private fun binarySearch(left: Int, right: Int): Int {
    var tempLeft = left
    var tempRight = right
    var tempSum = ary[tempLeft]
    var loc = right
    while (tempLeft < tempRight) {
        tempSum += ary[loc]
        if (tempSum == s) {
            return 1
        } else if (tempSum > s) { //더 큰 경우
            tempLeft++
            loc = tempLeft
        } else { //더 작을 경우
            tempRight--
            loc = tempRight
        }
    }
    return 0
}
```

이후에 풀이를 보니 수열을 반으로 나눠서, 수열 덩어리들을 조립해가는 방법을 알게 되었고, 풀이 과정을 수정하게 되었다.

수열을 반으로 나눠서, 각 구역에 해당하는 부분수열을 전부 구하고, 수열의 합을 map에 기록한다.

이렇게 해도 되는 이유는 무식하게 40개의 부분수열을 구하는것은 시간이 오래 걸리기 때문이고, 2^20을 2번 계산하는 것이 훨씬 유리하기 때문이다.

```
private fun divideSet() {
    divide(0, n / 2, 0, leftSum)
    divide(n / 2, n, 0, rightSum)
}

private fun divide(idx: Int, limit: Int, sum: Long, result: HashMap<Long, Long>) {
    if (idx == limit) { //다 뽑을 경우
        result[sum] = result.getOrDefault(sum, 0) + 1
        return
    }
    divide(idx + 1, limit, sum + ary[idx], result)
    divide(idx + 1, limit, sum, result)
}
```

다음과 같은 방법으로 2개의 수열로 분리했다.

하나는 0~n/2 -1 까지, 나머지 하나는 n/2 ~ n까지를 쪼개서 부분수열을 모두 구했다.

재귀함수에선 수열의 원소를 포함하거나, 안 하는 방법으로 재귀를 분기해서 진행했고,

정해진 깊이까지 도달하면 그때의 합을 map에 저장한다.

map은 sum을 key로 갖고, 해당 합을 도출할 수 있는 경우의 수가 value로 저장되어 있다.

해당 과정을 통해서 왼쪽, 오른쪽 부분수열의 합과 빈도를 가지고 있는 map을 구할 수 있다.

다음으론 두 구역의 부분수열에서 합이 s가 되는 경우를 구하는 과정이다.

```
private fun merge() {
    val leftSumList = leftSum.keys.toList().sorted()
    val rightSumList = rightSum.keys.toList().sorted()
    var left = 0
    var right = rightSumList.size - 1
    while (left < leftSumList.size && right >= 0) {
        val sum = leftSumList[left] + rightSumList[right]
        if (sum.toInt() == s) { //합일 때
            answer += leftSum[leftSumList[left]]!! * rightSum[rightSumList[right]]!!
            left += 1
            right -= 1
        } else if (sum > s) {
            right--
        } else if (sum < s) {
            left++
        }
    }
}
```

leftSumList, rightSumList는 map에 key값(즉 등장한 합의 경우의 수)을 List형태로 저장하고, 오름차순 정렬한다.

오름차순 정렬한 이유는 이분탐색을 진행하기 위해서이다.

이분탐색의 진행 조건은 left와 right가 범위를 벗어나지 않는 것이다.

만약 왼쪽 부분수열의 합과 오른쪽 부분 수열의 합이 s일 경우

왼쪽 부분 수열의 key 값에 해당하는 등장 횟수(value)와 오른쪽 부분 수열의 key 값에 해당하는 등장 횟수(value)를 곱해준다.

그 이후 왼쪽, 오른쪽의 포인터를 이동시킨다.

이렇게 merge를 통해서 answer를 구할 수 있다.

하지만 왼쪽, 오른쪽 부분수열의 집합에서 원소를 선택하지 않는 공집합이라는 선택지가 중복된다.

만약 입력으로 받은 s가 0이라면 answer는 공집합을 2번 계산했기 때문에 값을 1 감소시켜줘야 한다.

-------

지금 와서 보면 leftSumList와 rightSumList를 key의 list 형태로 만드는 것이 아닌 map을 Pair <long, long> 형태의 list로 변환하는 것이 더 가독성 좋은 코드가 되었을 것 같다,, 

map의 value를 뽑는 과정에서!! 연산자를 사용했기 때문이다,, 물론 당연하게도 해당 값은 null이 될 수 없지만 저런 코드는 좋지 않은 것 같다