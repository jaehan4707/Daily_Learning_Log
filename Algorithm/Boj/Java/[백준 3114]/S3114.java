import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m;

    static String[][] graph;

    static int[][] dp;

    static int[][] aSum;
    static int[][] bSum;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution(1, 1));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new String[n][m];
        aSum = new int[n + 1][m + 1];
        bSum = new int[n + 1][m + 1];
        dp = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                graph[i][j] = st.nextToken();
            }
        }

        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], -1);
            for (int j = 1; j <= m; j++) {
                int num = Integer.parseInt(graph[i-1][j-1].substring(1));
                char alpha = graph[i - 1][j - 1].charAt(0);
                aSum[i][j] = aSum[i - 1][j];
                bSum[i][j] = bSum[i][j - 1];
                if (alpha == 'A') {
                    aSum[i][j] += num;
                } else {
                    bSum[i][j] += num;
                }
            }
        }
    }

    public static int solution(int x, int y) {
        if (dp[x][y] != -1) {
            return dp[x][y];
        }
        int b_cnt = 0;
        int a_cnt = 0;
        dp[x][y] = 0;
        if (x + 1 <= n) { // 아래
            b_cnt = bSum[x][m] - bSum[x][y];
            dp[x][y] = Math.max(dp[x][y], solution(x + 1, y) + b_cnt);
        }
        if (y + 1 <= m) { //오른쪽
            a_cnt = aSum[n][y] - aSum[x][y];
            dp[x][y] = Math.max(dp[x][y], solution(x, y + 1) + a_cnt);
        }
        if (x + 1 <= n && y + 1 <= m) {
            a_cnt = aSum[n][y] - aSum[x][y];
            b_cnt = bSum[x][m] - bSum[x][y];
            dp[x][y] = Math.max(dp[x][y], solution(x + 1, y + 1) + a_cnt + b_cnt);
        }
        return dp[x][y];
    }
}
