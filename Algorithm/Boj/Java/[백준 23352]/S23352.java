import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m,answer,dist;
    static int[][] graph;
    static boolean[][] visited;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.println(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (graph[i][j] == 0) {
                    continue;
                }
                search(i,j);
            }
        }
    }

    public static void search(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y));
        visited = new boolean[n][m];
        visited[x][y] = true;
        int maxV=0, maxI = 0;
        while (!q.isEmpty()) {
            Point now = q.poll();
            if(maxI<now.v){
                maxV  = graph[now.x][now.y];
                maxI = Math.max(maxI,now.v);
            } else if(maxI == now.v){
                maxV = Math.max(maxV,graph[now.x][now.y]);
            }
            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (!checkRange(nx, ny) || visited[nx][ny] || graph[nx][ny] == 0) {
                    continue;
                }
                visited[nx][ny] = true;
                q.add(new Point(nx, ny, now.v + 1));
            }
        }
        if(dist<maxI){
            answer = maxV+graph[x][y];
            dist = maxI;
        } else if(dist==maxI){
            answer = Math.max(answer,maxV+graph[x][y]);
        }
    }

    public static boolean checkRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y, v;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        Point(int x, int y,int v ) {
            this.x = x;
            this.y = y;
            this.v = v;
        }
    }
}
