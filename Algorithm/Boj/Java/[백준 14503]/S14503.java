import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;

    static StringBuffer sb;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int n, m, r, c, d;

    static int[][] graph;
    static int answer = 1;

    static class Dir {
        int d, seq;

        Dir(int d, int seq) {
            this.d = d;
            this.seq = seq;
        }
    }

    static Queue<Point> q = new LinkedList<>();


    public static void main(String[] args) throws IOException {
        input();
        solution(r, c, d);
        System.out.println(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution(int x, int y, int d) {
        graph[x][y] = 2;
        int dir = d;
        for (int i = 0; i < 4; i++) {
            dir = (dir + 3) % 4;
            int mx = x + dx[dir];
            int my = y + dy[dir];
            if (!inGraph(mx, my)) {
                continue;
            }
            if (graph[mx][my] == 0) {
                answer += 1;
                solution(mx, my, dir);
                return;
            }
        }
        int dd = (d + 2) % 4;
        int mx = x + dx[dd];
        int my = y + dy[dd];
        if (!inGraph(mx, my)) {
            return;
        }
        if (graph[mx][my] == 0) {
            answer += 1;
            solution(mx, my, d);
            return;
        } else if(graph[mx][my]==2){
            solution(mx,my,d);
        }
    }


    public static boolean inGraph(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    static class Point {
        int x, y, d;

        Point(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}