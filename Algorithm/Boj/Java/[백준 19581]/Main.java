import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int n, maxL, maxV;

    static ArrayList<ArrayList<Node>> nodes;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        input();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        nodes = new ArrayList<>();
        visit = new boolean[n + 1];
        for (int i = 0; i <= n; i++) {
            nodes.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p1 = Integer.parseInt(st.nextToken());
            int p2 = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            nodes.get(p1).add(new Node(p2, v)); // p1 -> p2(v)
            nodes.get(p2).add(new Node(p1, v)); // p2 -> p1(v)
        }
        int[] node = new int[2];
        int[] dist = new int[2];
        solution(1, 0);
        node[0] = maxV;
        visit = new boolean[n + 1];
        maxV = 0;
        maxL = 0;
        solution(node[0], 0);
        visit = new boolean[n + 1];
        node[1] = maxV;
        for (int i = 0; i < 2; i++) {
            maxL = 0;
            visit = new boolean[n + 1];
            visit[node[0]] = true;
            visit[node[1]] = true;
            solution(node[i], 0);
            dist[i] = maxL;
        }
        System.out.println(Math.max(dist[0], dist[1]));
    }

    static void solution(int now, int length) {
        if (length > maxL) {
            maxL = length;
            maxV = now;
        }
        visit[now] = true;
        for (Node node : nodes.get(now)) {
            if (visit[node.end]) {
                continue;
            }
            solution(node.end, length + node.value);
            visit[node.end] = false;
        }
    }


    static class Node {
        int end, value;

        Node(int end, int value) {
            this.end = end;
            this.value = value;
        }
    }
}