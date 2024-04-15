# Binary Search

```kotlin
이분 탐색은 결정문제의 답이 이분적일 때 사용할 수 있는 탐색 기법이다.
이때 결정 문제란 답이 Yes or No인 문제를 의미하며, 보통 1개의 파라미터를 가진다.
```

예를 들어 크기가 100000인 1차원 배열에서 각 원소가 index값인 경우에 index가 15000인 값을 찾는다고 가정해보자.

여기서 결정문제를 어떻게 잡느냐에 따라 이분탐색의 성격이 달라진다.

대다수의 경우 Ary[i] ≥ 찾으려고 하는 원소로 조건을 설정한다.

i값이 변화함에 따라 해당 조건이 참, 거짓이 정해진다.

이렇게 결정 문제의 파라미터(i)에 대해 결정 문제의 답이 두 구간으로 나뉘는 것을 이분적이라고 하며, 이분 탐색을 사용해 결정 문제의 답이 달라지는 `경계` 를 찾을 수 있다.

이분탐색은 탐색의 시작점인 left와 탐색의 끝 점인 right라는 구간을 잡은뒤, 구간의 길이를 절반씩 줄여나가면서,

left와 right가 경계지점에 위치하도록 하는것이다.

이분탐색이 끝나는 지점은 left와 right의 관계가 역전되는 시점이다.

이분탐색은 탐색 구간의 길이가 길 수록 효과적이며, 시간 복잡도는 logN을 가진다.

따라서 대다수의 조건을 만족하는 x의 최대값, 최소값을 찾는 문제는 이분탐색을 이용하는 경우가 많다.

이분탐색의 구현은 양 끝점을 통한 중간값을 통해 참인지, 거짓인지를 판단하고, 결과값에 따라 left와 right의 값을 조절한다.

```kotlin
private fun binarySearch(num: Int): Int { //num이 들어갈 수 있는 공간을 찾아야 함.
      var left = 0
      var right = ary.size-1
      while (left <= right) {
          val mid = (left + right) / 2
          if (check(mid)) { //더 클 경우
              right = mid - 1
          } else { //작거나 같을 경우
              left = mid + 1
          }
      }
      return left
  }
```

이분탐색의 경우 항상 mid는 left와 right 사이에 위치한다.

이분탐색이 끝나는 경우는 left와 right의 관계가 역전된 경우이고(left가 right보다 커진 시점),
left와 right는 결정 문제의 답이 바뀌는 경계에 위치한다.

## lower_bound  & upper bound

이분 탐색이 사용되는 대표적인 예시로, 정렬된 배열에서 특정 값 이상 또는 초과인 원소가 등장하는 첫 위치를 찾는 문제가 있다.

### lower_bound

- ary[i-1] < X ≤ary[i] 인 i를 찾아주는 함수입니다.
- ary의 원소중 X 이상이며 가장 가까운 인덱스를 반환합니다.
- 만약 모든 원소가 X보다 작다면, 마지막 칸의 다음 위치를 반환합니다.

```kotlin
fun lowerBound() {
        val X = 5
        val ary = arrayOf(1, 2, 2, 3, 4, 4, 5, 6, 7)
        var left = 0
        var right = ary.size - 1
        while (left <= right) {
            val mid = (left + right) / 2
            if (ary[mid] >= X) {
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        println("lower_bound($X) : $left")
    }
```

![](https://blog.kakaocdn.net/dn/pSZIq/btsGFDoEnab/ZsiMjm7vAfLh5m7qk3FYDK/img.png)

### upper_bound

- ary[i-1]≤ X < ary[i]인 i를 찾아주는 함수입니다.
- ary의 원소중 X 초과이며, 가장 가까운 인덱스를 반환합니다.
- 모든 원소가 X보다 작거나 같다면, 마지막 칸의 다음 위치를 반환한다.

```kotlin
fun upperBound() {
      val X = 5
      val ary = arrayOf(1, 2, 2, 3, 4, 4, 5, 6, 7)
      var left = 0
      var right = ary.size - 1
      while (left <= right) {
          val mid = (left + right) / 2
          if (ary[mid] > X) {
              right = mid - 1
          } else {
              left = mid + 1
          }
      }
      println("upper_bound($X) : $left")
  }
```

![](https://blog.kakaocdn.net/dn/bmgN4w/btsGBXWT450/mA0BPJaCsjBowA5MKIAaak/img.png)

### 내가 했던 실수

1. left와 right의 범위를 int로 설정해서, 틀린 경험이 많았다.
   이분 탐색의 경우 구간의 길이가 큰 문제에서 특히 효과를 발휘하는데, left와 right를 더해서 mid를 구하는 과정에서 오버플로우가 발생할 수 있기 때문에 고려해서 자료형을 선택하는것이 중요하다.
2. 부등호의 방향에 따라서 left와 right의 값을 변화하는것이 중요하고, 경계값인 left와 right값중 문제 요구사항에 맞게 출력하는것이 중요하기 때문에 간단하게 손으로 그려보는것이 중요하다.