import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,q;
    static ArrayList<Deque<Integer>> rails;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken()); // 기차블럭 1~ n번 까지.
        m = Integer.parseInt(st.nextToken()); // 선로 개수 0~M-1까지
        q = Integer.parseInt(st.nextToken()); // 쿼리 개수
        rails = new ArrayList<>();
        for(int i=0; i<m; i++){
            int num = n/m;
            Deque<Integer> temp = new ArrayDeque<>();
            for(int j=1; j<=num; j++){
                temp.add(i*num+j);
            }
            rails.add(temp);
        }
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int opt = Integer.parseInt(st.nextToken()); // 놀이 옵션
            if(opt==3){
                int a = Integer.parseInt(st.nextToken());
                int b= Integer.parseInt(st.nextToken());
                game3(a,b);
            } else {
                int a= Integer.parseInt(st.nextToken());
                if(opt==1){
                    game1(a);
                } else {
                    game2(a);
                }
            }
        }
    }

    public static void game1(int a){ // 앞에 있는 기차블럭을 맨 뒤로 보냄
        // 앞에있는거 빼서 뒤로 옮기기
        Deque<Integer> dq = rails.get(a);
        if(dq.size()==0){
            return;
        }
        int num = dq.removeFirst();
        dq.addLast(num);
    }

    public static void game2(int a){ // 뒤에 있는 기차블럭을 맨 앞으로 보냄
        Deque<Integer> dq = rails.get(a);
        if(dq.size()==0){
            return;
        }
        int num = dq.removeLast();
        dq.addFirst(num);
    }

    public static void game3(int a, int b){ // a->b로  옮기고, b 앞으로 붙임
        Deque<Integer> aDq  = rails.get(a);
        Deque<Integer> bDq = rails.get(b);
        if(aDq.size()==0){
            return;
        }
        aDq.addAll(bDq);
        rails.get(b).clear();
        rails.get(b).addAll(aDq);
        rails.get(a).clear();
    }

    public static void solution(){
        for(int i=0; i<m; i++){
            if(rails.get(i).size()==0){
                sb.append("-1").append("\n");
                continue;
            }

            for (Integer elem : rails.get(i)) {
                sb.append(elem+" ");
            }
            sb.append("\n");
        }
    }
}

