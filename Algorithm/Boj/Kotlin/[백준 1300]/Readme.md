## 문제 풀이

NXN의 배열을 일차원으로 압축시켜서 k번째 index의 수를 찾는 문제이다.

단순하게 NXN 배열을 만들어서 값을 i\*j 값으로 넣어주기엔 N의 최댓값이 10^5승이므로 불가능하다.

따라서 실제 배열을 만들어주지 않고, 우리는 k번째 index의 수를 찾아야 한다.

우선 사용할 수 있는 정보는 배열의 원소들이 i\*j의 형태로 계산된다는점과 A의 행렬은 NXN라는 점이다.

A 행렬의 자세한 정보는 알지 못하지만, 최솟값은 1이고, 최댓값은 NXN이다.

일단은 N을 4라고 가정하고, A와 B 행렬을 그려봤다.

A행렬

![](https://blog.kakaocdn.net/dn/PUBEO/btsGC38XmtA/2oiQWIqmQ9QURTpwYk6KV1/img.png)

B행렬

![](https://blog.kakaocdn.net/dn/cWWRaI/btsGBhgicYg/jPFKz4ei2txWRvLNnJV6Lk/img.png)

우리는 B행렬에서 k번째 index의 수를 찾아야 하고, 나는 이분탐색을 이용하기로 했다.

실제 B 행렬을 그려보니 이분탐색으로 찾을 수 있을 것이라고 판단했다.

이분탐색을 구현하기 범위가 무엇을 의미하는지, 어떻게 조절할 것인지가 중요하다.

나는 이분탐색에서 사용하는 start와 end 변수를 다음과 같이 설정했다.

-   start : A 행렬의 최소 숫자인 1
-   end : A 행렬의 최대 숫자인 n\*n

예를 들어 n이 4이고, k가 11일 경우 결괏값은 8이다.

![](https://blog.kakaocdn.net/dn/dqlYoA/btsGEhsaaF0/IcUKIRMyyI0QljKbshxIc1/img.png)

즉 B \[k\]이 X라면 X보다 작은 숫자의 개수는 k-1개이다.

이런 정보를 이용해서 이분탐색의 구현을 다음과 같이 설정했다.

1.  mid는 start와 end 합의 절반값이며, 인덱스가 아닌 숫자의 값을 의미한다.
2.  mid 값을 통해서 mid 값보다 작거나 같은 숫자의 개수를 센다. (해당 값을 cnt라고 하자)
3.  cnt가 만약 k보다 작다면, mid값이 B \[k\]의 값보다 앞에 있다는 것을 의미한다.  
    따라서 left의 값을 mid+1로 조정한다.
4.  cnt가 만약 k보다 같거나 크다면, mid 값이 B\[k\]의 값 혹은 그 뒤에 있다는 것을 의미한다.  
    따라서 right의 값을 mid-1로 조정한다.

다음은 mid보다 같거나 작은 숫자의 개수를 어떻게 행렬의 구현 없이 카운팅 할 수 있는지에 대해 먼저 얘기할까 한다.

4X4 행렬에서 8을 찾고자 할 때 다음과 같은 영역이 생긴다.

![](https://blog.kakaocdn.net/dn/FOqQs/btsGB92VQ7t/TJn1tMQSxUguHQ8KCmAoyk/img.png)

각 행에 해당하는 열의 원소들은 i\*j 값이라는 단순한 규칙을 따른다.

따라서 각 행에 대응해 찾고자 하는 원소 x의 위치를 구하고, 1부터 n까지 적용하면 된다.

8의 원소인 경우

1번 행에 대해서 작거나 같은 수의 개수가 최대 8개

2번 행에 대해서 작거나 같은 수의 개수가 최대 4개

3번 행에 대해서 작거나 같은 수의 개수가 최대 2개

4번 행에 대해서 작거나 같은 수의 개수가 최대 2개

즉 행 I에 대해서 찾고자 하는 원소 X의 위치는 X / I 임을 알 수 있다.

하지만 여기서 A 행렬은 NXN이기 때문에 위치가 N을 넘어간다면, 실제 더해질 수 있는 수는 N게임을 알 수 있다.

```
private fun checkIndex(number: Long): Long {
    var sum = 0L
    for (i in 1..n) {
        var rem = number / i
        if (rem >= n) {
            rem = n
        }
        sum += rem
    }
    return sum
}
```

마지막으로 이분탐색의 결과로 left를 반환할지, right를 반환해야 할지 고민이 있었다.

사실 left와 right 값을 둘 다 반환해 보고, 테스트케이스의 정답과 일치하는 부분을 반환했다,, ㅠ

내가 추측하기엔, lower bound와 upper bound의 개념을 이용해야 하는 것 같았다.

-   lower bound : 찾고자 하는 값 이상의 값이 처음으로 등장하는 인덱스
-   upper bound :  찾고자 하는 값 초과의 값이 처음으로 등장하는 인덱스

k값에 대해 mid보다 작거나 같은 경우의 수가 여러 개일 가능성이 있기 때문에 찾고자 하는 값 이상의 값이 처음으로 등장하는 인덱스를 구하는 lower bound를 사용해야 한다.