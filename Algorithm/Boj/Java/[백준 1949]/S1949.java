import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;

    static long[] graph;

    static ArrayList<Integer>[] tree;

    static boolean[] visit;

    static long[][] dp;

    public static void main(String[] args) throws IOException {
        input();
        solution(1);
        System.out.print(Math.max(dp[1][0],dp[1][1]));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        tree = new ArrayList[n + 1];
        dp = new long[n + 1][2];
        graph = new long[n+1];
        visit = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
            graph[i] = Long.parseLong(st.nextToken());
        }
        int s, e;
        for (int i = 0; i < n-1; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            tree[s].add(e);
            tree[e].add(s); //인접한 경로 추가
        }
    }
    public static void solution(int depth) {
        if (visit[depth]) { //이미 방문한 곳이라면
            return;
        }
        dp[depth][0] = 0;
        dp[depth][1] = graph[depth];
        visit[depth] = true;
        for (int i = 0; i < tree[depth].size(); i++) {
            int next = tree[depth].get(i);
            if (visit[next]) { //이미 방문한 곳이라면
                continue;
            }
            solution(next);
            //dp [i][j] 는 i번째 마을이 우수마일인지, 아닌지를 뜻힘
            // j가 0이면 우수마을 x, 1이면 우수마을 O
            dp[depth][0] = dp[depth][0] + Math.max(dp[next][0], dp[next][1]);
            dp[depth][1] = dp[depth][1] + dp[next][0];
            visit[next] = false;
        }
    }
}