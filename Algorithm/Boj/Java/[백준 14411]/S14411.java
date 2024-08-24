import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;

    static PriorityQueue<Rectangle> pq;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        pq = new PriorityQueue<>((o1, o2) -> { // x 내림차순 -> y 내림차순
            if (o1.x != o2.x) {
                return Long.compare(o2.x, o1.x);
            }
            if (o1.y != o2.y) {
                return Long.compare(o2.y, o1.y);
            }
            return 0;
        });

        long x, y = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            x = Long.parseLong(st.nextToken()); // 가로
            y = Long.parseLong(st.nextToken()); // 세로
            pq.add(new Rectangle(x, y));
        }
    }

    public static long solution() {
        long answer = 0;
        long lastX = 0, lastY = 0;
        while (!pq.isEmpty()) {
            Rectangle now = pq.poll();
            if (now.y > lastY && now.x > lastX) { // 처음 사각형
                answer += Math.abs((now.x - lastX) * (now.y - lastY));
                lastY = now.y;
                lastX = now.x;
            } else if (lastY <= now.y) {
                answer += Math.abs((now.x) * (now.y - lastY));
                lastX = now.x;
                lastY = now.y;
            }
        }
        return answer;
    }

    public static class Rectangle {
        long x, y;

        public Rectangle(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
