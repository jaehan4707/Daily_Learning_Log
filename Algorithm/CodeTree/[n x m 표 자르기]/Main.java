import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static int[][] graph;
    static int[][][] dp;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        dp = new int[n+1][m+1][2];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static int solution(){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                dp[i][j][0] = dp[i-1][j][0]*10+graph[i][j]; //위에서 땡기는 경우
                dp[i][j][1] = dp[i][j-1][1]*10+graph[i][j]; //왼쪽에서 떙기는 경우
            }
        }
        int answer1 = 0;
        int answer2 = 0;
        for(int j=1; j<=m; j++){
            answer1+=dp[n][j][0];
        }
        for(int j=1; j<=n; j++){
            answer2+=dp[j][m][1];
        }
        return Math.max(answer1,answer2);
    }
}
