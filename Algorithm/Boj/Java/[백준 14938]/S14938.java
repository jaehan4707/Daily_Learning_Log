import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class S14938 {

    static BufferedReader br;
    static int n, m, r;
    static int[] item;
    static int[] dist;
    static int answer = 0;

    static ArrayList<ArrayList<Edge>> graph;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        input();
        br.close();
        search();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        item = new int[n];
        dist = new int[n];
        graph = new ArrayList<ArrayList<Edge>>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            item[i] = Integer.parseInt(st.nextToken());
            graph.add(new ArrayList<Edge>());
        }
        int a, b, l;
        for (int i = 0; i < r; i++) { // a->b l
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            l = Integer.parseInt(st.nextToken());
            graph.get(a - 1).add(new Edge(b - 1, l));
            graph.get(b - 1).add(new Edge(a - 1, l));
        }
    }

    public static void search() {
        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, dijkstra(i));
        }
    }

    public static int dijkstra(int start) { //출발점 start
        int sum = 0;
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        pq.add(new Edge(start, 0));
        dist[start] = 0;
        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            for (int i = 0; i < graph.get(now.vertex).size(); i++) { //갈 수 있는 경로
                Edge next = graph.get(now.vertex).get(i);
                int cost = now.weight + next.weight;
                if (cost > m && dist[next.vertex] < cost) { // 수색 불가
                    continue;
                }
                dist[next.vertex] = cost;
                pq.add(new Edge(next.vertex, cost));
            }
        }
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE)
                continue;
            sum += item[i];
        }
        return sum;
    }

    static class Edge {
        int vertex;
        int weight;

        public Edge(int v, int w) {
            this.vertex = v;
            this.weight = w;
        }
    }
}
