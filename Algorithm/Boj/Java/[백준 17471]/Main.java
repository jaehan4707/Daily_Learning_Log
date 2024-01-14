import java.io.*;
import java.util.*;

public class Main {

    static int n;
    static BufferedReader br;
    static StringTokenizer st;

    static int[] graph;
    static boolean[] visit;

    static int myPeople = 0;
    static int otherPeople = 0;
    static int answer = Integer.MAX_VALUE;

    static ArrayList<Integer> origin = new ArrayList<>();
    static ArrayList<Integer> other = new ArrayList<>();
    static ArrayList<Integer> my = new ArrayList<>();

    static ArrayList<Integer>[] list;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        input();
    }

    public static void input() throws IOException {
        n = Integer.parseInt(st.nextToken()); // 구역 수
        graph = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        list = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = Integer.parseInt(st.nextToken());
            origin.add(i);
            list[i] = new ArrayList<Integer>();
        }
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int amount = Integer.parseInt(st.nextToken());
            for (int j = 0; j < amount; j++) {
                int area = Integer.parseInt(st.nextToken());
                list[i].add(area);
            }
        }
        for (int i = 1; i < n; i++) {
            init();
            combination(i, 0, 1);
        }
        if (answer == Integer.MAX_VALUE)
            System.out.print(-1);
        else System.out.print(answer);
    }

    public static void combination(int limit, int count, int cur) { //뽑아야할 것
        if (count == limit) { //다 뽑았다는 뜻.
            other.removeAll(my);
            if (isConnected(my) && isConnected(other)) { //조합에 대해서 연결된것인지 확인 .. 그러면 반대편도 확인해야하지않나?
                for (int m : my) {
                    myPeople += graph[m];
                }
                for (int o : other) {
                    otherPeople += graph[o];
                }
                answer = Math.min(answer, Math.abs(myPeople - otherPeople));
            }
            myPeople = 0;
            otherPeople = 0;
            init();
            return;
        }
        for (int i = cur; i <= n; i++) {
            my.add(i);
            combination(limit, count + 1, i + 1);
            my.remove(my.size() - 1);
        }
    }

    public static void init() {
        other.clear();
        for (int i = 1; i <= n; i++) {
            other.add(i);
        }
    }

    public static boolean isConnected(ArrayList<Integer> ary) {
        visit = new boolean[n + 1];
        int count = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(ary.get(0));
        while (!q.isEmpty()) {
            int now = q.poll();
            if (visit[now])
                continue;
            count++;
            visit[now] = true;
            for (int i = 0; i < list[now].size(); i++) {
                int next = list[now].get(i);
                if (ary.contains(next) && !visit[next]) { //구역에 포함되어있고, 방문안한곳이라면
                    q.add(next);
                }
            }
        }
        if (count == ary.size())
            return true;
        return false;
    }
}