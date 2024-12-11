import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n,m,k;
    static int [] ary;
    static int [][] dist;
    static ArrayList<ArrayList<Pair>> graph;

    public static void main(String[] args) throws IOException{
        input();
        int answer = 0;
        int vertex = 1;
        for(int i=1; i<=n; i++){
            solution(i);
        }
        for(int i=0; i<k; i++){
            answer += dist[vertex][ary[i]];//solution(vertex, ary[i]);
            vertex = ary[i];
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        ary = new int[k]; //표지판
        graph = new ArrayList<>();
        dist = new int[n+1][n+1];
        for(int i=0; i<=n; i++){
            graph.add(new ArrayList<>());
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<k; i++){
            ary[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d =  Integer.parseInt(st.nextToken());
            graph.get(a).add(new Pair(b,d));
            graph.get(b).add(new Pair(a,d));
        }
    }

    public static void solution(int vertex){
        // 반드시 표지판을 지나야 함
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((p1,p2)->{
            return Integer.compare(p1.p,p2.p);
        });
        pq.add(new Pair(vertex,0));
        dist[vertex][vertex] = 0;
        while(!pq.isEmpty()){
            Pair now = pq.poll();
            for(int i=0; i<graph.get(now.v).size(); i++){
                Pair next = graph.get(now.v).get(i);
                int nextDist = dist[vertex][now.v]+next.p;
                if(nextDist <= dist[vertex][next.v]){ //이동하는것이 거리가 더 작다면
                    dist[vertex][next.v] = nextDist;
                    pq.add(new Pair(next.v, nextDist));
                }
            }
        }
    }

    static class Pair {
        int v,p;

        Pair(int v, int p){
            this.v=v;
            this.p=p;
        }
    }
}

