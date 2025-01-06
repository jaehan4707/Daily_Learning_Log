import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static Runner[] runners;

    static int[] tree;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        runners = new Runner[n];
        tree = new int[n * 4];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            runners[i] = new Runner(i, Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(runners, (r1, r2) -> (r1.rank - r2.rank));
        for (int i = 0; i < n; i++) {
            runners[i].rank = i + 1;
        }
        Arrays.sort(runners, (r1, r2) -> (r1.idx - r2.idx));
        for (int i = 1; i <= n; i++) {
            int rank = runners[i - 1].rank;
            sb.append(i - query(1, n, 1, 1, rank)).append("\n");
            update(1, n, 1, rank);
        }
    }

    static int query(int start, int end, int node, int left, int right) {
        // 범위거 밧어날 경우
        if (start > right || left > end) {
            return 0;
        }
        // 범위 내 일경우
        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        return query(start, mid, node * 2, left, right) + query(mid + 1, end, node * 2 + 1, left, right);
    }

    static int update(int start, int end, int node, int target) {
        if (target < start || end < target) return tree[node];
        if (start == end) {
            return tree[node] += 1;
        }
        int mid = (start + end) / 2;
        return tree[node] = update(start, mid, node * 2, target) + update(mid + 1, end, node * 2 + 1, target);
    }


    static class Runner {
        int idx, rank;

        Runner(int idx, int rank) {
            this.idx = idx;
            this.rank = rank;
        }
    }
}
