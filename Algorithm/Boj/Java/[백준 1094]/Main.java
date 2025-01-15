import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int x;

    static StringBuilder sb;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        x = Integer.parseInt(st.nextToken());
    }

    public static int solution() {
        return Integer.bitCount(x);
    }
}
