import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int n, k;
    static int[][] graph;

    static int[] dx = {0, 0, 0, -1, 1};
    static int[] dy = {0, 1, -1, 0, 0};

    static ArrayList<Chess> chess;

    static ArrayList<Chess>[][] map;


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new int[n + 1][n + 1];
        map = new ArrayList[n + 1][n + 1];

        chess = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
                map[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Chess ch = new Chess(i + 1, r, c, d);
            chess.add(ch);
            map[r][c].add(ch);
        }
    }

    public static long solution() {
        long turn = 0;
        while (turn <= 1000) {
            turn += 1;
            for (Chess c : chess) {
                int nx = c.x;
                int ny = c.y;
                if (map[nx][ny].size() >= 4) {
                    return turn;
                }
                // 체스판을 벗어나는 경우
                boolean isFirst = isFirst(nx, ny, c); // 가장 아래 말인지,
                if (!isFirst) { //아니라면 이동 X
                    continue;
                }

                int mx = nx + dx[c.d];
                int my = ny + dy[c.d];
                move(c, mx, my, nx, ny);

                if (map[c.x][c.y].size() >= 4) {
                    return turn;
                }
            }
        }
        return -1;
    }

    static void move(Chess c, int mx, int my, int nx, int ny) {
        if (!isInMap(mx, my) || graph[mx][my] == 2) {
            c.changeDirection(); //방향 바꿈
            int newX = nx + dx[c.d];
            int newY = ny + dy[c.d];
            if (!isInMap(newX, newY) || graph[newX][newY] == 2) { // 밖으로 벗어나거나, 파란색일때 방향만 바꾸고 되돌아가야함.
                return;
            }
            move(c, newX, newY, nx, ny);
        } else if (graph[mx][my] == 0) { // 흰
            for (Chess ch : map[c.x][c.y]) {
                ch.move(c.d);
                map[mx][my].add(ch);
            }
            map[nx][ny].clear();
        } else if (graph[mx][my] == 1) { //빨간색
            for (int k = map[nx][ny].size() - 1; k >= 0; k--) {
                Chess ch = map[nx][ny].get(k);
                ch.move(c.d);
                map[mx][my].add(ch);
            }
            map[nx][ny].clear();
        }
    }

    static boolean isFirst(int x, int y, Chess c) {
        if (map[x][y].get(0).s == c.s) {
            return true;
        }
        return false;
    }

    static boolean isInMap(int x, int y) {
        if (x <= 0 || x > n || y <= 0 || y > n) {
            return false;
        }
        return true;
    }

    static class Chess {
        int s, x, y, d;

        Chess(int s, int x, int c, int d) {
            this.s = s;
            this.x = x;
            this.y = c;
            this.d = d;
        }

        void move(int d) {
            this.x += dx[d];
            this.y += dy[d];
        }

        void changeDirection() {
            switch (this.d) {
                case 1:
                    this.d = 2;
                    break;
                case 2:
                    this.d = 1;
                    break;
                case 3:
                    this.d = 4;
                    break;
                case 4:
                    this.d = 3;
                    break;
            }
        }
    }
}
