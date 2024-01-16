import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int m;
    static int r;
    static int opt;
    static int[][] graph;
    static int[][] after;
    static int[][] graph1;
    static int[][] graph2;
    static int[][] graph3;
    static int[][] graph4;

    static int maxNum;

    static int[] operand;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        input();
        int temp;
        for (int i = 0; i < r; i++) {
            opt = operand[i];
            if (opt <= 2) {
                rotateReverse();
            } else if (opt <= 4) {
                rotate90();
                temp = n;
                n = m;
                m = temp;
            } else if (opt <= 6) {
                divideGraph();
                rotateSpecial();
            }
        }
        printGraph();
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        maxNum = Math.max(n, m);
        graph = new int[maxNum][maxNum];
        after = new int[maxNum][maxNum];
        operand = new int[r];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < m; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < r; i++) {
            operand[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void rotateReverse() { //상하 , 좌우
        int a;
        if (opt == 1) {
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < m; j++) {
                    a = graph[i][j];
                    graph[i][j] = graph[n - 1 - i][j]; // 거꾸로 넣기
                    graph[n - 1 - i][j] = a;
                }
            }
        } else if (opt == 2) {
            for (int i = 0; i < m / 2; i++) {
                for (int j = 0; j < n; j++) {
                    a = graph[j][i];
                    graph[j][i] = graph[j][m - 1 - i];
                    graph[j][m - 1 - i] = a;
                }
            }
        }
    }

    public static void rotate90() {
        int a;
        after = new int[m][n];
        if (opt == 3) {
            for (int i = 0; i < n; i++) { //0번째 행은 m-1열로, 1번째 행은 m-2열로,
                for (int j = 0; j < m; j++) {
                    after[j][n - 1 - i] = graph[i][j];
                }
            }
        }
        if (opt == 4) { //왼쪽으로 90도
            for (int i = 0; i < n; i++) { //0번쨰 행은 0번째 열로,//1번째 행은 1번째 열로,//2번째 행은 2번째 열로
                for (int j = 0; j < m; j++) {
                    after[m - 1 - j][i] = graph[i][j];
                }
            }
        }
        graph = after;
    }

    public static void divideGraph() {
        //배열을 4분할 해야하나?
        graph1 = new int[maxNum / 2][maxNum / 2];
        graph2 = new int[maxNum / 2][maxNum / 2];
        graph3 = new int[maxNum / 2][maxNum / 2];
        graph4 = new int[maxNum / 2][maxNum / 2];
        //4분면 나누기
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph1[i][j] = graph[i][j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph2[i][j] = graph[i][m / 2 + j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph3[i][j] = graph[n / 2 + i][j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph4[i][j] = graph[n / 2 + i][m / 2 + j];
            }
        }
    }

    public static void assembleGraph(int[][] one, int[][] two, int[][] three, int[][] four) {
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph[i][j] = one[i][j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph[i][m / 2 + j] = two[i][j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph[n / 2 + i][j] = three[i][j];
            }
        }
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < m / 2; j++) {
                graph[n / 2 + i][m / 2 + j] = four[i][j];
            }
        }
    }

    public static void rotateSpecial() {
        if (opt == 5) {
            assembleGraph(graph3, graph1, graph4, graph2); //1->2 2->3 3->4 4->1
        } else {
            assembleGraph(graph2, graph4, graph1, graph3); //1->4 2->1 3->2 4->3
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
}