import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n, m;

    static long answer = 0;

    static int[] ary;

    static StringBuffer sb;

    public static void main(String[] args) throws IOException {
        input();
        System.out.println(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        ary = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ary[i] = i;
        }
        int op, a, b;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            op = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            if (op == 0) { //합집합
                union(a,b);
            } else {
                int parentA = find(a);
                int parentB = find(b);
                if(parentA==parentB){
                    sb.append("YES").append("\n");
                } else{
                    sb.append("NO").append("\n");
                }
            }
        }
    }

    public static void union(int a, int b) {
        // a의 부모와 b의 부모를 합침
        int parentA = find(a); // b의 부모
        int parentB = find(b); //a의 부모
        if (parentA < parentB) { //b의 부모를 a로
            ary[parentB] = ary[parentA];
        } else if(parentA>parentB){
            ary[parentA] = ary[parentB];
        }
    }

    public static int find(int a) {
        if (ary[a] == a) {
            return a;
        } else {
            ary[a] = find(ary[a]);
            return ary[a];
        }
    }
}