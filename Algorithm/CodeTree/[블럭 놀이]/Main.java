import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,q;
    static int [] dx  = {1,-1,0,0};
    static int [] dy  = {0,0,1,-1};
    static int [][] map;
    static boolean [][] visit;
    static boolean [][] del;

    public static void main(String[] args) throws IOException{
        input();
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        map = new int[n+1][m+1];
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int opt = Integer.parseInt(st.nextToken());
            if(opt==1){
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                map[x][y] = Math.max(map[x][y],v);
            } else if(opt==4) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                map[x][y] = 0;
            } else {
                move(opt);
            }

        }
    }

    public static void upMove(){
        for(int j=1; j<=m; j++){
            for(int i=2; i<=n; i++){
                // 위 블럭이 0이 아니면 패스
                if(map[i][j]==0){
                    continue;
                }
                int row = -1;
                for(int k=i-1; k>=1; k--){ // 갈 수 있을만큼 땡김
                    if(map[k][j]!=0){ //비어있으면 옮김
                        //map[k+1][j] = map[i][j];
                        break;
                    } else {
                        row = k;
                    }
                }
                if(row==-1){
                    continue;
                }
                map[row][j] = map[i][j];
                map[i][j] = 0;
            }
        }
    }

    public static void downMove(){
        for(int j=1; j<=m; j++){
            for(int i=n-1; i>=1; i--){
                // 위 블럭이 0이 아니면 패스
                if(map[i][j]==0){
                    continue;
                }
                int row = -1;
                for(int k=i+1; k<=n; k++){ // 갈 수 있을만큼 땡김
                    if(map[k][j]!=0){ //비어있으면 옮김
                        break;
                    } else {
                        row = k;
                    }
                }
                if(row==-1){
                    continue;
                }
                map[row][j] = map[i][j];
                map[i][j] = 0;
            }
        }
    }

    // 2번 위로 기울이기
    public static void move(int opt){ //위로 기울이기 블럭을 만나면 멈춤
        while(true){
            visit = new boolean[n+1][m+1];
            del = new boolean[n+1][m+1];
            boolean flag = false;
            if(opt==2){
                upMove();
            } else {
                downMove();
            }
            for(int i=1; i<=n; i++){
                for(int j=1; j<=m; j++){
                    if(map[i][j]==0 || visit[i][j]){
                        continue;
                    }
                    visit[i][j] = true;
                    search(i,j);
                }
            }

            for(int i=1; i<=n; i++){
                for(int j=1; j<=m; j++){
                    if(del[i][j]){
                        flag = true;
                        map[i][j] = 0;
                    }
                }
            }
            if(!flag){
                break;
            }
            // 인접한 숫자 찾기
        }
    }

    public static void search(int x, int y){
        // 한꺼번에 지운다.
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x,y));

        while(!q.isEmpty()){
            Point now = q.poll();
            for(int d=0; d<4; d++){
                int mx = now.x+dx[d];
                int my = now.y+dy[d];
                if(!isInMap(mx,my) || visit[mx][my] || map[mx][my]==0){
                    continue;
                }
                if(map[now.x][now.y] == map[mx][my]){
                    del[mx][my] = true;
                    del[now.x][now.y] = true;
                    q.add(new Point(mx,my));
                    visit[mx][my] = true;
                }
            }
        }

    }

    public static boolean isInMap(int x, int y){
        if(x<=0 || x>n || y<=0 || y>m){
            return false;
        }
        return true;
    }

    static class Point{
        int x,y;

        Point(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
}

