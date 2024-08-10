import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;
    static int answer;
    static String str;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine();
    }

    public static void solution() {
        Queue<Integer> b = new LinkedList<>();
        Queue<Integer> a = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'A') {
                a.add(i);
            } else if (str.charAt(i) == 'B') {
                b.add(i);
            } else if (str.charAt(i) == 'C') { //몇번째 B와 매칭할것인지, 앞에 B가 등장했는지
                if (b.isEmpty() || b.peek() > i) {
                    continue;
                }
                b.poll();
                answer += 1;
            }
        }
        while (!a.isEmpty() && !b.isEmpty()) {
            int now = a.poll(); // a index
            while (!b.isEmpty()) { //a보다 앞에 있는 B를 지워야함
                int value = b.peek();
                b.poll();
                if (now < value) {
                    answer += 1;
                    break;
                }
            }
        }
    }
}
