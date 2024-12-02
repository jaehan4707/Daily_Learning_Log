import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,k,answer;
    static int[] number;
    static HashSet<Integer> comb;
    static boolean[] check;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        number = new int[n];
        check = new boolean[n];
        comb = new HashSet<Integer>();

        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            number[i] = Integer.parseInt(st.nextToken());
        }

        combination(0,"");
        System.out.println(answer);
    }

    public static void combination(int depth, String num){
        if(depth == k){
            int now = Integer.parseInt(num);
            if(comb.contains(now)){
                return;
            }
            answer+=1;
            comb.add(now);
            return;
        }

        for(int i=0; i<n; i++){
            if(check[i]){
                continue;
            }
            check[i] = true;
            combination(depth+1,num+number[i]);
            check[i] = false;
        }
    }
}
