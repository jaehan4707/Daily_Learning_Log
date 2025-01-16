import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, m, k;

    static StringBuilder sb;

    static char[][] graph;
    static int[][] black;
    static int[][] white;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(Math.min(solution('B'), solution('W')));
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new char[n + 1][m + 1];
        black = new int[n + 1][m + 1];
        white = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            String input = br.readLine();
            for (int j = 1; j <= m; j++) {
                graph[i][j] = input.charAt(j - 1);
            }
        }
    }

    public static int solution(char color) {

        int[][] paint = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int flag = 0;
                if ((i + j) % 2 == 0) { //여기는 색깔을 칠함
                    if (graph[i][j] != color) { //칠해져야 함.
                        flag = 1;
                    }
                } else {
                    if (graph[i][j] == color) { // 칠해지면 안됨.
                        flag = 1;
                    }
                }
                paint[i][j] = paint[i - 1][j] + paint[i][j - 1] - paint[i - 1][j - 1] + flag;
            }
        }
        int cnt = Integer.MAX_VALUE;
        for (int i = k; i <= n; i++) {
            for (int j = k; j <= m; j++) {
                cnt = Math.min(cnt, paint[i][j] - (paint[i - k][j] + paint[i][j - k] - paint[i - k][j - k]));
            }
        }
        return cnt;
    }
}