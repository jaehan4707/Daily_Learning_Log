import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int[][] graph;
    static int [] dx = {1,-1,0,0};
    static int [] dy = {0,0,1,-1};

    public static void main(String[] args) throws IOException{
        input();
        System.out.print(solution());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        graph = new int[100][100];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int x  = Integer.parseInt(st.nextToken());
            int y  = Integer.parseInt(st.nextToken());
            for(int j=0; j<10; j++){
                for(int k=0; k<10; k++){
                    graph[x+j][y+k] = 1;
                }
            }
        }
    }

    public static int solution(){
        int size = 0;
        for(int i=0; i<100; i++){
            for(int j=0; j<100; j++){
                if(graph[i][j]==1){
                    for(int d=0; d<4; d++){
                        int mx = i+dx[d];
                        int my = j+dy[d];
                        if(!isInMap(mx,my)){
                            size+=1;
                        } else if(graph[mx][my]==0){
                            size+=1;
                        }
                    }
                }
            }
        }
        return size;
    }

    public static boolean isInMap(int x, int y){
        if(x<0 || x>99 || y<0|| y>99){
            return false;
        }
        return true;
    }
}

