## 문제 풀이

문제를 읽어보면 미사일을 최소로 사용해서 모든 폭격 미사일을 요격하려 한다라고 적혀있다.

이를 통해 해당 문제는 선택지마다 최선의 선택을 해야 하는 그리디 문제이다.

항상 그리지 문제를 풀기전에 어떻게 최선의 선택을 도출할까 가 가장 고민되고, 어려운 부분이다.

배낭문제와 같이 보통의 그리디문제는 주어진 배열을 정렬해서, 최선의 선택을 하기 위한 사전작업을 한다.

입력받은 구간에 대해서 나는 끝나는 구간을 기준으로 오름차순 정렬했다.

기존의  [[4,5],[4,8],[10,14],[11,13],[5,12],[3,7],[1,4]] 배열을 다음과 같이 정렬했다.

[[1,4], [4,5], [3,7], [4,8], [5,12], [11,13], [10,14]]

사실 정렬하는 조건은 시작구간을 해도 상관없을 것 같다.

하지만 끝나는 구간으로 정렬을 하면 다음 구간에 대해서 검사하기 더 편하다.

처음 구간인 [1,4]에 해당하는 구간에 미사일을 위치시킨다.

그러면 우리는 1~4 미만인 구간을 커버할 수 있다.

다음 구간의 시작점과 우리가 미사일을 설치한 끝구간을 비교해야 한다.

[4,5] 구간을 검사해야 하는데, 1~4 내의 구간은 4~5를 격추시키지 못한다.

문제에서 개구간 (s, e)에서 요격하는 미사일을 격추할 수 없다는 조건 때문이다.

따라서 다음 구간의 [4,5]를 커버할 수 있는 미사일을 설치한다.

그러면 우리는 1~5의 구간을 커버할 수 있다.

다음 구간인 [3,7], [4,8]도 설치된 미사일로 요격할 수 있다.

[5,12] 구간은 설치된 미사일로 요격할 수 없기에 5~12를 커버하는 미사일을 설치한다.

뒤에 이어지는 [11,13], [10,14]도 5~12구간에 걸치기 때문에 모두 커버가능하다.

즉 요약하면 다음과 같다.

1.  구간의 끝나는 지점을 기준으로 오름차순 정렬한다.
2.  구간을 하나씩 검사하면서, 미사일을 설치한다.
    -   이때 미사일의 커버 범위는 해당 구간의 끝지점일 것이다.
3.  만약 다음 구간의 시작이 설치한 미사일의 끝지점 이상이라면 미사일을 또 설치하고, 커버 범위를 업데이트한다.
4.  2~4를 반복한다.