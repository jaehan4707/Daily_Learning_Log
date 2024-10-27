import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m;

    static ArrayList<ArrayList<Integer>> graph;

    static int[] inDegree;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        graph = new ArrayList<>();
        inDegree = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int tc = Integer.parseInt(st.nextToken());
            int temp = Integer.parseInt(st.nextToken());
            for (int j = 1; j < tc; j++) {
                int now = Integer.parseInt(st.nextToken());
                inDegree[now]++;
                graph.get(temp).add(now);
                temp = now;
            }
        }
    }

    public static void solution() {
        Stack<Integer> s = new Stack<>();
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) { //가장 먼저 출발점들을 넣어줌
                s.add(i);
            }
        }
        while (!s.isEmpty()) {
            int now = s.pop();
            cnt += 1;
            sb.append(now).append("\n");
            for (int i = 0; i < graph.get(now).size(); i++) {
                int cur = graph.get(now).get(i);
                inDegree[cur]--;
                if (inDegree[cur] == 0) {
                    s.push(cur);
                }
            }
        }
        if(cnt != n){
            sb = new StringBuilder().append(0);
        }
    }
}
