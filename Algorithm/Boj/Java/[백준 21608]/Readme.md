# 문제 풀이
처음에는 문제의 조건을 차근차근 해결해가면서, 코드를 길게 작성했다.   
제출하고, 정답인걸 확인하고 개선할 수 있는 부분을 개선하기로 했다.   
우선 크게는 조건1에 해당하는 후보군들을 뽑아서, 후보군들의 크기가 1초과라면 후보군들 중에서 빈칸의 개수가 많은 후보군으로 다시 추리고, 그 후보군들에 대해 빈칸의 개수를 바탕으로 우선순위큐에 넣어줬다.

우선순위큐에 정렬 조건은 행과 열이 작은 순서대로이다.

코드는 정답이었지만, 중복된 데이터 처리도 많았고, 로직도 깔끔하지 않아서 개선을 하고자 했다.   
개선하는 과정에서 많이 버벅였지만, 후보군들이 아닌, 하나의 점을 계속해서 업데이트하는 방향으로 코드를 바꿀 수 있었다.
좋아하는 학생과 빈칸의 개수가 0일때, 점이 업데이트되지 않았던 문제가 있어서 각각 -1로 초기화했다.