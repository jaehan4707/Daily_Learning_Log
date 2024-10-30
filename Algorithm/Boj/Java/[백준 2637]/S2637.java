import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n, m;
    static long[][] dp;
    static long[][] product;
    static int[] outDegree;
    static ArrayList<Integer> temp;
    static ArrayList<ArrayList<Integer>> graph;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        dp = new long[n + 1][n + 1]; //n * 5
        product = new long[n + 1][n + 1];
        outDegree = new int[n + 1];
        graph = new ArrayList<>();
        temp = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        int x, y, k;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            product[x][y] = k; //x를 y로 만드는데 k개가 필요함.
            graph.get(y).add(x); // x를 만드는데 필요한 부품은 y라는 뜻
            outDegree[x]++; //필요한 부붐의 개수 ++
        }
    }

    static void solution() {
        Stack<Integer> s = new Stack<>();
        for (int i = 1; i <= n; i++) {
            if (outDegree[i] == 0) { //필요한 부붐의 개수가 없을 경우 기본부품임
                s.push(i);
                temp.add(i);
                dp[i][i] = 1;
            }
        }
        while (!s.isEmpty()) {
            int basic = s.pop();
            for (int i = 0; i < graph.get(basic).size(); i++) { //필요한 부품들
                int median = graph.get(basic).get(i);
                outDegree[median] -= 1;
                if (outDegree[median] == 0) {
                    s.push(median);
                }
                for (int j = 1; j <= n; j++) {
                    dp[median][j] += dp[basic][j] * product[median][basic];
                }
            }
        }
        for (int now : temp) {
            sb.append(now).append(" ").append(dp[n][now]).append("\n");
        }
    }
}
