import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int n, k;

    static String str;

    static long[][] dp;

    static long[][] min;
    static long[][] max;


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        input();
        long answer = 0;
        if (n == 1) {
            answer = str.charAt(0) - '0';
        } else {
            answer = solution();
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        min = new long[n][n];
        max = new long[n][n];
        st = new StringTokenizer(br.readLine());
        str = st.nextToken();
        for (int i = 0; i < n; i += 2) {
            Arrays.fill(min[i], Integer.MAX_VALUE);
            Arrays.fill(max[i], Integer.MIN_VALUE);
            min[i][i] = str.charAt(i) - '0';
            max[i][i] = str.charAt(i) - '0';
        }
    }

    public static long solution() {
        // i~k k~j
        for (int j = 2; j < n; j += 2) {  // 2,(4,6,8,---n) 4,(6,8, --- n) 6,(8,10 ---n)
            for (int i = 0; i < n - j; i += 2) {//
                for (int k = 2; k <= j; k += 2) {
                    char op = str.charAt(i + k - 1);
                    long[] sum = new long[4];
                    sum[0] = calculation(max[i][i + k - 2], max[i + k][i + j], op);
                    sum[1] = calculation(max[i][i + k - 2], min[i + k][i + j], op);
                    sum[2] = calculation(min[i][i + k - 2], max[i + k][i + j], op);
                    sum[3] = calculation(min[i][i + k - 2], min[i + k][i + j], op);
                    Arrays.sort(sum);
                    max[i][i + j] = Math.max(max[i][i + j], sum[3]);
                    min[i][i + j] = Math.min(min[i][i + j], sum[0]);
                    // 최대값 * 최대값 ->
                }
            }
        }
        return max[0][n - 1];
    }

    public static long calculation(long num1, long num2, char op) {
        long answer = 0;
        switch (op) {
            case '+':
                answer = num1 + num2;
                break;

            case '-':
                answer = num1 - num2;
                break;
            case '*':
                answer = num1 * num2;
                break;

            default:
                answer = -1;
        }
        return answer;
    }
}
