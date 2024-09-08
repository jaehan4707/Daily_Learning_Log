import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n, r, q;


    static ArrayList<Integer>[] tree;

    static boolean[] visit;

    static long[] dp;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        input();
        solution(1);
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        tree = new ArrayList[n + 1];
        dp = new long[n + 1];
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
        solution(r); //한번 쫙 계산
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());
            sb.append(dp[query]).append("\n");
        }
    }

    public static void solution(int depth) {
        if (visit[depth]) { //이미 방문한 곳이라면
            return;
        }
        dp[depth] = 1;
        visit[depth] = true;
        for (int i = 0; i < tree[depth].size(); i++) {
            int next = tree[depth].get(i);
            if (visit[next]) { //이미 방문한 곳이라면
                continue;
            }
            solution(next);
            dp[depth] += dp[next];
        }
    }
}
