import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n, k;

    static long[] numbers;

    public static void main(String[] args) throws IOException {
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        k = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        numbers = new long[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void solution() {
        HashSet<Long> set = new HashSet<>();
        PriorityQueue<Long> pq = new PriorityQueue<>();
        long maxV = 0;
        for (int i = 0; i < k; i++) {
            set.add(numbers[i]);
            pq.add(numbers[i]);
            maxV = Math.max(maxV, numbers[i]);
        }
        int seq = 0;
        while (!pq.isEmpty()) {
            long now = pq.poll();
            seq += 1;
            if (seq == n) {
                System.out.println(now);
                break;
            }
            for (long num : numbers) {
                long result = now * num;
                if (result >= 2L << 30 || set.contains(result)) {
                    continue;
                }

                if (pq.size() > n && maxV <= result) {
                    continue;
                }
                pq.add(result);
                maxV = Math.max(maxV, result);
                set.add(result);
            }
        }
    }
}