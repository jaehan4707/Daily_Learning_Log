## 문제 풀이
다른 수에 대해 잘못 이해해서 헤맸던 문제였다.   
주어진 수를 다른 두 수의 합으로 표현할 수 있는지를 찾기 위해선 이분탐색 or 투 포인터를 사용해야 한다고 생각했고, 
이를 위해서 배열을 우선 오름차순 정렬했다.    
후에는 탐색을 시작하는데, left와 right에 대해 합과 숫자를 비교한다.   
만약 합보다 숫자가 크다면 left 범위를 증가시키고, 아니라면 right 범위를 감소시킨다.   
만약 합과 숫자가 일치하다면 여기서 합을 만드는데 사용한 숫자가 다른 숫자인지 판별해야하는데, 문제를 잘못 이해해서 해당 부분을 빼고 제출하니 70%에서 틀렸었다.   
다른 숫자인지 검사하는 로직을 추가한 뒤 통과할 수 있었다.