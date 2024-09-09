import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;


    static ArrayList<Integer>[] tree;

    static boolean[] visit;

    static long[][] dp;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        input();
        solution(1); // 루트 노드가 1로 가정하고 계산
        System.out.print(Math.min(dp[1][0], dp[1][1]));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        tree = new ArrayList[n + 1];
        dp = new long[n + 1][2];
        visit = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }
        int s, e;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            tree[s].add(e);
            tree[e].add(s); //인접한 경로 추가
        }
    }

    public static void solution(int depth) {
        if (visit[depth]) {
            return;
        }
        dp[depth][1] = 1;
        visit[depth] = true;
        for (int i = 0; i < tree[depth].size(); i++) {
            int next = tree[depth].get(i);
            if (visit[next]) {
                continue;
            }
            solution(next);
            dp[depth][1] = dp[depth][1] + Math.min(dp[next][0], dp[next][1]);// 자신이 얼리어답터일 때
            dp[depth][0] = dp[depth][0] + dp[next][1]; // 자신이 얼리어답터가 아닐 때
        }
    }
}
