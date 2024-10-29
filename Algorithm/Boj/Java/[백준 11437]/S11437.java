import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m;

    static int[] parent;

    static int[] depth;

    static ArrayList<ArrayList<Integer>> graph;


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        parent = new int[n + 1];
        depth = new int[n + 1];
        int a, b;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        // 각 깊이를 계산하오.
        for (int i = 1; i <= n; i++) {
            calDepth(1, i);
        }
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            solution(a, b);
            //a b의 공통 조상을 찾자.
        }
    }

    public static void calDepth(int d, int idx) {
        if (depth[idx] != 0) {
            return;
        }
        depth[idx] = d;
        for (int child : graph.get(idx)) {
            if (depth[child] != 0) {
                continue;
            }
            parent[child] = idx;
            calDepth(d + 1, child);
        }
    }

    public static void solution(int a, int b) {
        while (depth[a] != depth[b]) {
            if (depth[a] > depth[b]) { // b의 높이를 올려준다.
                a = parent[a];
            } else if (depth[b] > depth[a]) {
                b = parent[b];
            }
        }
        // 높이 맞췄으니 이제 부모가 같아질 때 까지 위로 올려요
        while (a != b) {
            a = parent[a];
            b = parent[b];
        }
        sb.append(a).append("\n");
    }
}
