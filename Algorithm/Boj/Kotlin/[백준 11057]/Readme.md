## 문제 풀이

모든 자리가 오름차순 혹은 같은 수로 이루어져야한다.   
그를 위해서 마지막 자리의 숫자를 기록할 필요가 있다.
![img.png](https://blog.kakaocdn.net/dn/Zm5Wu/btsHDy8wBLw/p3Sjmbn4c3geIfudo1Z52k/img.png)

기록하다보면 규칙을 발견할 수 있다.   
즉 위와 왼쪽에서의 경우의 수를 합하면 현재 합이 나온다는점이다.   
이를 바탕으로 이중 포문을 구성하고, dp[n]의 합을 계산하면 된다. 