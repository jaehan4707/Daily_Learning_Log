import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int[] dx = {-1,-2,-2,-1,1,2,2,1};
    static int[] dy = {-2,-1,1,2,2,1,-1,-2};
    static int r1,c1,r2,c2;
    static boolean visit[][];

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        visit = new boolean[n+1][n+1];
        st = new StringTokenizer(br.readLine());
        r1 = Integer.parseInt(st.nextToken());
        c1 = Integer.parseInt(st.nextToken());
        r2 = Integer.parseInt(st.nextToken());
        c2 = Integer.parseInt(st.nextToken());
    }

    public static int solution(){
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(r1,c1));
        visit[r1][c1] = true;
        while(!q.isEmpty()){
            Point now = q.poll();
            if(now.x==r2 && now.y==c2){
                return now.cnt;
            }
            for(int d=0; d<8; d++){
                int mx = now.x+dx[d];
                int my = now.y+dy[d];
                if(!isInMap(mx,my) || visit[mx][my]){
                    continue;
                }
                q.add(new Point(mx,my, now.cnt+1));
                visit[mx][my]=true;
            }
        }
        return -1;
    }

    public static boolean isInMap(int x, int y){
        if(x<1 || x>n || y<1 || y>n){
            return false;
        }
        return true;
    }

    static class Point {
        int x,y,cnt;

        Point(int x, int y){
            this.x=x;
            this.y=y;
            this.cnt=0;
        }

        Point(int x, int y, int cnt){
            this.x=x;
            this.y=y;
            this.cnt=cnt;
        }
    }
}

