import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n, m, x;


    static List<ArrayList<Node>> graph;

    static int[] inDist;
    static int[] outDist;
    static StringBuffer sb;

    static PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> {
        if (n1.price > n2.price) {
            return 1;
        } else {
            return -1;
        }
    });

    static class Node {
        int vertex, price;

        Node(int vetex, int price) {
            this.price = price;
            this.vertex = vetex;
        }
    }

    public static void main(String[] args) throws IOException {
        input();
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (i == x) continue;
            inDegree(i);
        }
        outDegree();
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, inDist[i] + outDist[i]);
        }
        System.out.print(answer);
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        inDist = new int[n + 1];
        outDist = new int[n + 1];
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        int s, f, v;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            f = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            graph.get(s).add(new Node(f, v));
        }
    }

    public static void inDegree(int start) {
        pq.add(new Node(start, 0));
        boolean[] visit = new boolean[n + 1];
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (visit[now.vertex]) continue;
            visit[now.vertex] = true;
            if (now.vertex == x) {
                inDist[start] = now.price;
                pq.clear();
                return;
            }
            for (int i = 0; i < graph.get(now.vertex).size(); i++) {
                Node current = graph.get(now.vertex).get(i);
                if (visit[current.vertex]) continue;
                pq.add(new Node(current.vertex, now.price + current.price));
            }
        }
    }

    public static void outDegree() {
        boolean[] visit = new boolean[n + 1];
        Arrays.fill(outDist, Integer.MAX_VALUE);
        outDist[x] = 0;
        pq.add(new Node(x, 0));
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (visit[now.vertex]) {
                continue;
            }
            visit[now.vertex] = true;
            for (int i = 0; i < graph.get(now.vertex).size(); i++) {
                Node current = graph.get(now.vertex).get(i);
                if (visit[current.vertex]) continue;
                int nextDist = outDist[now.vertex] + current.price;
                if (outDist[current.vertex] > nextDist) {
                    outDist[current.vertex] = nextDist;
                    pq.add(new Node(current.vertex, nextDist));
                }
            }
        }
    }
}