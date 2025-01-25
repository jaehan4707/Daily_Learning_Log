import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n;
    static int[] parent;

    static int[] cnt;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        parent = new int[1000001];
        cnt = new int[1000001];
        for (int i = 0; i <= 1000000; i++) {
            parent[i] = i;
            cnt[i] = 1;
        }
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String opt = st.nextToken();
            if (opt.equals("I")) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a, b);
            } else {
                int c = Integer.parseInt(st.nextToken());
                sb.append(count(find(c))).append("\n");
            }
        }
    }

    public static int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public static void union(int a, int b) {
        //a == b 같은 로봇
        a = find(a);
        b = find(b);

        if (a == b) {
            return;
        }
        parent[b] = a;
        cnt[a] += cnt[b];
    }

    public static int count(int x) {
        return cnt[x];
    }
}