import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n, q;

    static StringBuffer sb;

    static long[] tree;

    static long[] accum;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        tree = new long[n + 1];
        accum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(tree);
        for (int i = 1; i <= n; i++) {
            accum[i] = accum[i - 1] + tree[i];
        }

        for (int i = 0; i < q; i++) {
            long value = solution(Integer.parseInt(br.readLine()));
            sb.append(value).append("\n");
        }
    }

    public static long solution(int loc) {
        long left = 1;
        long right = n-1;
        while (left <= right) {
            int mid = (int) ((left + right) / 2);
            if (tree[mid] <= loc) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        //왼쪽의 합이랑 오른족의 합 더해야 함!!!!!!
        long leftSum=0;
        long rightSum=0;
        if(tree[(int) left]  <= loc){
            leftSum = (left*loc) - accum[(int) left];
            rightSum = (accum[n]-accum[(int) left])-((n-left)*loc);
        } else{
            leftSum = ((left-1)*loc) - accum[(int) (left-1)];
            rightSum = (accum[n]-accum[(int) (left-1)]) - ((n-left+1)*loc);
        }
        return leftSum+rightSum;
    }
}
