import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m;
    static int[] number;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        number = new int[n];
        m = Integer.parseInt(st.nextToken());
        // m을 넘지 않으면서 m에 가장 가깝게 정수 3개 합
        st = new StringTokenizer(br.readLine());
        // 3개의 포인터?
        for(int i=0; i<n; i++){
            number[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(number);
        System.out.print(solution());
    }

    static long solution(){
        long answer = -1;
        for(int i=n-1; i>2; i--){ // E -> D -> C
            int l = 0;
            int r = i-1; //
            long num = number[i];
            while(l<r){
                long sum = number[l] + number[r] + num;
                if(sum==m){
                    answer = sum;
                    return answer;
                } else if(sum>m){ // 넘는다면?
                    r-=1;
                } else if(sum<m){
                    answer = Math.max(answer,sum);
                    l+=1;
                }
            }
        }
        return answer;
    }
}
