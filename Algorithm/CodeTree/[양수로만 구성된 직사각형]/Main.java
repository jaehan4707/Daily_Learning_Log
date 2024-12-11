import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static int [][] graph;

    public static void main(String[] args) throws IOException{
        input();
        int answer = -1;
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                for(int k=i; k<=n; k++){
                    for(int l=j; l<=m; l++){
                        answer = Math.max(answer,solution(i,j,k,l));
                    }
                }
            }
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new int[n+1][m+1];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static int solution(int x1, int y1, int x2, int y2){
        int answer = 0;
        for(int i=x1; i<=x2; i++){
            for(int j=y1; j<=y2; j++){
                if(graph[i][j]<=0){
                    return -1;
                }
                answer+=1;
            }
        }
        return answer;
    }
}

