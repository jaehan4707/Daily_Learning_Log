import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;

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
            sb.append(solution(str)).append("\n");
        }
    }

    public static int solution(String str){
        char [] alpha = new char[26];
        boolean checked = false;
        char before  = ' ';
        int cnt = 0;
        for(int i=0; i<str.length(); i++){
            char ch  = str.charAt(i);
            alpha[ch-'a']++;
            if(before == ch && (before != 'e' && before != 'o')){ // 연속 글자 등장
                return 0;
            }
            if(cnt==0){ // 초기 값
                checked = validate(ch); //자음 모음 결정
                before = ch; //이전 글자 입력
                cnt=1; // 카운터 증가

            } else if(checked == validate(ch)){ // 같을 때
                before = ch;
                cnt+=1;
            } else {
                before = ch;
                checked = validate(ch);
                cnt = 1;
            }

            if(cnt==3){
                return 0;
            }
        }
        if(alpha['a'-'a']==0 && alpha['e'-'a'] == 0 && alpha['o'-'a'] == 0 && alpha['i'-'a'] == 0 && alpha['u'-'a'] == 0){
            return 0;
        }
        return 1;
    }

    public static boolean validate(char ch){ //모음 판별
        if(ch=='a' || ch=='e' || ch=='o' || ch=='i' || ch=='u'){
            return true;
        }
        return false;
    }
}

