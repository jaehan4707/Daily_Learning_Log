## 문제 풀이

시간초과 때문에 정말 고생했던 문제였다.

문제에서 요구한 대로 벽을 부수고, 벽을 부순 시점에서 이동할 수 있는 경로의 수를 구하는 문제이다.

정말 당연하게도 벽인 곳을 큐에 넣어서 BFS를 진행했다.

```
while(!q.isEmpty()) { 
        Queue<Point> child = new LinkedList<Point>();
        Point cur = q.poll();
        child.add(cur);
        boolean [][] visit = new boolean[n][m];
        int cnt = 0;
        while(!child.isEmpty()) {
            Point now = child.poll();
            if(visit[now.x][now.y])
                continue;
            visit[now.x][now.y]=true;
            cnt++;			
            for(int d=0; d<4; d++) {
                int mx = now.x + dir[d][0];
                int my = now.y + dir[d][1];
                if(!isInGraph(mx, my) || graph[mx][my]==1 || visit[mx][my]) {
                    continue;
                }
                child.add(new Point(mx,my));					
            }
        }
        answer[cur.x][cur.y]=cnt%10;
}
```

-   q에는 벽인 장소만 저장된 상태이다.
-   벽을 뽑아서 또 다른 큐에 넣고, 해당 큐를 BFS로 돌려서 갈 수 있는 경로의 수를 cnt에 저장한다.
-   그러고 child 큐가 비게 된다면 최종적인 cnt 값을 그래프에 넣어준다.

해당 알고리즘으로 코드를 작성했지만 **시간초과**가 발생했다.

나름대로 이유를 생각해봤다.

1.  child 큐를 탐색하는 과정에서 탐색했던 경로에 대해서 중복으로 탐색을 하는 것.
2.  parent와 child  두 번의 탐색이 적절하지 않을 수 있다.

따라서 중복적으로 계산되는 영역인 연속적으로 갈 수 있는 0의 개수를 먼저 계산했다.

많은 방법이 있겠지만, 그룹핑을 통해서 연결된 영역의 0의 개수를 영역별로 저장했다.

그룹핑을 하는 방법은 0에서 BFS를 해서 연결할 수 있는 영역을 묶는 것이다.

```
public static int makeGroup(int r, int c, int group) {
    int cnt = 0;
    Queue<Point> temp = new LinkedList<Point>();
    temp.add(new Point(r, c));
    while (!temp.isEmpty()) {
        Point cur = temp.poll();
        if (graph[cur.x][cur.y] != 0)
            continue;
        graph[cur.x][cur.y] = group;
        cnt++;
        for (int d = 0; d < 4; d++) {
            int mx = cur.x + dir[d][0];
            int my = cur.y + dir[d][1];
            if (!isInGraph(mx, my) || graph[mx][my] != 0) { //
                continue;
            }
            temp.add(new Point(mx, my));
        }
    }
    return cnt;
}
```

좌표 r, c에서 출발해서 갈 수 있는 모든 지역을 group으로 묶는다.

```
static HashMap<Integer, Integer> mark = new HashMap<Integer, Integer>();
```

이제 그룹의 번호와 그룹의 0의 개수를 저장해야 했고, 나는 map 자료구조를 사용했다.

key는 그룹번호이고, value는 영역의 0의 개수이다.

이렇게 라벨링 한 그룹에 0의 개수를 저장하고, 벽에서 갈 수 있는 0의 개수를 BFS를 통해서 진행한다.

```
public static int bfs(int r, int c) {
    int cnt = 1;
    HashSet<Integer> s = new HashSet<>();
    for (int d = 0; d < 4; d++) {
        int mx = r + dir[d][0];
        int my = c + dir[d][1];
        if (!isInGraph(mx, my) || graph[mx][my] == 1 || s.contains(graph[mx][my])) {
            continue;
        }
        cnt += mark.get(graph[mx][my]);
        s.add(graph[mx][my]);
    }
    return cnt % 10;
}
```

4방향에서 라벨을 검사한다.

라벨을 중복적으로 포함시키면 안 되기 때문에 Set 자료구조를 사용했다.