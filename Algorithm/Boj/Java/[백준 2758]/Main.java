import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int t, n, m;
    static StringBuilder sb;

    static long[][] dp;

    public static void main(String[] args) throws IOException {
        input();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        t = Integer.parseInt(br.readLine());
        dp = new long[11][2001];
        solution();
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            long answer = 0;
            for (int j = 1; j <= m; j++) {
                answer += dp[n][j];
            }
            System.out.println(answer);
        }
    }

    static void solution() {
        for (int i = 1; i <= 2000; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= 10; i++) {
            for (int j = 1; j <= 2000; j++) {
                for (int k = 0; k <= j / 2; k++) {
                    if (dp[i - 1][k] != 0) {
                        dp[i][j] += dp[i - 1][k];
                    }
                }

            }
        }
    }
}