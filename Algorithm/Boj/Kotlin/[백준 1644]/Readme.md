## 문제 풀이

해당 문제는 소수에 대한 판별을 최적화할 수 있는지와 투 포인터를 알고 있는지를 묻는 문제라고 생각한다.

소수에 대한 판별은 학부생 때 에라토스테네스의 체를 이용해서 최적화하는 방법은 배웠었다.

이제 시간복잡도를 계산해보자.

N\*2에 해당하는 반복문 2개로, 연속된 소수의 범위를 설정해서 합을 구할 수도 있다.

하지만 N의 최댓값이 4,000,000이고, 그에 따른 소수의 개수는 알지 못해도 소수의 개수가 100,000개만 돼도 N^2이면

10,000,000,000이라 시간 초과가 난다. 따라서 해당 알고리즘은 N or NlogN인 알고리즘을 사용해야 한다고 알 수 있다.

숫자들 중에 연속된 숫자들의 합으로 결괏값을 구성해야 하고, 복잡도가 N or NlogN인 알고리즘은 투 포인터가 있다.

투포인터는 left와 right를 이동시키면서 연속된 구간의 합을 계산할 수 있기 때문에 제격이라고 생각했다.

### 소수 만들기

```
private fun makePrimeNumber() {
    isPrime = BooleanArray(n + 1) { true }
    isPrime[1] = false
    for (i in 2..Math.sqrt(n.toDouble()).toInt()) { // 최대범위 까지
        if (isPrime[i].not()) {
            continue
        }
        for (j in i + i..n step i) { //자기 자신으로 나눌 수 있는지
            isPrime[j] = false
        }
    }
    for (i in 1 until isPrime.size) {
        if (isPrime[i]) {
            primeNumber.add(i)
        }
    }
}
```

에라토스테네스의 체를 이용해서 최적화를 하지 않는다면 소수 판별은 대부분 시간 초과가 난다.

따라서 반드시 해당 로직을 이해하고, 구현할 수 있어야 한다.

나는 투포인터로 연속된 구간을 원했기에, isPrime이라는 배열로 투포인터의 left와 right가 아닌, primeNumber라는 배열을 추가해 줬다.

만약 isPrime 만으로 투포인터를 구현했다면 해당 포인터가 소수인지, 아닌지 계속 검사해줘야 하고, 코드가 이쁘지 않다고 생각한다.

#### 투 포인터

```
private fun solve() {
    var left = 0
    var right = 0
    var sum = 0
    while (left <= right) {
        if (sum == n) {
            answer++
            sum -= primeNumber[left]
            left += 1
        } else if (sum < n) {
            if (right >= primeNumber.size) {
                break
            }
            sum += primeNumber[right]
            right += 1
        } else {
            sum -= primeNumber[left]
            left += 1
        }
    }
}
```

left와 right 포인터를 이용해서 구간의 누적합을 구한다고 생각하면 된다.

당연하게도 primeNumber의 배열은 순서대로 소수를 넣어줬기 때문에 오름차순 정렬되어 있다.

-   구간의 합이 N보다 작을 경우
    -   구간을 늘려줘야 한다. 따라서 right+=1을 한다.
-   구간의 합이 N보다 클 경우
    -   구간을 좁혀줘야 하기 때문에 left+=1을 통해서 구간의 왼쪽 범위를 줄이고, 왼쪽 범위에 해당했던 값을 뺀다.
-   구간의 합이 N일 경우
    -   left~right 까지는 N이기 때문에 구간을 좁혀줘서 새로운 구간을 찾아준다.
