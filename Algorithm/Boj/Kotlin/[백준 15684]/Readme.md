## 문제 풀이
이러한 문제의 유형이 가로와 세로의 개념이 전환되어서 조금 헷갈리는 문제 유형이라고 생각한다.   
문제 조건을 살펴보고 세로선과 높이 즉 가로층이 그렇게 크지 않고, 최대 설치 가능 개수가 3개라서 충분히 백트래킹 + 완탐이 가능하다고 생각이 들었다.    
재귀를 탐색하는 과정에서 층수를 +1 해줬었는데, 같은 층에서 연속된 선이 있을 경우도 생각해줘야했고, 이 판단이 조금 어려웠다.   
사다리를 검사하는 과정에서 해당되는지 아닌지를 판단하는 로직은 왼쪽에 선이 있는지, 오른쪽에 선이 있는지를 검사하고, 세로 칸을 좌우로 움직여줬다.
