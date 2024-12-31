import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static long answer = Long.MAX_VALUE;
    static int[][] graph;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void solution() {
        for (int i = 1; i < (1 << n) - 1; i++) {
            answer = Math.min(answer, dfs(i));
        }
    }

    public static long dfs(int num) {
        ArrayList<Integer> start = new ArrayList<>();
        ArrayList<Integer> link = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int now = 1 << i;
            if ((num & now) >= 1) {
                start.add(i);
            } else {
                link.add(i);
            }
        }
        return Math.abs((calculate(start) - calculate(link)));
    }

    public static long calculate(ArrayList<Integer> list) {
        long score = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                score += graph[list.get(i)][list.get(j)];
            }
        }
        return score;
    }
}
