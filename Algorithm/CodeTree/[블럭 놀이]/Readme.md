# 문제 풀이

해당 문제도 문제가 길고, 조건이 많지만 차근 차근 하나씩 해결 하면 된다.   

4가지 동작이 있다.

1. 블럭 넣기

해당 동작은 주어진 좌표에 블럭을 넣고, 블럭이 있을 경우 더 큰 값이 위치한다.  

2. 블럭 지우기

해당 동작은 주어진 좌표의 블록을 지운다


3. 위, 아래로 옮기기

방향만 다를뿐 매커니즘은 동일하기 때문에 하나의 동작으로 묶어서 설명한다.   
중요한 점은 한 칸 옮기는것이 아닌, 0을 만날때까지 해당 방향으로 땡겨준다.   
그 후, 블럭에 대해 인접 방향을 검사하고, 같을 경우 체크한다.   
한꺼번에 뺴야하기 때문에 지워져야할 블럭을 미리 체크하고, 한꺼번에 지운다.   
이후, 블럭을 지울 경우 3번을 반복하고 아니라면 중단한다.   



