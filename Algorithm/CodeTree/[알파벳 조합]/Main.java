import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static String str;
    //static TreeSet<String> dict;
    static int answer = 0;
    static Set<String> set;
    static boolean[] visit = new boolean[16];
    static int[] alpha = new int[26];

    public static void main(String[] args) throws IOException{
        input();
        solution(0,"");
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        str = st.nextToken();
        for(int i=0; i<str.length(); i++){
            alpha[str.charAt(i)-'a']+=1;
        }
        set = new HashSet<String>();
    }

    public static void solution(int len, String s){ //사전순
        if(answer>=10000 || set.contains(s)){
            return;
        }

        if(len==str.length()){
            sb.append(s).append("\n");
            set.add(s);
            answer+=1;
            return;
        }
        for(int i=0; i<26; i++){
            if(alpha[i]>0){
                char ch = (char) ('a'+i);
                alpha[i]-=1;
                solution(len+1, s+ch);
                alpha[i]+=1;
            }
        }
    }
}

