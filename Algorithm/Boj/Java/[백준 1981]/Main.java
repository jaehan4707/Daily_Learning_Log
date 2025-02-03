import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n;

    static int[][] graph;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int minV = Integer.MAX_VALUE;
    static int maxV = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        input();
        System.out.println(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        graph = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
                minV = Math.min(graph[i][j], minV);
                maxV = Math.max(graph[i][j], maxV);
            }
        }
    }

    public static int solution() {
        int left = 0; // 최대 - 최소의 최소값이 0
        int right = maxV - minV; //최대값이 200
        while (left <= right) {
            int mid = (left + right) / 2;
            if (!bfs(mid)) { //불가능하면 범위를 늘림
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public static boolean bfs(int value) {
        for (int i = minV; i <= maxV; i++) {
            int s = i;
            int e = i + value;
            if (e > maxV) {
                break;
            }
            if (graph[1][1] < s || graph[1][1] > e) {
                continue;
            }
            boolean[][] visit = new boolean[n + 1][n + 1];
            Queue<Point> q = new LinkedList<>();
            visit[1][1] = true;
            q.add(new Point(1, 1));
            while (!q.isEmpty()) {
                Point now = q.poll();
                for (int d = 0; d < 4; d++) {
                    int mx = now.x + dx[d];
                    int my = now.y + dy[d];
                    if (!isInMap(mx, my) || visit[mx][my] || graph[mx][my] < s || graph[mx][my] > e) {
                        continue;
                    }
                    if (mx == n && my == n) {
                        return true;
                    }
                    q.add(new Point(mx, my));
                    visit[mx][my] = true;
                }
            }
        }
        return false;
    }

    public static boolean isInMap(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            return false;
        }
        return true;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}