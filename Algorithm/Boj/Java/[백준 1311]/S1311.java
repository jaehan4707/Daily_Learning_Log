import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;


    static long[][] dp;

    static long[][] graph;

    static long answer = Long.MAX_VALUE;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        input();
        System.out.println(solution(1, 1));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        graph = new long[n + 1][n + 1];
        dp = new long[n + 1][1 << (n + 1)];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE);
        }
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                graph[i][j] = Long.parseLong(st.nextToken());
            }
        }
    }

    public static long solution(int depth, int checked) { //checked는 현재 한 일, value는 값, depth는 깊이
        if (checked == (1 << (n + 1)) - 1) { // 끝났다면
            return 0;
        }
        if (dp[depth][checked] != Long.MAX_VALUE) {
            return dp[depth][checked];
        }
        for (int i = 1; i <= n; i++) {
            int next = 1 << i;
            if ((checked & next) >= 1) { //이미 했다면
                continue;
            }
            dp[depth][checked] = Math.min(dp[depth][checked], solution(depth + 1, (checked | next)) + graph[depth][i]);
        }
        return dp[depth][checked];
    }
}