import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static long p, q, r,S, max = 200000L;
    static int n;
    static int[] a;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        p = Long.parseLong(st.nextToken());
        q = Long.parseLong(st.nextToken());
        r = Long.parseLong(st.nextToken());
        S = Long.parseLong(st.nextToken());
    }

    public static long solution() {
        long left = 1;
        long right = max;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (calculate(mid)) {
                left = mid +1;
            } else {
                right = mid -1;
            }
        }

        return  left > max ? -1 : left;
    }

    public static boolean calculate(long value) {
        long sum = 0L;
        for (int i = 0; i < n; i++) {
            sum += a[i];
            if (a[i] < value) {
                sum += q;
            } else if (a[i] > value + r) {
                sum -= p;
            }
        }
        return sum < S;
    }
}
