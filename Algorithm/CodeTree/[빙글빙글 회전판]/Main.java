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
    static int[] dx = {1,-1,0,0,0};
    static int[] dy = {0,0,1,-1,0};

    public static void main(String[] args) throws IOException{
        input();
        for(int i=0; i<q; i++){
            solution(points[i][0], points[i][1]);
        }
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                System.out.print(graph[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        points = new Point[q][2];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            points[i][0] = new Point(r1,c1);
            points[i][1] = new Point(r2,c2);
        }

        // 영역 반시계방향으로 회전 -> 영역 내에 있는 숫자들의 값을 상하좌우로 평균값으로 변경
    }

    public static void solution(Point p1, Point p2){
        int tmp = graph[p1.x][p1.y];
        for(int j = p1.y; j < p2.y; j++) graph[p1.x][j] = graph[p1.x][j + 1];
        for(int i = p1.x; i < p2.x; i++) graph[i][p2.y] = graph[i + 1][p2.y];
        for(int j = p2.y; j > p1.y; j--) graph[p2.x][j] = graph[p2.x][j - 1];
        for(int i = p2.x; i > p1.x; i--) graph[i][p1.y] = graph[i - 1][p1.y];
        graph[p1.x + 1][p1.y] = tmp;
        int [][] temp = new int[n+1][m+1];
        for(int i=p1.x; i<=p2.x; i++){
            for(int j=p1.y; j<=p2.y; j++){
                int sum = graph[i][j];
                int count = 1;
                for(int d=0; d<4; d++){
                    int mx = i+dx[d];
                    int my= j+dy[d];
                    if(!isInMap(mx,my)){
                        continue;
                    }
                    sum+=graph[mx][my];
                    count+=1;
                }
                temp[i][j] = (sum + count - 1) / count;
            }
        }
        for(int i=p1.x; i<=p2.x; i++){
            for(int j=p1.y; j<=p2.y; j++){
                graph[i][j] = temp[i][j];
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