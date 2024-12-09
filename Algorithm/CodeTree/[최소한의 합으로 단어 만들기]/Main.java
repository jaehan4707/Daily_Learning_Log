import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static String inputVoca;
    static int n;
    static Word[] words;
    static int[] alpha;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        input();
        solution(0,0);
        if(answer == Integer.MAX_VALUE){
            answer = -1;
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        alpha = new int[26];
        inputVoca = st.nextToken();
        for(int i=0; i<inputVoca.length(); i++){
            int ch = inputVoca.charAt(i)-'A';
            alpha[ch]+=1;
        }
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        words = new Word[n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int price = Integer.parseInt(st.nextToken());
            String w = st.nextToken();
            words[i] = new Word(price,w);
        }
        Arrays.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.compare(o1.price,o2.price);
            }
        });
    }

    // 주어진 문자열에 있는 문자들을 이용해 특정 단어를 만들기
    // 최소 비용으로 단어를 만들어라.
    // 하나의 단어를 여러번 사용 X

    public static boolean isPossible(){
        for(int j=0; j<26; j++){
            if(alpha[j]!=0){
                return false;
            }
        }
        return true;
    }

    public static void solution(int depth, int price){
        if(depth>n || price > answer){
            return;
        }
        if(isPossible()){ //가능할 경우.
            answer = Math.min(answer, price);
            return;
        }
        for(int i=depth; i<n; i++){
            boolean flag = false;
            int[] before = new int[26];
            for(int j=0; j<26; j++){
                before[j] = alpha[j];
                if(alpha[j]>0 && words[i].alpha[j]>0){
                    alpha[j] = Math.max(alpha[j]-words[i].alpha[j], 0);
                    flag = true;
                }
            }
            if(flag){
                solution(i+1, price+words[i].price);
                for(int k=0; k<26; k++){
                    alpha[k] = before[k];
                }
            }
        }
    }

    static class Word{
        int price;
        int [] alpha;

        Word(int price, String word){
            this.price = price;
            this.alpha = new int[26];
            for(int i=0; i<word.length(); i++){
                int ch = word.charAt(i)-'A';
                alpha[ch]+=1;
            }
        }
    }
}