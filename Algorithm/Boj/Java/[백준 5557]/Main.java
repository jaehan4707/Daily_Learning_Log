import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, q;
    static StringBuilder sb;

    static int[] numbers;
    static long[][] dp;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(dp[n-1][numbers[n]]);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        numbers = new int[n + 1];
        dp = new long[n + 1][21];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }
    }

    static void solution() {
        // i번째 까지 수를 사용해서 j를 만드는 경우의 수
        dp[1][numbers[1]] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= 20; j++) {
                if (dp[i - 1][j] != 0) {
                    if (j - numbers[i] >= 0) {
                        dp[i][j - numbers[i]] += dp[i - 1][j];
                    }
                    if (j + numbers[i] <= 20) {
                        dp[i][j + numbers[i]] += dp[i - 1][j];
                    }
                }
            }
        }
    }
}