import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m;
    static ArrayList<ArrayList<Integer>> student;

    static int[][] graph;

    static int[] seq;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        System.out.println(solution());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = (int) Math.pow(n, 2);
        seq = new int[m + 1];
        graph = new int[n + 1][n + 1];
        student = new ArrayList<>();
        for (int i = 0; i <= m; i++) {
            student.add(new ArrayList<>());
        }
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            seq[i] = s;
            for (int j = 0; j < 4; j++) {
                student.get(s).add(Integer.parseInt(st.nextToken()));
            }
        }
    }

    public static long solution() {
        //1 인접한 학생이 가장 많은 칸
        //2 비어있는 칸이 가장 많은 곳
        // 행의 번호가 작고 , 열의 번호가 작은 곳
        for (int i = 1; i <= m; i++) {
            int now = seq[i];
            int maxStudent = -1;
            int maxBlank = -1;
            Point goal = new Point(m + 1, m + 1);
            for (int j = 1; j <= n; j++) { //인접한 칸 계산
                for (int k = 1; k <= n; k++) {
                    if (graph[j][k] != 0) {
                        continue;
                    }
                    int cnt = 0;
                    int empty = 0;
                    for (int d = 0; d < 4; d++) {
                        int mx = j + dx[d];
                        int my = k + dy[d];
                        if (!checkRange(mx, my)) {
                            continue;
                        }
                        int here = graph[mx][my];
                        if (here == 0) {
                            empty += 1;
                        } else {
                            if (student.get(now).contains(here)) { //만약 좋아하는 학생이라면,
                                cnt += 1;
                            }
                        }
                    }
                    if (maxStudent < cnt) { // 값이 더 클 때
                        maxBlank = empty;
                        maxStudent = cnt;
                        goal.x = j;
                        goal.y = k;
                    } else if (maxStudent == cnt) { // 좋아하는 사람 칸이 같을 경우, 빈칸으로 검사함
                        Point p = new Point(j, k);
                        if (maxBlank < empty) { // 빈칸의 개수가 더 크다면,
                            maxBlank = empty;
                            goal.x = j;
                            goal.y = k;
                        } else if (maxBlank == empty) { // 좋아하는 사람도 같을 경우, 행과 열을 비교함.
                            int result = comparePoint(goal, p);
                            if (result == 1) {
                                goal.x = p.x;
                                goal.y = p.y;
                            }
                        }
                    }
                }
            }
            graph[goal.x][goal.y] = now;
        }
        long answer = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int now = graph[i][j];
                int c = 0;
                for (int d = 0; d < 4; d++) {
                    int mx = i + dx[d];
                    int my = j + dy[d];
                    if (!checkRange(mx, my)) {
                        continue;
                    }
                    int next = graph[mx][my];
                    if (student.get(now).contains(next)) {
                        c += 1;
                    }
                }
                answer += Math.pow(10, c - 1);
            }
        }
        return answer;
    }

    public static boolean checkRange(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            return false;
        }
        return true;
    }

    public static int comparePoint(Point p1, Point p2) {
        if (p1.x == p2.x) {
            return Integer.compare(p1.y, p2.y);
        }
        return Integer.compare(p1.x, p2.x);
    }

    static class Point {
        int x, y;

        Point() {

        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
