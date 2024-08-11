import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static long answer, n, k;
    static char[] str;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Long.parseLong(st.nextToken());
        k = Long.parseLong(st.nextToken());
        str = br.readLine().toCharArray();
    }

    public static void solution() {
        PriorityQueue<Integer> p = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> c = new PriorityQueue<>();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'P') {
                p.add(i);
            } else {
                c.add(i);
            }
        }
        while (k != 0 && !p.isEmpty() && !c.isEmpty()) {
            int pIndex = p.peek();
            int cIndex = c.peek();
            if (pIndex > cIndex) {
                str[pIndex] = 'C';
                str[cIndex] = 'P';
                p.poll();
                c.poll();
                p.add(cIndex);
                c.add(pIndex);
                k--;
            } else {
                break;
            }
        }
        long sum = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'P') {
                sum++;
            } else { // C일 경우
                answer += (sum * (sum - 1)) / 2;
            }
        }
    }
}
