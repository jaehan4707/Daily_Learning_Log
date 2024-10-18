import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int n, cnt = 0;
    static int[] dx = {-1, 1, 0, 0, 1, 1, -1, -1};
    static int[] dy = {0, 0, -1, 1, 1, -1, 1, -1};

    static char[][] graph;

    static int[][] height;

    static Point start;

    static TreeSet<Integer> heights;


    public static void main(String[] args) throws IOException {
        input();
        Integer[] list = heights.toArray(new Integer[0]);
        int left = 0;
        int right = 0;
        int answer = Integer.MAX_VALUE;
        while (right < list.length) {
            if (solution(list[left], list[right])) {
                answer = Math.min(answer, Math.abs(list[right] - list[left]));
                if (left == right) {
                    break;
                } else {
                    left += 1;
                }
            } else {
                right += 1;
            }
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        graph = new char[n][n];
        height = new int[n][n];
        heights = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                graph[i][j] = str.charAt(j);
                if (str.charAt(j) == 'P') {
                    start = new Point(i, j);
                } else if (str.charAt(j) == 'K') {
                    cnt += 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());
                heights.add(height[i][j]);
            }
        }
        start.m = height[start.x][start.y];
        start.h = height[start.x][start.y];
    }

    public static boolean solution(int left, int right) {
        Queue<Point> pq = new LinkedList<>();
        if (height[start.x][start.y] < left || height[start.x][start.y] > right) {
            return false;
        }
        boolean[][] visit = new boolean[n][n];
        int c = 0;
        pq.add(start);
        while (!pq.isEmpty()) {
            Point now = pq.poll();
            for (int d = 0; d < 8; d++) {
                int mx = now.x + dx[d];
                int my = now.y + dy[d];
                if (isOutSide(mx, my)) {
                    continue;
                }
                if (height[mx][my] < left || height[mx][my] > right) {
                    continue;
                }
                if (visit[mx][my]) {
                    continue;
                }
                int nowH = Math.max(now.h, height[mx][my]);
                int nowM = Math.min(now.m, height[mx][my]);
                if (graph[mx][my] == 'K') {
                    c += 1;
                    if (c == cnt) {
                        return true;
                    }
                }
                pq.add(new Point(mx, my, nowM, nowH));
                visit[mx][my] = true;
            }
        }
        return false;
    }

    public static boolean isOutSide(int x, int y) {
        if (x >= n || x < 0 || y >= n || y < 0) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y, m, h;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(int x, int y, int m, int h) {
            this.x = x;
            this.y = y;
            this.m = m;
            this.h = h;
        }
    }

}