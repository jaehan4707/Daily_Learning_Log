import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static int[][] graph;
    static int[][] dp;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(dp[n][m]);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        dp = new int[n+1][m+1];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution(){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                dp[i][j] += graph[i][j]+Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
    }
}

