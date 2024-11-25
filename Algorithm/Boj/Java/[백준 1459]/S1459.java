import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int x, y, w, s;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
    }

    public static long solution() {
        int min = Math.min(x, y);
        int max = Math.max(x, y);
        long p1 = (long) (x + y) * w; // 평행 이동
        long p2 = (long) min * s + (long) (max - min) * w; // 평행 + 대각선
        long p3;
        if ((x + y) % 2 == 0) { //짝수 일 경우
            p3 = (long) max * s;
        } else { //홀수일 경우
            p3 = (long) (max - 1) * s + w;
        }
        return Math.min(p1, Math.min(p2, p3));
    }
}
