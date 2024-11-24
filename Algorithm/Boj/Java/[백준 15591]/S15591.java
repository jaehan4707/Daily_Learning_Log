import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, q;


    static ArrayList<ArrayList<Point>> graph;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            graph.get(p).add(new Point(q, r));
            graph.get(q).add(new Point(p, r));
        }
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            sb.append(solution(k, v)).append("\n");
        }
    }

    public static long solution(int k, int v) {
        long answer = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(v);
        boolean[] visit = new boolean[n + 1];
        visit[v] = true;
        while (!q.isEmpty()) {
            int now = q.poll();
            for (Point point : graph.get(now)) {
                if (visit[point.v] || point.r < k) {
                    continue;
                }
                answer += 1;
                visit[point.v] = true;
                q.add(point.v);
            }
        }
        return answer;
    }

    static class Point {
        int v, r;

        Point(int v, int r) {
            this.v = v;
            this.r = r;
        }
    }
}
