import java.io.*;
import java.util.*;

public class Main {

    static int n;
    static BufferedReader br;
    static StringTokenizer st;

    static int answer = 0;
    static boolean[] visit;

    static PriorityQueue<Node> pq = new PriorityQueue<>();
    static List<Node>[] list;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        input();
        prim();
        System.out.print(answer);
    }

    public static void input() throws IOException {
        n = Integer.parseInt(st.nextToken());
        visit = new boolean[n];
        List<int[]> data = new ArrayList<>();
        list = new ArrayList[n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            data.add(new int[]{i, x, y, z});
            list[i] = new ArrayList<>();
        }
        for (int i = 1; i <= 3; i++) {
            int v = i;
            data.sort((o1, o2) -> (o1[v] - o2[v]));
            for (int j = 1; j < n; j++) {
                int[] p1 = data.get(j - 1);
                int[] p2 = data.get(j);
                int dis = Math.abs(p1[i] - p2[i]);
                list[p1[0]].add(new Node(p2[0], dis));
                list[p2[0]].add(new Node(p1[0], dis));
            }
        }
    }

    public static void prim() {
        pq.add(new Node(0, 0));
        while (!pq.isEmpty()) { //pq가 빌때까지 진행
            Node now = pq.poll();
            int curPosition = now.to;
            int value = now.value;
            if (visit[curPosition])
                continue;
            visit[curPosition] = true;
            answer += value;
            for (Node next : list[curPosition]) {
                if (!visit[next.to]) {
                    pq.add(next);
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        int to;
        int value;

        public Node(int to, int value) {
            this.to = to;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }
    }
}