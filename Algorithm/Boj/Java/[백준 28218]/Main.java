import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m, k, t;
    static char[][] graph;

    static StringBuilder sb;

    static String[] answer = {"Second", "First"};

    static int[][] dp;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new char[n + 1][m + 1];
        dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            String line = br.readLine();
            for (int j = 1; j <= m; j++) {
                graph[i][j] = line.charAt(j - 1);
                dp[i][j] = -1;
            }
        }
        t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            sb.append(answer[solution(x, y)]).append("\n");
        }
    }

    public static int solution(int x, int y) {
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        if (validate(x + 1, y) && solution(x + 1, y) == 0) {
            return dp[x][y] = 1;
        }

        if (validate(x, y + 1) && solution(x, y + 1) == 0) {
            return dp[x][y] = 1;
        }

        for (int i = 1; i <= k; i++) {
            if (validate(x + i, y + i) && solution(x + i, y + i) == 0) {
                return dp[x][y] = 1;
            }
        }
        return 0;
    }

    public static boolean validate(int x, int y) {
        if (x <= 0 || x > n || y <= 0 || y > m) {
            return false;
        }
        if (graph[x][y] == '#') {
            return false;
        }
        return true;
    }
}
