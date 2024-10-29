import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m, k;

    static long[] numbers;

    static Tree sTree;

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
        numbers = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            numbers[i] = Long.parseLong(st.nextToken());
        }
        sTree = new Tree(n);
        int a, b;
        long c;
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Long.parseLong(st.nextToken());
            if (a == 1) {
                //b번째 수를 c로 바꿈
                sTree.update(1, n, b, c - numbers[b], 1);
                numbers[b] = c;
            } else {
                sb.append(sTree.query(1, n, b, c, 1)).append("\n");
            }
        }
    }

    static class Tree {
        long[] tree;

        Tree(int h) {
            tree = new long[h * 4]; //4h로 잡음
            init(1, n, 1);
        }

        void init(int start, int end, int nodeIdx) {
            if (start == end) {
                tree[nodeIdx] = numbers[start];
                return;
            }
            int mid = (start + end) / 2;
            init(start, mid, nodeIdx * 2);
            init(mid + 1, end, nodeIdx * 2 + 1);
            tree[nodeIdx] = tree[nodeIdx * 2] + tree[nodeIdx * 2 + 1];
        }

        long query(int start, int end, int left, long right, int nodeIdx) {
            if (right < start || left > end) { // 구간을 벗어나면
                return 0L;
            }

            if (left <= start && end <= right) { //구간 내의 존재할 경우
                return tree[nodeIdx];
            }
            int mid = (start + end) / 2;
            Long leftChild = query(start, mid, left, right, nodeIdx * 2);
            Long rightChild = query(mid + 1, end, left, right, nodeIdx * 2 + 1);
            return leftChild + rightChild;
        }

        void update(int start, int end, int diffIdx, long diff, int nodeIdx) {
            if (diffIdx < start || diffIdx > end) {
                return;
            }
            tree[nodeIdx] += diff;
            if (start != end) {
                int mid = (start + end) / 2;
                update(start, mid, diffIdx, diff, nodeIdx * 2);
                update(mid + 1, end, diffIdx, diff, nodeIdx * 2 + 1);
            }
        }
    }
}
