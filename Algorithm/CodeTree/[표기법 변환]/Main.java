import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static String str;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        str = st.nextToken(); //중위 표기식
    }

    public static void solution(){
        // 중위 -> 후위로 바꾸기
        // 알파벳과 +,-,*,%,(,)
        // 우선순위는 통상 개념과 동일
        Stack<Character> op = new Stack<>();
        for(int i=0; i<str.length(); i++){
            char ch = str.charAt(i);
            if(isAlpha(ch)){ //알파벳일 경우
                sb.append(ch);
            } else {
                int nowRank = getOpRank(ch);
                if(ch==')'){
                    while(!op.isEmpty() && op.peek() !='('){
                        sb.append(op.pop());
                    }
                    op.pop();
                } else if(ch=='('){
                    op.add(ch);
                } else {
                    while(!op.isEmpty() && getOpRank(op.peek())<=nowRank){
                        sb.append(op.pop());
                    }
                    op.add(ch);
                }
            }
        }
        while(!op.isEmpty()){
            sb.append(op.pop());
        }
    }

    public static boolean isAlpha(char ch){
        if(ch-'A'<0){
            return false;
        }
        return true;
    }

    public static int getOpRank(char ch){
        if(ch==')' || ch=='('){
            return 4;
        } else if(ch=='*' || ch=='/'){
            return 2;
        } else {
            return 3;
        }
    }
}

