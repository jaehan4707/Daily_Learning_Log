# 문제 풀이

처음 문제를 보고, 이해를 하기 어려웠다.
문제에서 요구하는것은 건물의 높이가 바뀔 때 마다를 감지하는 것이다.

따라서 스택을 사용해서, 건물의 높이를 저장한다.

반복문을 돌면서, 현재 건물의 높이가 스택의 최상단 값보다 낮을 경우 이는 현재 건물이 가려지는 상황이다. 이 경우 높이가 같아질 때까지 스택을 비우고, 건물의 개수를 추가한다.

건물의 높이가 최상단과 동일한 경우는 무시한다.

마지막으로 모든 건물의 높이를 확인해도 스택에 남아있는 경우가 있어, 이를 카운팅한다.

