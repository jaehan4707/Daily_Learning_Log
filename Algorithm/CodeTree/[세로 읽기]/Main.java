import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static char[][] strs;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        strs = new char[4][16];
        for(int i=0; i<4; i++){
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            Arrays.fill(strs[i],'?');
            for(int j=0; j<str.length(); j++){
                strs[i][j] = str.charAt(j);
            }
        }
    }

    public static void solution(){
        for(int i=0; i<15; i++){
            for(int j=0; j<4; j++){
                if(strs[j][i]=='?'){
                    continue;
                }
                sb.append(strs[j][i]);
            }
        }
    }
}

