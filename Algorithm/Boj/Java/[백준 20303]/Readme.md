## 🔎문제 풀이

해당 문제는 문제를 읽고 어떤 알고리즘을 사용해서 접근해야 할지 바로 알아야 한다.

**친구들의 사탕을 모조리 뺏어버린다** 해당 문구를 통해서 유니온 파인드를 통해서 모든 노드의 부모 노드를 설정해야 함을 알 수 있다.

주어진 친구들의 관계를 통해서 묶음으로 친구들을 분류해야 한다.

그리고 얻을 수 있는 사탕의 최대 수는 DP로 접근하면 된다.

그러기 위해선 부모 노드를 자기자신으로 초기화해야 한다.

#### 부모 노드 초기화

```
for (int i = 1; i <= n; i++) {
        c[i] = Integer.parseInt(st.nextToken());
        ary[i] = i; // 자기 자신을 일단 넣어둠.
}
```

다음은 Union & Find를 통해서 부모노드를 설정해줘야 한다.

부모노드를 결정하는 방법은 많은 방법이 있지만 A와 B가 있다면 더 작은 값이 부모로 설정되는 방법이 많이 사용된다.

예를 들어서 1번 노드와 3번 노드가 연결되어 있다면 3번 노드의 부모를 1번 노드로 결정짓는다.

#### Union & Find

```
public static void union(int a, int b) {
    int rootA = find(a); // a의 부모 찾기
    int rootB = find(b); // b의 부모 찾기
    if (rootA == rootB)
        return;
    if (rootA < rootB) { // B의 부모가 크다면
        ary[rootB] = rootA;
    } else {
        ary[rootA] = rootB;
    }
}
public static int find(int o) {
    if (ary[o] != o) { // 연결되어있다면
        ary[o] = find(ary[o]);
        return ary[o];
    }
    return o;
}
```

find를 통해서 해당 노드의 부모를 찾는다.

그리고 Union에서 두 노드의 부모를 비교하고, 부모를 작은 노드로 설정한다.

이렇게 모든 친구들의 관계를 정리하고 나서, 부모 노드들의 집합에 해당하는 사탕의 수와 사람의 수를 구해야 한다.

#### 집합 계산하기

```
public static void sumCandy() {
    for (int i = 1; i <= n; i++) {
        int parent = find(i);
        s.add(parent); // 집합에 부모 추가하기.
        people[parent]++;
        totalCandy[parent] += c[i];
    }
}
```

1번부터 n번까지의 아이들이 있을 것이고, 그 아이들의 최상단 노드가 있을 것이다.

최상단 노드를 find를 통해서 찾아주고,

부모노드의 집합인 s에 부모를 넣어준다.

s는 set으로 설정해서 중복을 없애준다.

그리고 부모 노드에 대한 사람의 수를 증가시켜 주고, 사탕의 수를 더해준다.

이렇게 되면 부모 노드에 해당하는 집합에 사람의 수와 사탕의 수가 people과 totalCandy에 들어가게 된다.

#### DP 계산하기

```
public static void solution() {
    ArrayList<Integer> temp = new ArrayList<Integer>(s);
    dp = new long[temp.size()+1][k];
    for (int i = 1; i <= temp.size(); i++) {
        int nowPeople = people[temp.get(i - 1)];
        int nowCandy = totalCandy[temp.get(i - 1)];
        for (int j = k - 1; j >= 0; j--) {
            if (j - nowPeople >= 0) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - nowPeople] + nowCandy);
            } else {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    answer = Math.max(answer, Arrays.stream(dp[temp.size()]).max().getAsLong());
}
```

set에는 index로 접근하지 못하기 때문에 set의 원소를 ArrayList에 그대로 넣어준다.

dp [i][j]는 i번째 집합까지 이용하고 j 인원을 사용했을 때의 사탕의 최댓값이다.

즉 dp [1][2]는 첫 번째 집합에서 2명의 인원의 사탕을 뺏을 때의 사탕의 최댓값이다.

해당 코드는 배낭문제와 굉장히 흡사하다.

사탕을 뺏지 못하는 경우는 dp[i-1][[j]를 그대로 끌어서 사용한다.

그리고 사탕을 뺏는 경우는 사탕을 뺏지 않는 경우와 사탕을 뺏을 때의 결괏값을 비교해서 최댓값을 갱신한다.

계산을 끝내고 최종적인 dp 배열의 최대값을 answer 값으로 갱신한다.