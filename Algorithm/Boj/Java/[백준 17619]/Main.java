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
    static Bridge[] bridges;

    static int[] parent;

    static int[] idx;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        bridges = new Bridge[n];
        idx = new int[n + 1];
        parent = new int[n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            bridges[i] = new Bridge(x1, x2, y, i + 1);
            parent[i + 1] = i + 1;
        }
        Arrays.sort(bridges, (b1, b2) -> {
            if (b1.x1 == b2.x1) {
                return b1.y - b2.y;
            }
            return b1.x1 - b2.x1;
        });

        for (int i = 0; i < n; i++) {
            idx[bridges[i].idx] = i; // 1번 다리는 idx i번에 있음.
        }

        solution();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            if (find(n1) == find(n2)) {
                sb.append(1).append("\n");
            } else {
                sb.append(0).append("\n");
            }
        }
    }

    static void solution() {
        int x2 = bridges[0].x2;
        int x1 = bridges[0].x1;
        for (int i = 1; i < n; i++) {
            if (x2 >= bridges[i].x1) { // 선분에 포함됨.
                x1 = Math.min(bridges[i].x1, x1);
                x2 = Math.max(bridges[i].x2, x2);
                parent[bridges[i].idx] = find(bridges[i - 1].idx);
            } else {
                x2 = bridges[i].x2;
                x1 = bridges[i].x1;
            }
        }
    }

    static int find(int x1) {
        if (x1 == parent[x1]) {
            return x1;
        }
        return parent[x1] = find(parent[x1]);
    }

    static class Bridge {
        int x1, x2, y, idx;

        Bridge(int x1, int x2, int y, int idx) {
            this.x1 = x1;
            this.x2 = x2;
            this.y = y;
            this.idx = idx;
        }
    }
}
