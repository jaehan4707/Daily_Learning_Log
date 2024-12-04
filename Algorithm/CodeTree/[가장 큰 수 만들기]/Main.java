import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,k;
    static int[] number;
    static long answer;

    public static void main(String[] args) throws IOException{
        input();
        solution(0);
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        number = new int[k];
        for(int i=0; i<k; i++){
            number[i] = Integer.parseInt(st.nextToken());
        }
    }

    public static void solution(long num){
        if(num>n){
            return;
        }
        answer = Math.max(answer,num);
        for(int i=0; i<k; i++){
            solution(num*10+number[i]);
        }
    }
}