import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, q;

    static long[] graph;

    static SegTree sTree;


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graph = new long[n + 1];
        q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            graph[i] = Long.parseLong(st.nextToken());
        }
        sTree = new SegTree();
        int x, y, a, s, e;
        long b;
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Long.parseLong(st.nextToken());
            s = Math.min(x, y);
            e = Math.max(x, y);
            sb.append(sTree.query(1, n, s, e, 1)).append("\n");
            sTree.update(1, n, 1, a, b - graph[a]);
            graph[a] = b;

        }
    }

    public static class SegTree {
        long[] tree;

        SegTree() {
            int h = (int)Math.ceil(Math.log(n)/Math.log(2));
            int height = (int) Math.pow(2, h+1);
            tree = new long[height];
            init(1, n, 1);
        }

        void init(int start, int end, int nodeIdx) {
            if (start == end) {
                tree[nodeIdx] = graph[start];
                return;
            }
            int mid = (start + end) / 2;
            init(start, mid, nodeIdx * 2);
            init(mid + 1, end, nodeIdx * 2 + 1);
            tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
        }

        long query(int start, int end, int left, int right, int nodeIdx) { // left~ right의 구간합을 구하기
            if (right < start || end < left) {
                return 0;
            }
            if (start >= left && end <= right) { //구간 내에 있을 때
                return tree[nodeIdx];
            }
            int mid = (start + end) / 2;
            Long leftChild = query(start, mid, left, right, nodeIdx * 2);
            Long rightChild = query(mid + 1, end, left, right, nodeIdx * 2 + 1);
            return leftChild + rightChild;
        }

        void update(int start, int end, int nodeIdx, int targetIdx, long changed) {
            //
            if (targetIdx < start || targetIdx > end) {
                return;
            }
            tree[nodeIdx] += changed;
            if (start != end) { // 리프노드가 아닌것은 구간합임
                int mid = (start + end) / 2;
                update(start, mid, nodeIdx * 2, targetIdx, changed);
                update(mid + 1, end, nodeIdx * 2 + 1, targetIdx, changed);
            }
        }
    }
}
