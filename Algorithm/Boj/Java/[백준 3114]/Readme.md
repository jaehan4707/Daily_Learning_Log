# 문제 풀이
정해는 아니지만, 나는 dfs + dp로 문제를 풀었다.   
경로를 탐색해가면서, 계속 경로에 따른 바나나와 사과의 값을 더해줬다.   
그러기 위해서 좌표별 사과의 누적합과 바나나의 누적합을 계산해준다.   
바나나의 값은 y좌표를 기준으로 현재 좌표의 누적합에서 최대 좌표의 누적합을 아래와 같이 계산하면 된다.
```java
banana = bananaSum[x][c] - bananaSum[x][y];
dp[x][y] = Math.max(dp[x][y],dfs(x+1,y+1) + banana);
```
사과의 값은 x좌표를 기준으로 아래와 같이 계산하면 된다.   
사과는 아래의 영역이기 때문에 바나나의 값의 계산과 달라서 생각하기가 어려웠다.
```java
apple = appleSum[r][y] - appleSum[x][y];
dp[x][y] = Math.max(dp[x][y],dfs(x+1,y+1) + apple);
```
대각선은 위 값을 더해가면 된다.
```java
apple = appleSum[r][y] - appleSum[x][y];
banana = bananaSum[x][c] - bananaSum[x][y];
dp[x][y] = Math.max(dp[x][y],dfs(x+1,y+1) + apple+banana);
```

접근하기가 어려웠던 문제였고, 충분한 고민을 하게 만든 문제라서 좋은 문제라고 생각한다.