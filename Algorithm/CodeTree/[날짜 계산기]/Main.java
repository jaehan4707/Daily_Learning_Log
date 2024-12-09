import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static Date start,end;
    static int[] M1 = {1,3,5,7,8,10,12};
    static int[] M2 = {4,6,9,11};
    static int M3 = 2;

    public static void main(String[] args) throws IOException{
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int y1 = Integer.parseInt(st.nextToken());
        int m1 = Integer.parseInt(st.nextToken());
        int d1 = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int y2 = Integer.parseInt(st.nextToken());
        int m2 = Integer.parseInt(st.nextToken());
        int d2 = Integer.parseInt(st.nextToken());
        start = new Date(y1,m1,d1);
        end = new Date(y2,m2,d2);
    }

    public static void solution(){
        // 윤년이면 2월이 29일까지 있음. 원래는 28일까지고.
        long answer = 1;
        for(int i=start.y+1; i< end.y; i++){
            if(isTrue(i)){
                answer+=366;
            } else {
                answer+=365;
            }
        }
        answer += end.calculate(); //끝날짜 더하기
        if(start.flag){ //윤년일 경우
            answer+= 366 - start.calculate();
        } else {
            answer+= 365-start.calculate();
        }
        System.out.print(answer);

    }

    public static boolean isTrue(int y){
        if(y%4 ==0 ){
            if(y%400!=0 && y%100 == 0){
                return false;
            } else{
                return true;
            }
        }  else {
            return false;
        }
    }

    static class Date {
        int y, m, d;
        boolean flag = false;

        Date(int y, int m, int d){
            this.y=y;
            this.m=m;
            this.d=d;
            this.flag = isTrue(y);
        }

        long calculate(){
            int idx1 = 0;
            int idx2 = 0;
            long answer = 0;
            for(int i=1; i<m; i++){
                if(i==M1[idx1]){
                    idx1++;
                    answer+=31;
                } else if(i==M2[idx2]){
                    idx2++;
                    answer+=30;
                } else {
                    if(flag){
                        answer+=29;
                    } else{
                        answer+=28;
                    }
                }
            }
            answer+=d;
            return answer;
        }
    }
}