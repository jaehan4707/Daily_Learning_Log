import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int answer = 0;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        hanoi(n,1,2,3);
        System.out.println(answer);
        System.out.print(sb.toString());
    }

    public static void hanoi(int move,int start, int mid, int to){
        answer+=1;
        if (move == 1) {
            sb.append(start+" "+to).append("\n");
            return;
        }
        hanoi(move - 1, start, to, mid);
        sb.append(start+" "+to).append("\n");
        hanoi(move - 1, mid, start, to);
    }
}
