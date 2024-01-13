import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static char[][] graph;
    static boolean[][] visit;
    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static BufferedReader br;
    static StringTokenizer st;
    static int area=0;
    static int elseArea=0;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        input();
        BFS();
        System.out.print(area+" "+elseArea);
    }

    public static void input() throws IOException {
        n = Integer.parseInt(st.nextToken());
        graph = new char[n][n];
        visit = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < n; j++) {
                graph[i][j] = s.charAt(j);
            }
        }
    }
    public static void BFS() {
        Queue <Point> q = new LinkedList<>();
        for(int i=0; i<n; i++){
            for(int j=0; j<n;j++){
                if(visit[i][j])
                    continue;
                area++;
                q.add(new Point(i,j,graph[i][j]));
                while(!q.isEmpty()){ //queue가 빌떄까지
                    Point current = q.poll(); //첫번째 원소 반환하고 제거
                    if(visit[current.x][current.y]) //방문했다면 다음 지역 탐험
                        continue;
                    visit[current.x][current.y]=true;
                    for(int d =0; d<4; d++) {
                        int mx = current.x+dir[d][0];
                        int my = current.y+dir[d][1]; //이동 좌표
                        if(InRange(mx,my)) //InRange(mx,my) ->  false
                            continue;
                        if(current.alpha!=graph[mx][my]) // 알파벳이 다를 경우
                            continue;
                        q.add(new Point(mx,my,graph[mx][my]));
                    }
                }
            }
        }
        visit = new boolean[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(visit[i][j])
                    continue;
                elseArea++;
                q.add(new Point(i,j,graph[i][j]));
                while (!q.isEmpty()){
                    Point current = q.poll();
                    if(visit[current.x][current.y])
                        continue;
                    visit[current.x][current.y]=true;
                    for(int d=0; d<4; d++){
                        int mx = current.x+dir[d][0];
                        int my = current.y+dir[d][1];
                        if(InRange(mx,my))
                            continue;
                        if(!CheckAlpha(graph[mx][my], current.alpha))
                            continue;
                        q.add(new Point(mx,my,graph[mx][my]));
                    }
                }
            }
        }
    }
    public static boolean InRange(int x, int y){
        return x<0 || x>=n || y<0 || y>=n;
    }
    public static boolean CheckAlpha(char origin, char other){
        if(origin == other)
            return true;
        else return origin != 'B' && other != 'B';
    }
    static class Point {
        int x;
        int y;
        char alpha;
        public Point(int x, int y, char alpha){
            this.x = x;
            this.y = y;
            this.alpha = alpha;
        }
    }
}