import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        int x = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {
            if(o1>o2){
                return 1;
            }
            return -1;
        });
        for (int i = 0; i < n; i++) {
            x = Integer.parseInt(br.readLine());
            if (x == 0) { //가장 작은 값 출력
                if (pq.isEmpty()) {
                    sb.append(0).append("\n");
                    continue;
                }
                int front = pq.poll();
                sb.append(front).append("\n");
            } else {
                pq.add(x);
            }
        }
        System.out.print(sb.toString());
    }
}