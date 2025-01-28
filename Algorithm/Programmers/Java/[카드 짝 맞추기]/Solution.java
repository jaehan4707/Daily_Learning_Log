import java.util.*;

class Solution {

    Point start;
    int [][] board;
    int n,m;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static int [] card;
    static int answer = Integer.MAX_VALUE;
    static int mx, my;
    public int solution(int[][] b, int r, int c) {
        init(b,r,c);
        dfs(0,"");
        return answer;
    }

    public void init(int [][] b, int r, int c){
        n = 4;
        m = 4;
        start = new Point(r,c);
        board = new int[n][m];
        int cnt = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                board[i][j] = b[i][j];
                if(board[i][j]!=0){
                    cnt+=1;
                }
            }
        }
        cnt/=2;
        card = new int[cnt];
        for(int i=0; i<cnt; i++){//다 뽑아야 하니 비트마스킹은 의미 없잖슴!
            card[i] = i+1;
        }
    }

    public void dfs(int depth, String order){
        if(depth == card.length){ // 다 뽑은 겨웅
            // bfs
            answer = Math.min(answer,searchCard(order));
            return;
        }

        for(int i=0; i<card.length; i++){
            if(order.contains(""+card[i])){
                continue;
            }
            dfs(depth+1, order+card[i]);
        }
    }


    public int searchCard(String order){
        int cnt = 0;
        int [][] temp = new int[4][4];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                temp[i][j] = board[i][j];
            }
        }
        mx = start.x;
        my = start.y;
        for(int i=0; i<order.length(); i++){
            int num = order.charAt(i)-'0';
            cnt += bfs(num, temp);
            cnt+=1; // Enter
            cnt+=bfs(num,temp);
            cnt+=1; // Enter
        }
        // 현재 위치에서 o를 맞추도록 이동시키기

        return cnt;
    }

    public int bfs(int target, int [][] temp){
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(mx,my));
        int [][] visit = new int[4][4];
        for(int i=0; i<4; i++){
            Arrays.fill(visit[i],-1);
        }
        visit[mx][my] = 0;
        while(!q.isEmpty()){
            Point now = q.poll();
            if(temp[now.x][now.y]==target){
                mx = now.x;
                my = now.y;
                temp[mx][my] = 0;
                return visit[now.x][now.y];
            }

            for(int d=0; d<4; d++){
                int nx = now.x+dx[d];
                int ny = now.y+dy[d];
                if(!validate(nx,ny) || visit[nx][ny]!=-1){
                    continue;
                }

                visit[nx][ny] = visit[now.x][now.y]+1;
                q.add(new Point(nx,ny));
            }

            for(int d=0; d<4; d++){
                // 해당 방향에 그래프가 있는지 확인하기
                Point find = findPoint(now.x,now.y, d,temp);
                if(visit[find.x][find.y] !=-1){
                    continue;
                }
                visit[find.x][find.y] = visit[now.x][now.y] + 1;
                q.add(new Point(find.x,find.y));
            }
        }
        return -1;
    }

    public Point findPoint(int x, int y, int d, int [][] temp){
        x+=dx[d];
        y+=dy[d];
        while(validate(x,y)){
            if(temp[x][y]!=0){
                return new Point(x,y);
            }
            x+=dx[d];
            y+=dy[d];
        }
        // 끝가지 갔을 때 넘은 경우임 이 경우 한번 빼서 적절한 위치로 조정
        return new Point(x-dx[d], y-dy[d]);
    }

    public boolean validate(int x, int y){
        if(x>=4 || x<0 || y>=4 || y<0){
            return false;
        }
        return true;
    }

    static class Point {
        int x,y;
        Point(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
}

/*
2장 선택 -> 같은 그림 -> 카드 제거
       -> 다른 그림 -> 원래 상태로 뒷변이 보이도록 뒤집음(원상 복구)
게임의 목적은 모든 카드를 제거하는 것

커서 -> ctrl 키와 방향키(좌,상,하,우)
방향키만 누르면 한 칸 이동
ctrl 키와 같이 누르면 방향에 있는 가장 가까운 카드 or 방향의 마지막 칸
Enter
앞면이 보이는 카드가 1장 뿐이라면 그림을 맞출 수 없음 -> 두번 째 카드를 뒤집을 때 까지 앞면 유지
2장이라면, 두 개의 카드가 그려진 그림이 같으면 사라지고, 아니라면 다시 뒷면이 보이도록 뒤집음
제거하는데 필요한 키 조작 횟수의 최소값 (Enter, 방향키, Ctrl 각각 1로 계산)
*/