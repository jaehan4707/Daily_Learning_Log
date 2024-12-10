import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
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
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        numbers = new int[n];
        for(int i=0; i<n; i++){
            numbers[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static int solution(){
        int l = -1,r=-1,sum=0,answer=0;
        while(l<=r&&r<=numbers.length){
            if(sum<m){
                r+=1;
                if(r==numbers.length){
                    break;
                }
                sum += numbers[r];
            } else {
                l+=1;
                if(sum==m){
                    //  System.out.println(l+" "+r);
                    answer+=1;
                }
                sum -= numbers[l];
            }
        }
        return answer;
    }
}

