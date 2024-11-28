import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m;
    static ArrayList<ArrayList<Integer>> ary;
    static int[] inDegree;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        ary = new ArrayList<>();
        inDegree = new int[n+1];
        for(int i=0; i<=n; i++){
            ary.add(new ArrayList<>());
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            inDegree[b]++;
            ary.get(a).add(b);
        }
        solution();
        System.out.println(sb.toString());
    }

    public static void solution(){
        PriorityQueue<Integer>q = new PriorityQueue<>();
        for(int i=1; i<=n; i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }
        while(!q.isEmpty()){
            int now = q.poll();
            sb.append(now+" ");
            for(int i=0; i<ary.get(now).size(); i++){
                int next = ary.get(now).get(i);
                inDegree[next]-=1;
                if(inDegree[next]==0){
                    q.add(next);
                }
            }
        }
    }
}