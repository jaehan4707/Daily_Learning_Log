import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br;
    static int n, m;

    static StringBuffer sb;
    static StringTokenizer st;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static char[][] graph;

    static int[][] visit;
    static int answer = 0;
    static HashMap<Character, Integer> dir;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuffer();
        dir = new HashMap<>();
        makeDir();
        input();
        br.close();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visit[i][j] == 1) {
                    answer += 1;
                    continue;
                }
                if (search(i, j)==1) {
                    answer += 1;
                }
            }
        }
        System.out.print(answer);
    }

    public static void makeDir() {
        dir.put('U', 0);
        dir.put('R', 1);
        dir.put('D', 2);
        dir.put('L', 3);
    }

    public static void input() throws IOException {
        //이렇게 하면 무조건 시간초과인데.. 어느 지점을 밟아서 가버리면 그 지점들을 기록해야 하는데
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new char[n][m];
        visit = new int[n][m];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                graph[i][j] = line.charAt(j);
                visit[i][j] =-1;
            }
        }
    }

    public static int search(int x, int y) {
        // 해당 지점을 밟아서 이미 탈출했다면?
        if (visit[x][y] != -1) { //처음 방문이 아닐 경우
            return visit[x][y];
        }
        visit[x][y] = 0;
        Character alpha = graph[x][y];
        int mx = x + dx[dir.get(alpha)];
        int my = y + dy[dir.get(alpha)];
        if (isOutSide(mx, my)) {
            return visit[x][y] = 1;
        }

        return visit[x][y] = search(mx, my);
    }


    public static boolean isOutSide(int x, int y) {
        if (x < 0 || x >= n || y < 0 || y >= m) {
            return true;
        }
        return false;
    }
}
