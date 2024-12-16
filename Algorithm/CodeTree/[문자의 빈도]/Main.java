import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int [] alpha;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            alpha = new int[26];
            for(int j=0; j<str.length(); j++){
                int ch = str.charAt(j)-'a';
                alpha[ch]++;
            }
            sb.append(solution()).append("\n");
        }
    }

    public static char solution(){
        int cnt = 0;
        char ch = ' ';
        for(int i=0; i<26; i++){
            if(cnt<alpha[i]){
                cnt = alpha[i];
                ch = (char)(i+'a');
            } else if(cnt == alpha[i]){
                ch = '?';
                cnt = alpha[i];
            }
        }
        return ch;
    }
}

