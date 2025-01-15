import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.StreamSupport;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m;

    static StringBuilder sb;
    static int[][] graph;

    static int[][] label;

    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {-1, 0, 1, 0};

    static ArrayList<Area> areas;

    public static void main(String[] args) throws IOException {
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        label = new int[n][m];
        areas = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution() {
        int mark = 0;
        int cnt = 0;
        int maxS = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (label[i][j] != 0) {
                    continue;
                }
                mark += 1;
                areas.add(bfs(mark, i, j));
                cnt += 1;
            }
        }
        int sum = 0;
        for (int i = 0; i < areas.size(); i++) {
            Area area = areas.get(i);
            maxS = Math.max(area.s, maxS);
            for (int v : area.visit) {
                if (v - 1 == i || v == 0) {
                    continue;
                }
                sum = Math.max(sum, area.s + areas.get(v - 1).s);
            }
        }
        System.out.println(cnt + "\n" + maxS + "\n" + sum);
    }

    public static Area bfs(int mark, int x, int y) {
        int s = 0;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y));
        HashSet<Integer> visit = new HashSet<>();
        label[x][y] = mark;
        while (!q.isEmpty()) {
            Point now = q.poll();
            int number = graph[now.x][now.y];
            s += 1;
            for (int d = 0; d < 4; d++) {
                int mx = now.x + dx[d];
                int my = now.y + dy[d];
                if (!isInMap(mx, my)) {
                    continue;
                }
                visit.add(label[mx][my]);
                if ((number & (1 << d)) >= 1 || label[mx][my] != 0) {
                    continue;
                }
                q.add(new Point(mx, my));
                label[mx][my] = mark;
            }
        }
        return new Area(s, visit);
    }

    static boolean isInMap(int x, int y) {
        if (x < 0 || x >= n || y < 0 || y >= m) {
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

    static class Area {
        int s;
        HashSet<Integer> visit;

        Area(int s, HashSet<Integer> visit) {
            this.s = s;
            this.visit = visit;
        }
    }
}