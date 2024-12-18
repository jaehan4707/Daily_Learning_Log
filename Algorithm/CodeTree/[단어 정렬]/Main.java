import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static PriorityQueue<Word> words;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        words = new PriorityQueue<Word>((w1,w2)->{
            if(w1.len==w2.len){
                int minLen = Math.min(w1.len,w2.len);
                for(int i=0; i<minLen; i++){
                    if(w1.str.charAt(i)!=w2.str.charAt(i)){
                        return Character.compare(w1.str.charAt(i),w2.str.charAt(i));
                    }
                }
            }
            return Integer.compare(w1.str.length(), w2.str.length());

        });
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            words.add(new Word(st.nextToken()));
        }
    }

    public static void solution(){
        while(!words.isEmpty()){
            sb.append(words.poll().str).append("\n");
        }
    }

    static class Word {
        String str;
        int len;

        Word(String str){
            this.str=str;
            this.len = str.length();
        }
    }
}

