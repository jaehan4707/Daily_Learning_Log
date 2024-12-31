import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,k;
    static int[] coin;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        coin = new int[n];
        // n개의 동전으로 k를 만들어야 함.
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            coin[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static long solution(){
        // 큰 동전부터 끼울까?
        long cnt = 0;
        for(int i=n-1; i>=0; i--){
            int res = k/coin[i];
            if(res==0){
                continue;
            }
            k-= res*coin[i];
            cnt+=res;
        }
        return cnt;
    }
}

