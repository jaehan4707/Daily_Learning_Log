import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int[] numbers;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        numbers = new int[n+1];
        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++){
            numbers[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static int solution(){
        int l = 1;
        int r  = 1;
        int answer = 0;
        int [] cnt = new int[100001];
        while(l <= r && r <= n){
            if(cnt[numbers[r]]==0){
                cnt[numbers[r]]+=1;
                r+=1;
            } else {
                cnt[numbers[l]]-=1;
                l+=1;
            }
            answer = Math.max(answer,r-l);
        }
        return answer;
    }
}

