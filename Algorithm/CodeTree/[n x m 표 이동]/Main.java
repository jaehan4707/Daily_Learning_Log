import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static int[][] graph;
    static int[][] dp;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution(1,1));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        dp = new int[n+1][m+1];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }
    }

    public static int solution(int x,int y){
        if(x==n && y == m){ //경로 도착
            return 1;
        }
        if(dp[x][y]>=0){
            return dp[x][y];
        }
        dp[x][y] = 0;
        for(int i=0; i<4; i++){
            int mx = x+dx[i];
            int my = y+dy[i];
            if(!isInMap(mx,my)){
                continue;
            }
            if(graph[x][y]> graph[mx][my]){
                dp[x][y] = (dp[x][y] + solution(mx,my))%1000000007;
            }
        }
        return dp[x][y];
    }

    public static boolean isInMap(int x, int y){
        if(x<=0 || x>n || y<=0 || y>m){
            return false;
        }
        return true;
    }
}