import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int m;

    static long answer = 0;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        input();
        System.out.println(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            if (order.equals("all") || order.equals("empty")) {
                solution(order, 0);
            } else {
                int x = Integer.parseInt(st.nextToken());
                if (order.equals("check")) {
                    solution(order, x);
                } else {
                    solution(order, x);
                }
            }
        }
    }

    public static void solution(String order, int x) {
        int now = 1 << x;
        switch (order) {
            case "add" -> {
                answer |= now;
            }
            case "remove" -> {
                answer &= ~now;
            }
            case "check" -> {
                if ((answer & now) >= 1) {
                    sb.append(1).append("\n");
                } else {
                    sb.append(0).append("\n");
                }
            }
            case "toggle" -> {
                answer ^= now;
            }
            case "all" -> {
                answer = (1 << 21) - 1;
            }
            case "empty" -> {
                answer = 0;
            }
        }
    }
}