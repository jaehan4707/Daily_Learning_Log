import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m;
    static int[] inDegree;
    static ArrayList<ArrayList<Integer>> graph;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        inDegree = new int[n+1];
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            inDegree[b]++;
            graph.get(a).add(b);
            // a > b 그냥 위상 정렬 문제임.
            // 내림차순으로 놓여져 있음.
        }

        Stack<Integer> s = new Stack<>();
        for(int i=1; i<=n; i++){
            if(inDegree[i]==0){
                s.add(i);
            }
        }

        while(!s.isEmpty()){
            int now = s.pop();
            sb.append(now+" ");
            for(int i=0; i<graph.get(now).size(); i++){
                int next = graph.get(now).get(i);
                inDegree[next]--;
                if(inDegree[next]==0){
                    s.add(next);
                }
            }
        }

        System.out.print(sb.toString());
    }
}
