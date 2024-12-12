import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,q;
    static int[] fish;
    static int [] sum;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        sum = new int[n+1];
        fish = new int[n+1];
        for(int i=1; i<=n; i++){
            fish[i] = Integer.parseInt(st.nextToken());
            sum[i] = sum[i-1] + fish[i]; // 1 3 6 10
        }
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // a~b 물고기 합
            sb.append(sum[b] - sum[a-1]).append("\n");
        }
    }
}

