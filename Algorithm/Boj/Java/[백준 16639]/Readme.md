# 문제 풀이
처음 문제를 보고, 구간 별로 값을 설정해서 꺼내 쓰는 dp 문제라고 생각했다.   
접근은 좋았지만, 조금 더 복잡한 문제였고, 풀이를 봤다.
우선 구간 A와 B에 대해 경우의 수를 생각해줘야했다.
1. 양수, 양수
2. 양수, 음수
3. 음수, 양수
4. 음수, 음수

각 구간은 최솟값과 최댓값을 가지는데, 연산 기호에 따라 최솟값과 최대값이 특정되는 경우가 다 달랐다.

따라서, 모든 구간에 대해 최솟값과 최대값을 구하고, 연산 기호에 따른 값을 추출해서 게속해서 최대값을 업데이트 해줘야하는 문제였다.


