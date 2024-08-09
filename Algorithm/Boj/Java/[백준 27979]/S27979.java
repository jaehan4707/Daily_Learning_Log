import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, answer;
    static PriorityQueue<Integer> pq = new PriorityQueue<>();

    static Queue<Integer> q = new LinkedList<>();

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            q.add(Integer.parseInt(st.nextToken()));
        }
    }

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void solution() {
        int beforeValue = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur < beforeValue) {
                answer++;
                int value = cur;
                while (!q.isEmpty()) {
                    if (q.peek() >= beforeValue) {
                        break;
                    }
                    answer++;
                    value = Math.max(value, q.poll());
                }
                if (!q.isEmpty()) {
                    beforeValue = q.peek();
                }
                while (!pq.isEmpty()) {
                    if (pq.peek() >= value) {
                        break;
                    }
                    pq.poll();
                    answer++;
                }
            } else {
                beforeValue = cur;
                pq.add(cur);
            }
        }
    }

}