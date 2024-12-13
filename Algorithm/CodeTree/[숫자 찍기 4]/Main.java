import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;
    static int [][] graph;

    public static void main(String[] args) throws IOException{
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        graph = new int[n][n];
    }

    public static void solution(){
        int num = 1;
        for(int i=0; i<n-i; i++){
            //윗변
            for(int j=i; j<n-i;j++){
                graph[i][j] = num++;
            }
            // 오른쪽
            for(int j=i+1; j<n-i; j++){
                graph[j][n-1-i] = num++;
            }
            // 아래
            for(int j=n-i-2; j>=i; j--){
                graph[n-1-i][j] = num++;
            }
            // 왼쪽
            for(int j=n-2-i; j>=i+1; j--){
                graph[j][i] = num++;
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                sb.append(graph[i][j]+" ");
            }
            sb.append("\n");
        }
    }
}

