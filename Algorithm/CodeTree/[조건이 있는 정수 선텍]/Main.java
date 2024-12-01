import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m,answer=0;
    static boolean [][][] check;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        check = new boolean[n+1][n+1][n+1];
        long total = n*(n-1)*(n-2)/3/2;
        long cnt = 0;
        for(int j=0; j<m; j++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int min = Math.min(a,b);
            int max = Math.max(a,b);
            for(int i=1; i<=n; i++){
                if(i==a || i==b){
                    continue;
                }
                int center = i;
                if(center<min){
                    check[i][min][max] = true;
                } else if(center>max){
                    check[min][max][i] = true;
                } else {
                    check[min][center][max] = true;
                }
            }
        }
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                for(int k=1; k<=n; k++){
                    if(check[i][j][k]){
                        cnt+=1;
                    }
                }
            }
        }
        System.out.println(total-cnt);
    }


    static class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }
}