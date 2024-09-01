import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;

    static long l;

    static long answer = 0;
    static long[] ary;

    static long[] forwardSum;

    static long[] dp;
    static long[] reDp;


    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        ary = new long[n + 1];
        forwardSum = new long[n + 1];
        dp = new long[n + 1];
        reDp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            ary[i] = Long.parseLong(st.nextToken());
            answer += ary[i];
            forwardSum[i] = ary[i] + forwardSum[i - 1];
        }
    }

    public static void solution() {
        //얘를 껏을때 구간이 이어지냐 안이어지냐?
        long forwardValue = ary[1];
        dp[1] = ary[1];
        for (int i = 2; i <= n; i++) { //앞에서의 구간의 최대합
            forwardValue = Math.max(ary[i], forwardValue + ary[i]); //이어가는게 좋은지, 새로 시작하는게 좋은지 비교
            dp[i] = Math.max(dp[i - 1], forwardValue);
        }

        long reverseValue = ary[n];
        reDp[n] = ary[n];
        for (int i = n - 1; i >= 0; i--) {
            reverseValue = Math.max(ary[i], reverseValue + ary[i]);
            reDp[i] = Math.max(reDp[i + 1], reverseValue);
        }

        long maxValue = Math.max(dp[n], 0);
        for (int i = 1; i < n; i++) {
            maxValue = Math.max(maxValue, dp[i] + reDp[i + 1]);
        }
        answer += maxValue;
    }
}
