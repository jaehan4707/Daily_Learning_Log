import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static BufferedReader br;
    static Long n, k, l;

    static StringBuffer sb;
    static StringTokenizer st;
    static ArrayList<Long> light;

    static Set<Long> visited;

    static Queue<Node> q = new LinkedList<>();


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuffer();
        input();
        search();
        br.close();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        l = Long.parseLong(st.nextToken());
        n = Long.parseLong(st.nextToken());
        k = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        visited = new HashSet<>();
        for (int i = 0; i < n; i++) { //켜져있는 가로등
            long next = Long.parseLong(st.nextToken());
            q.add(new Node(next, 0L));
            visited.add(next);
        }
    }

    public static void search() {
        while (!q.isEmpty()) {
            Node now = q.poll();
            sb.append(now.d).append("\n");
            k--;
            if (k == 0) {
                return;
            }
            long left = calculateDist(now.v - 1);
            if (!visited.contains(left) && left != -1L) {
                q.add(new Node(left, now.d + 1));
                visited.add(left);
            }
            long right = calculateDist(now.v + 1);
            if (!visited.contains(right) && right != -1L) {
                q.add(new Node(right, now.d + 1));
                visited.add(right);
            }

        }
    }

    public static Long calculateDist(long vertex) {
        if (vertex < 0) {
            return -1L;
        } else if (vertex > l) {
            return -1L;
        }
        return vertex;
    }

    public static class Node {
        Long v;
        Long d;

        public Node(long v, long d) {
            this.v = v;
            this.d = d;
        }
    }
}
