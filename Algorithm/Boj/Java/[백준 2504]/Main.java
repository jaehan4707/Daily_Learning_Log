import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static String str;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        str = br.readLine();
    }

    static int solution() {
        Stack<Character> s = new Stack<>(); // 선입 후출
        int answer = 0;
        int number = 1;
        for (int i = 0; i < str.length(); i++) {
            char now = str.charAt(i);
            if (now == '(') {
                number *= 2;
                s.push('(');
            }
            if (now == '[') {
                number *= 3;
                s.push('[');
            }
            if (now == ')') {
                if (s.isEmpty() || s.peek() != '(') { // 올바르지 않는 괄호열
                    return 0;
                } else {
                    if (str.charAt(i - 1) == '(') {
                        answer += number;
                    }
                    s.pop(); // ) 꺼내기
                    number /= 2;
                }
            }

            if (now == ']') {
                if (s.isEmpty() || s.peek() != '[') { // 올바르지 않는 괄호열
                    return 0;
                } else {
                    if (str.charAt(i - 1) == '[') {
                        answer += number;
                    }
                    s.pop();
                    number /= 3;
                }
            }
        }
        if (!s.isEmpty()) {
            return 0;
        }
        return answer;
    }
}