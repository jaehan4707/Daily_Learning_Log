import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int answer = 0;
        Stack<Integer> s = new Stack<>();

        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            while(!s.isEmpty() && s.peek() > y){
                answer++;
                s.pop();
            }

            if(!s.isEmpty() && s.peek() == y){
                continue;
            }
            s.add(y);
        }

        while(!s.isEmpty()){
            if(s.peek() >0){
                answer++;
            }
            s.pop();
        }
        System.out.print(answer);
    }
}
