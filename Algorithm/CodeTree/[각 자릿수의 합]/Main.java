import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,loc,answer = Integer.MAX_VALUE;
    static int [] number;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());

        // 자연수 m 찾기
        // m의 각 자릿수와, m 자기 자신을 더하면 n
        // m1, m2, m3 , .. mn + m = n
        loc = String.valueOf(n).length(); //자리 수
        number = new int[loc];
        dfs(0,0);
        if(answer==Integer.MAX_VALUE){
            answer=0;
        }
        System.out.println(answer);
    }

    public static void dfs(int total, int num){
        if(answer<num){ //이미 찾은 수보다 num이 더 크다면 탐색할 의미가 없음
            return;
        }
        if(total+num==n){ //합이 n일 경우 answer를 최소값으로 업데이트 함.
            answer = Math.min(answer,num);
            return;
        } else if(total+num>n){
            return;
        }
        for(int i=0; i<=9; i++){
            if(num==0 && i==0){ //앞에 0이 오면 안됨.
                continue;
            }
            dfs(total+i,num*10+i);
        }
    }
}
