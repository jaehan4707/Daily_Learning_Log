# 문제 풀이

문제를 읽고, 바로 디큐를 사용해서 풀면 쉽게 풀 수 있을것이라고 생각했다.   
주어진 패턴은 다음과 같다.

1. 앞에 거를 뒤에 붙이기
2. 뒤에 거를 앞에 붙이기
3. A 배열을 B 배열 앞에 그대로 붙이기
이런 패턴을 분석하면 앞이나 뒤에서 접근할 수 있고, 빼는것이나 더하는것도 자유로운 Deque를 사용했다.


1번은 removeFirst -> addLast
2번은 removeLast -> addFirst
3번은 A 배열을 역순으로 빼서, B배열 앞에 붙이면 되지만, 문법이 헷갈려서 조금 무식한 방법을 사용했다.   

addAll은 순서 그대로 붙이는것이고, B 배열을 A 배열 그대로 붙이기 위해 a.AddAll(b)를 사용해 순서를 정립했다.