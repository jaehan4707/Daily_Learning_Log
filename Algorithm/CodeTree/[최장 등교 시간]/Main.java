import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n,m;
    static ArrayList<ArrayList<Node>> nodes;

    public static void main(String[] args) throws IOException{
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        nodes = new ArrayList<>();
        for(int i=0; i<=n; i++){
            nodes.add(new ArrayList<>());
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            nodes.get(s).add(new Node(e,l));
        }
    }

    public static void solution(){
        long answer = Integer.MIN_VALUE;
        for(int i=1; i<n; i++){ // 1 ~ n-1번까지 출발
            answer = Math.max(answer, dijkstra(i));
        }
        System.out.print(answer);
    }

    public static long dijkstra(int start){
        //
        int [] dis = new int[n+1];
        for(int i=0; i<=n; i++){
            dis[i] = 1000*1000;
        }
        dis[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<Node>( (n1,n2)->{
            return Integer.compare(n1.l,n2.l);
        });
        pq.add(new Node(start,0));
        while(!pq.isEmpty()){
            Node now = pq.poll();
            for(int i=0; i<nodes.get(now.v).size(); i++){
                Node next = nodes.get(now.v).get(i);
                int distance = next.l+now.l;
                if(dis[next.v]<distance){
                    continue;
                }
                dis[next.v] = distance;
                pq.add(new Node(next.v,distance));
            }
        }
        return dis[n];
    }

    static class Node{
        int v,l;

        Node(int v, int l){
            this.v=v;
            this.l=l;
        }
    }
}
