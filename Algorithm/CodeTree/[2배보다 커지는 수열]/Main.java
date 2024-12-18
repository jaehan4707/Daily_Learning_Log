import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,answer=0;
    static int INF = 1000000007;
    static int [][] dp;

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
        dp = new int[n+1][m+1];
    }

    public static long solution(){
        for(int i=1; i<=m; i++){
            dp[1][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 1; k <= j / 2; k++) {
                    dp[i][j] += dp[i - 1][k];
                    dp[i][j] %= INF;
                }
            }
        }
        long answer = 0;
        for(int j=1; j<=m; j++){
            answer += dp[n][j];
            answer%=INF;
        }
        return answer;
    }
}

