import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n, m, x, y, k;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    static int[][] graph;

    static int[] dice = {0, 0, 0, 0, 0, 0, 0};

    public static void main(String[] args) throws IOException {
        input();
        System.out.println(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            solution(Integer.parseInt(st.nextToken()) - 1);
        }
    }

    public static void solution(int opt) {
        int mx = x + dx[opt];
        int my = y + dy[opt];
        if (!checkRange(mx, my)) {
            return;
        }
        x = mx;
        y = my;
        rotate(opt);
        // 현재 내 위치에서 방향에 위치한 면이 바닥이 됨.
        if (graph[mx][my] == 0) { // 이동한 칸이 0이라면 주사위 바닥면에 쓰여있는 수가 복사.
            graph[mx][my] = dice[6]; //바닥면 복사
        } else { //칸에 쓰여있는 수가 주사위 바닥으로 복사, 칸은 0
            dice[6] = graph[mx][my];
            graph[mx][my] = 0;
        }
        sb.append(dice[1]).append("\n");
    }

    public static void rotate(int opt) {
        int topNumber = dice[1];
        if (opt == 0) { //동쪽으로 굴릴 때
            dice[1] = dice[4]; //위는 서쪽이 먹음
            dice[4] = dice[6];
            dice[6] = dice[3];
            dice[3] = topNumber;//오른쪽은?
        } else if (opt == 1) { //서쪽
            dice[1] = dice[3];
            dice[3] = dice[6];
            dice[6] = dice[4];
            dice[4] = topNumber;
        } else if (opt == 2) { //북쪽
            dice[1] = dice[5];
            dice[5] = dice[6];
            dice[6] = dice[2];
            dice[2] = topNumber;
        } else { //남쪽
            dice[1] = dice[2];
            dice[2] = dice[6];
            dice[6] = dice[5];
            dice[5] = topNumber;
        }
    }


    public static boolean checkRange(int mx, int my) {
        if (mx >= n || mx < 0 || my >= m || my < 0) {
            return false;
        }
        return true;
    }
}