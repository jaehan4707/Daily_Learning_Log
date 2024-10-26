import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n;

    static int[] ary;

    static long totalSum = 0;

    static long[][] sum;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        ary = new int[n];
        sum = new long[n][2];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            ary[i] = Integer.parseInt(st.nextToken());
            totalSum += ary[i];
        }
    }

    public static Long solution() {
        long tempSum = 0;
        for (int i = 0; i < n; i++) {
            sum[i][0] = tempSum;
            tempSum += ary[i];
            sum[i][1] = totalSum - tempSum;
        }
        // 벌 벌 꿀통
        long sum1 = 0L;
        for (int i = 1; i < n - 1; i++) {
            if (sum[0][1] + sum[i][1] - ary[i] > sum1) {
                sum1 = sum[i][1] - ary[i] + sum[0][1];
            }
        }
        // 벌 꿀 벌
        long sum2 = 0L;
        for (int i = 1; i < n - 1; i++) { //꿀통 위치
            if (sum2 < sum[0][1] - sum[i][1] + sum[n - 1][0] - sum[i][0]) {
                sum2 = sum[0][1] - sum[i][1] + sum[n - 1][0] - sum[i][0];
            }
        }
        long sum3 = 0L;
        // 꿀 벌 벌
        for(int i=1; i<n-1; i++){
            if(sum3< sum[i][0] + sum[n-1][0] - ary[i]){
                sum3 = sum[i][0] + sum[n-1][0] - ary[i];
            }
        }
        return Math.max(sum1, Math.max(sum2,sum3));
    }
}