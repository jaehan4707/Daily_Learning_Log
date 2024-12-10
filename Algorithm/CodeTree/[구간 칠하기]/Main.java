import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int [] cnt;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        cnt = new int[201];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken())+100;
            int e = Integer.parseInt(st.nextToken())+100;
            for(int j=s+1; j<=e; j++){
                cnt[j]++;
            }
        }
    }

    public static int solution(){
        int answer = -1;
        for(int i=0; i<=200; i++){
            answer = Math.max(answer,cnt[i]);
        }
        return answer;
    }
}

