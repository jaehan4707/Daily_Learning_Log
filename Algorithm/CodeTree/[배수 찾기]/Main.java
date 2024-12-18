import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static long n;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Long.parseLong(st.nextToken());
    }

    public static long solution(){
        long l=1, r = n*5;
        while(l <= r){
            long mid = (l+r)/2;
            if(validate(mid)<n){ //가능할 경우
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    public static long validate(long num){
        return (num/3) + (num/5) - (num/15);
    }
}

