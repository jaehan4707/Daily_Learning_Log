import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n;

    static int[] inDegree;

    static int[] time;

    static int[] answer;

    static ArrayList<ArrayList<Integer>> graph;


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        inDegree = new int[n + 1];
        time = new int[n + 1];
        answer = new int[n + 1];
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            time[i] = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) {
                int next = Integer.parseInt(st.nextToken());
                if (next == -1) {
                    break;
                }
                inDegree[i] += 1;
                graph.get(i).add(next);
                graph.get(next).add(i);
            }
        }
    }

    public static void solution() {
        PriorityQueue<Item> pq = new PriorityQueue<>((i1, i2) -> {
            return Integer.compare(i1.value, i2.value);
        });
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                pq.add(new Item(i, time[i]));
            }
        }
        while (!pq.isEmpty()) {
            Item now = pq.poll();
            answer[now.seq] = now.value;
            for (int i = 0; i < graph.get(now.seq).size(); i++) {
                int next = graph.get(now.seq).get(i);
                inDegree[next] -= 1;
                if (inDegree[next] == 0) {
                    pq.add(new Item(next, now.value + time[next]));
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            sb.append(answer[i]).append("\n");
        }
    }

    static class Item {
        int seq, value;

        Item(int seq, int v) {
            this.seq = seq;
            this.value = v;
        }
    }
}
