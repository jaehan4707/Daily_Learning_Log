import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,answer;
    static int[] row;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        row = new int[n+1];
        solution(0);
        System.out.print(answer);
    }

    public static void solution(int cnt){
        if(cnt==n){
            answer++;
            return;
        }

        for(int i=0; i<n; i++){
            row[cnt] = i;
            if(isPossible(cnt)){
                solution(cnt+1);
            }
            row[cnt] = 0;
        }
    }

    public static boolean isPossible(int c){
        for(int i=0; i<c; i++){
            if(row[i] == row[c] || Math.abs(i-c) == Math.abs(row[c]-row[i])){
                return false;
            }
        }
        return true;
    }
}
