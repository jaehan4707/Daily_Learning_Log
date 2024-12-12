import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int [][] graph;
    static int [] dx = {1,-1,0,0};
    static int [] dy = {0,0,1,-1};
    static boolean [][] pin;
    static int answer = 0;

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        graph = new int[1001][1001];
        pin = new boolean[1001][1001];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            answer = solution(x,y);
            sb.append(answer).append("\n");
        }
    }

    public static int solution(int x, int y){
        int cnt = answer;
        pin[x][y] = true;
        for(int d=0; d<4; d++){
            int mx = x+dx[d];
            int my = y+dy[d];
            if(!isInMap(mx,my)){
                continue;
            }
            graph[mx][my]+=1;
            if(!pin[mx][my]){
                continue;
            }
            if(graph[mx][my]==3){
                cnt+=1;
            } else if(graph[mx][my]==4) {
                cnt-=1;
            }
        }

        return cnt;
    }

    static boolean isInMap(int x, int y){
        if(x<0 || x>1000 || y<0 || y>1000){
            return false;
        }
        return true;
    }
}
