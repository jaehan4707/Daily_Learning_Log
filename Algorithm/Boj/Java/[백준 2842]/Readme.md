# 문제 풀이

정말 오랫동안 풀었던 문제였다. 가장 적은 피로도를 사용해서 방문하는것을 찾아야 해서, 처음에는 0~1,000,000 까지의 피로도를 사용해서 검사를 하고,
가능하다면 바로 답을 출력하도록 했다. 하지만 시간초과가 발생했고, 이분탐색으로 바꿨다.
left를 0으로, right를 1,000,000로 설정해서 이분탐색으로 피로도를 결정했고, bfs를 통해서 검사를 했다.  
하지만 이래도 시간초과가 발생했고, visit를 처리하는 로직이 잘못된것을 발견했다.   
bfs를 해서, 이동한 지점에 대해 우체국 방문 개수를 검사했는데, 사실 bfs이기 때문에 전역적으로 방문 개수를 검사해도 된다는 사실을 까먹었다.   
사실 DFS로 로직을 구현하는것이 깔끔했을 것 같다.   
그래도 방문처리에서 꼬여서, 풀이를 보니 투포인터로 검사를 해서 최저로 방문할 수 있는 피로도와 최고 피로도를 설정해서 해당 바운더리만 방문하는것을 검사하니까 통과했다.

실제로 많이 어려웠던 문제였고, 투포인터와 BFS를 섞은 유형은 처음풀어봐서 새로웠다.