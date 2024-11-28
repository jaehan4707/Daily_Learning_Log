import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int [][] graph;
    static boolean [][] visit;
    static int [] dx = {1,-1,0,0};
    static int [] dy = {0,0,1,-1};
    static ArrayList<Integer> answer;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        sb = new StringBuilder();
        graph = new int[n][n];
        visit = new boolean[n][n];
        answer = new ArrayList<>();
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(visit[i][j]|| graph[i][j]!=1){
                    continue;
                }
                search(i,j);
            }
        }
        Collections.sort(answer);
        sb.append(answer.size()).append("\n");
        for(int i=0; i<answer.size(); i++){
            sb.append(answer.get(i)).append("\n");
        }
        System.out.print(sb.toString());
    }

    public static void search(int x, int y){
        visit[x][y] = true;
        Queue<Point> q = new LinkedList();
        q.add(new Point(x,y));
        int cnt = 0;
        while(!q.isEmpty()){
            Point now = q.poll();
            cnt+=1;
            for(int d=0; d<4; d++){
                int mx = now.x+dx[d];
                int my = now.y+dy[d];
                if(!isInMap(mx,my)||visit[mx][my] || graph[mx][my]!=1){
                    continue;
                }
                visit[mx][my] = true;
                q.add(new Point(mx,my));
            }
        }
        answer.add(cnt);
    }

    public static boolean isInMap(int x, int y){
        if(x<0 || x>=n || y<0 || y>=n){
            return false;
        }
        return true;
    }

    static class Point {
        int x, y;
        Point(int x, int y){
            this.x=x;
            this.y=y;
        }
    }
}
