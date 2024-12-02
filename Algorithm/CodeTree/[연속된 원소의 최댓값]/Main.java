import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;
    static int[] number;
    static int[] dp;

    public static void main(String[] args) throws IOException{
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        number = new int[n+1];
        dp = new int[n+1];
        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++){
            number[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void solution(){
        long answer = -1001;
        dp[0] = -1001;
        for(int i=1; i<=n; i++){
            dp[i] = Math.max(number[i],dp[i-1]+number[i]);
            answer = Math.max(dp[i],answer);
        }
        System.out.print(answer);
    }
}
