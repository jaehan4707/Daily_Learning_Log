import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n, answer;

    static Point[] points;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        points = new Point[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
        }
        Arrays.sort(points, (p1, p2) -> {
            if (p1.x == p2.x) {
                return p1.y - p2.y;
            }
            return p1.x - p2.x;
        });
    }

    public static void solution() {
        int left = points[0].x;
        int right = points[0].y;
        for (int i = 1; i < n; i++) {
            Point cur = points[i];
            if (right < cur.x) { //선분이 끊김
                answer += right-left;
                left = cur.x;
                right = cur.y;
            } else { // 선분이 이어질 때
                right = Math.max(right, cur.y);
            }
        }
        answer += right - left;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
