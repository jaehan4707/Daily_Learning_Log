import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int m;
    static int r;
    static int[][] graph;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        input();
        int newN = n;
        int newM = m;
        for (int i = 0; i < Math.min(n, m) / 2; i++) {
            rotate(i, 2 * newN + 2 * newM - 4);
            newN -= 2;
            newM -= 2;
        }
        printGraph();
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void rotate(int start, int length) {
        int number = r % length; //회전수
        for (int i = 0; i < number; i++) {
            Point p1 = new Point(start, start, graph[start][start]); //왼쪽 위
            Point p2 = new Point(start, m - start - 1, graph[start][m - start - 1]); //오른쪽 위
            Point p3 = new Point(n - start - 1, start, graph[n - start - 1][start]); //왼쪽 아래
            Point p4 = new Point(n - start - 1, m - start - 1, graph[n - start - 1][m - start - 1]); //오른쪽 아래

            //윗변 움직이기
            for (int j = start; j < m - 1 - start; j++) {
                graph[start][j] = graph[start][j + 1];
            }
            //왼쪽변 움직이기
            for (int j = n - start - 1; j > start + 1; j--) {
                graph[j][start] = graph[j - 1][start];
            }
            //아래변 움직이기
            for (int j = m - 1 - start; j > start + 1; j--) {
                graph[n - 1 - start][j] = graph[n - 1 - start][j - 1];
            }
            //오른쪽변 움직이기
            for (int j = start; j < n - 1 - start; j++) {
                graph[j][m - 1 - start] = graph[j + 1][m - 1 - start];
            }
            //각각의 꼭짓점 다시 넣어주기
            graph[p1.x + 1][p1.y] = p1.v;
            graph[p2.x][p2.y - 1] = p2.v;
            graph[p3.x][p3.y + 1] = p3.v;
            graph[p4.x - 1][p4.y] = p4.v;
        }
    }

    public static void printGraph() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Point {
        int x;
        int y;
        int v;

        public Point(int x, int y, int v) {
            this.x = x;
            this.y = y;
            this.v = v;
        }
    }
}