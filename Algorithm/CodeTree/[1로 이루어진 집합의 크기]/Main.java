import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static String inputVoca;
    static int n,m;
    static int[][] graph;
    static int answer = -1;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static int [][] map;
    static ArrayList<Integer> count = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        int label = -1;
        input();
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(graph[i][j]==1){
                    labeling(i,j,label);
                    label--;
                }
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                int sum = 1;
                boolean [] visit = new boolean[label*-1+1];
                if(graph[i][j]==0){
                    for(int d=0; d<4; d++){
                        int x = i+dx[d];
                        int y = j+dy[d];
                        if(!isInMap(x,y) || graph[x][y]==0){
                            continue;
                        }
                        int l = (graph[x][y] * -1)-1;
                        if(visit[l]){
                            continue;
                        }
                        sum+=count.get(l);
                        visit[l] = true;
                    }
                }
                answer = Math.max(answer,sum);
            }
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n][m];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void labeling(int x, int y, int number){
        Queue<Point> q = new LinkedList<>();
        boolean[][] visit = new boolean[n][m];
        q.add(new Point(x,y));
        visit[x][y] = true;
        int cnt = 0;
        while(!q.isEmpty()){
            Point now = q.poll();
            cnt+=1;
            graph[now.x][now.y] = number;
            for(int d=0; d<4; d++){
                int mx = now.x+dx[d];
                int my = now.y+dy[d];
                if(!isInMap(mx,my)||visit[mx][my]||graph[mx][my]!=1){
                    continue;
                }
                visit[mx][my] = true;
                q.add(new Point(mx,my));
            }
        }
        count.add(cnt);
    }

    public static boolean isInMap(int x, int y){
        if(x<0 || x>=n || y<0 || y>=m){
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