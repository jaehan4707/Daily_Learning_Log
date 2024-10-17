import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int[] dx = {0, -1, 1, 0, 0};
    static int[] dy = {0, 0, 0, 1, -1};


    static Point[] points;

    static long[][] graph;

    public static void main(String[] args) throws IOException {
        long answer = Long.MAX_VALUE;
        input();
        solution();
        for (int i = 0; i <= 4; i++) {
            answer = Math.min(answer, graph[n][i]);
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        points = new Point[n + 1];
        graph = new long[n+1][5];
        for (int i = 0; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            points[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        for (int i = 0; i <= n; i++) {
            Arrays.fill(graph[i], Long.MAX_VALUE);
        }
    }

    public static void solution() {
        graph[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            Point now = points[i];
            Point temp = points[i - 1];
            for (int d = 0; d <= 4; d++) {
                if (i - 1 == 0) { //출발지
                    graph[i][d] = graph[i - 1][0] + diff(now.x + dx[d], temp.x, now.y + dy[d], temp.y);
                }
                else {
                    for (int k = 0; k <= 4; k++) {
                        graph[i][d] = Math.min(graph[i][d], graph[i - 1][k] + diff(now.x + dx[d], temp.x + dx[k], now.y + dy[d], temp.y + dy[k]));
                    }
                }
            }
        }
    }

    public static int diff(int x1, int x2, int y1, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
