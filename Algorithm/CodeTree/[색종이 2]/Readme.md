# 문제 풀이

사각형의 둘레의 길이를 측정하기 위해서 어떻게 해야 할까 고민을 했었다.   
우선, 복잡하지 않게 생각해도 될 이유는 우선 사각형의 변의 길이가 고정인 점이었다.   
사각형의 둘레를 측정하기 위해 그 좌표가 둘레에 포함되는지 확인해야 한다.   


하나의 점에 대해 4방향으로 탐색을 해서 만약 사각형이 없는 좌표이거나, 좌표의 범위를 벗어날 경우 흰색 영역일 것이고, 이를이용해 둘레의 길이를 구할 수 있다.