# 문제 풀이
접근 방식에 대해서 빨리 떠올랐는데, 시간초과와의 싸움이 길어졌던 문제였다.   
딱 보고 저번 카카오 인턴 코딩테스트 문제와 비슷한 유형이라고 생각이 들었다.   
문제의 핵심은 a,b,c,d의 배열에서 0이 되는 순서쌍을 찾는것이다.  
대부분 이런 문제 유형은 배열의 크기가 엄청나게 커서 묶어서 계산을 하는것이 정해이다.  
그래서 나도 (a,b) / (c,d)를 묶어서 계산했다. 계산은 HashMap 의 key는 합, value는 개수를 정해서
HashMap을 순회하면서 반대편 HashMap에 그 값이 있는지를 찾았고, 있다면 value를 곱해서 더해줬다.   
하지만 이렇게 풀 경우 시간초과를 받게 될 것이다. 최악의 경우 겹치는 합이 없어 map의 key 개수가 16,000,000이 될 것이고 이를 매번 정렬하고 하는것이 꽤 시간이 오래 걸리는 작업이었다.   
나도 왜 시간초과가 발생했는지 몰라 질문게시판을 보고 HashMap을 사용하면 안된다는 얘기를 듣고, 풀이를 수정했다.   

맵으로 저장하지 않고, n * n의 배열 두개에다가 (a,b)와 (c,d)의 합을 저장했다.  
이후 두 배열의 원소에서 0을 찾기 위해서 투포인터 알고리즘을 진행한다.   
각각 처음과 끝에서 출발해, 0일 경우 똑같은 경우의 수를 제거하기 위해 left와 right를 증가시키면 끝이다.