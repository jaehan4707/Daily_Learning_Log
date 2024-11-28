import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m,k;
    static int[][] graph;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        graph = new int[n][m];

        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<k; i++){
            rotation();
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                System.out.print(graph[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void rotation(){
        int row = Math.min(n,m); //6,9
        for(int i=0; i<row-i-1; i++){ //0, 1,
            int tl = graph[i][i];
            int tr = graph[i][m-i-1]; //0,8
            int bl = graph[n-1-i][i];
            int br = graph[n-1-i][m-i-1];

            // 왼쪽
            for(int j=n-i-1; j>i; j--){
                graph[j][i] = graph[j-1][i];
            }

            //아래
            for(int j=m-1-i; j>i; j--){
                graph[n-1-i][j] = graph[n-1-i][j-1];
            }

            // 오른쪽
            for(int j=i; j<n-1-i; j++){
                graph[j][m-1-i] = graph[j+1][m-1-i];
            }
            //위쪽
            for(int j=i; j<m-1-i; j++){
                graph[i][j] = graph[i][j+1];
            }
            graph[i+1][i] = tl;
            graph[n-1-i][i+1] = bl;
            graph[n-2-i][m-1-i] = br;
            graph[i][m-2-i] = tr;
        }
    }
}