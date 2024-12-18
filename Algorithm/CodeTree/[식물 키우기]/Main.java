import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,q,t;
    static int [] plants;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        if(sb.length()==0){
            System.out.print(-1);
        } else {
            System.out.print(sb.toString());
        }
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        plants = new int[n+2];
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // a~b까지 물을 준다.
            /// 여기서 반복문을 딱 하면 안될거 같단 말이지
            plants[a]+=1;
            plants[b+1]--;
        }
    }

    public static void solution(){
        for(int i=1; i<=n; i++){
            plants[i] += plants[i-1];
            if(plants[i]==t){
                sb.append(i+" ");
            }
        }
    }
}

