# 문제 풀이

합집합과 교집합을 계산하는 문제이다. 이런 문제는 보통 Union&Find를 사용해서 푸는것이 정해라고 생각한다.   
합집합일 경우 Union을 통해 부모를 합쳐준다. 여기서 나는 작은 부모가 항상 상단이 가도록 하는법이다.   
예를 들어 4와 3을 합칠 때, 4의 부모가 1이고, 3의 부모가 2일 경우 3의 부모를 4의 부모(더 작은 쪽으로) 변경한다.   
교집합의 경우 Find를 통해서 부모를 찾고 일치하면 Yes, 아니라면 NO를 출력하면 된다.