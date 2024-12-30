import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static String str,voca;
    static ArrayList<Character> ary;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(str);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        str = st.nextToken();
        ary = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        voca = st.nextToken();
    }

    public static void solution(){
        int idx = 0;
        while(str.contains(voca)){
            str = str.replace(voca,"");
        }
    }
}
