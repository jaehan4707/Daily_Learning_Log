## 문제 풀이

문제를 풀다가 어려워서 알고리즘 분류를 봤고, 비트마스킹이 적혀있었다.

저번에 풀었던 외판원 순회라는 문제도 비트마스킹을 사용했고, 비트마스킹을 통해서 도시의 방문처리를 했던 것을 토대로,

숫자들을 사용했는지, 안했는지를 **비트마스킹**을 통해서 해결했다.

**우리는 0~9까지의 숫자를 반드시 하나 이상 사용해야 한다.**

이러한 정보를 비트마스킹으로 저장을 한다는 얘기이다.

예를 들어

숫자가 1일경우 visit -> visit | 1<<1 -> 2

숫자가 12일경우 visit-> visit | 2<<1 -> 6

이렇게 숫자를 사용했는지에 대한 여부를 visit 배열이 아닌 비트마스킹으로 해결할 수 있다.

**n개의 길이만큼 숫자를 만들었다면 visit를 통해서 숫자를 전부 사용했는지 검사해야 한다.**

```
if (idx == n) {
    if (visit == (1 << 10) - 1) {
        return 1;
    }
    return 0;
}
```

0~9까지의 숫자를 모두 사용했다면 visit는 111111111(2^10 -1)이고, 일치하다면 계단수이고, 아니라면 0이다.

다음은 DP 점화식이다.

초기 DP는 다음과 같이 설계했다.

> DP [i][j] : i는 길이, j는 visit(방문현황)

하지만 해당 DP 배열로는 계단수에 대한 조건인 인접한 숫자를 계산하고, 정보를 저장하기에 부족함이 있었다.

따라서 수정된 DP는 다음과 같다.

```
DP[i][j][k] : i-> 숫자의 길이, j->방문현황, k->마지막 숫자
```

즉 DP[1][visit][1] = 숫자의 길이가 1이고, 방문현황은 visit이고, 마지막 숫자는 1이라는 소리이다.

해당 경우에는 아마 visit가 2일 것이다.

이전 정보를 이용해서 DP [2][6][2]는 숫자의 길이가 2이고, 방문현황은 (110 -> 6), 마지막 숫자는 2이므로, 숫자는 12가 될 것이다.

```
if (dp[idx][visit][num] != 0) {
    return dp[idx][visit][num];
}
```

이렇게 계산한 dp배열을 통해서 메모이제이션을 이용해서 최대한 연산의 수를 줄일 수 있다.

다음은 숫자를 이어 붙이는 코드이다.

```
for(int i = 0; i<=9; i++){
    int next = 1 << i;
    if(Math.abs(num-i)!=1)
        continue;
    dp[idx][visit][num] += solution(idx+1,i,visit|next);
    dp[idx][visit][num]%=mod;
}
```

-   반복문의 범위는 0~9로 사용할 수 있는 숫자를 의미한다.
-   num은 현재 마지막에 붙인 숫자를 의미하며 해당 숫자와 절댓값의 차이가 1인 숫자만 계산을 진행한다.
-   next가 유효한 숫자라면 visit를 next와 합쳐준다.
    -   예를 들어 visit가 2이고, i가 2, next가 4인 경우 visit는 6이 되는 것처럼 next를 방문처리하는 과정이다.

계단수를 쭈욱 적어보면 발견할 수 있는 사실이 있다.

처음에는 무조건 9로 시작해서 987654321~의 경우만 있는 줄 알았는데,

자릿수가 늘어날 때마다 앞에 등장할 수 있는 숫자의 종류가 늘어났다.

예를 들어 n이 11일 경우 10123456789라는 계단수를 만들 수 있다.

따라서 계단수의 앞자리를 고정하는 것이 아닌 1~9까지의 모든 경우의 수를 판단하는 것이 맞다고 생각했다.

```
for (int i = 1; i <= 9; i++) {
    result += solution(1, i, 1 << i);
    result %= mod;
}
```

-   숫자가 1로 시작한다면 visit는 2, 2로 시작한다면 visit는 4, ~~ 숫자가 9로 시작한다면 visit는 512가 될 것이다.

**해당 문제는 비트마스킹을 통한 방문 처리와 DP 배열 설계가 중점인 문제인 것 같다.**