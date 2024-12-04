import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,q;
    static int[][] graph;
    static long answer;
    static Point[][] points;
    static boolean[][] visited;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};

    public static void main(String[] args) throws IOException{
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution(){
        int turn = 0;
        while(true){
            turn++;
            int [][] temp = new int[n+1][m+1];
            for(int i=1; i<=n; i++){
                for(int j=1; j<=m; j++){
                    if(graph[i][j]==0){
                        continue;
                    }
                    int cnt = 0;
                    for(int d=0; d<4; d++){
                        int mx = i+dx[d];
                        int my = j+dy[d];
                        if(!isInMap(mx,my)){
                            continue;
                        }
                        if(graph[mx][my]==0){
                            cnt+=1;
                        }
                    }
                    temp[i][j] = cnt;
                }
            }
            for(int i=1; i<=n; i++){
                for(int j=1; j<=m; j++){
                    graph[i][j] -= temp[i][j];
                    if(graph[i][j]<0){
                        graph[i][j]=0;
                    }
                }
            }
            visited = new boolean[n+1][m+1];
            int count = 0;
            for(int i=1; i<=n; i++){
                for(int j=1; j<=m; j++){
                    if(graph[i][j]==0 || visited[i][j]){
                        continue;
                    }
                    bfs(i,j);
                    count+=1;
                }
            }
            if(count>=2){
                System.out.println(turn);
                break;
            }  else if(count==0){
                System.out.println(0);
                break;
            }
        }
    }

    static void bfs(int x, int y){
        visited[x][y] = true;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x,y));
        while(!q.isEmpty()){
            Point now = q.poll();
            for(int d=0; d<4; d++){
                int mx = now.x+dx[d];
                int my = now.y+dy[d];
                if(!isInMap(mx,my) || graph[mx][my]==0 || visited[mx][my]){
                    continue;
                }
                q.add(new Point(mx,my));
                visited[mx][my]=true;
            }
        }
    }

    static boolean isInMap(int x, int y){
        if(x<=0 || x>n || y<=0 || y>m){
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