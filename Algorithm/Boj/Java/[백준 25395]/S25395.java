import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, s;

    static Car[] cars;
    static boolean[] visit;

    static int INF = 1000000000;

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
        s = Integer.parseInt(st.nextToken());
        cars = new Car[n];
        visit = new boolean[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(i, Integer.parseInt(st.nextToken()));
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cars[i].gas = Integer.parseInt(st.nextToken());
        }
    }

    public static void solution() {
        Queue<Car> q = new LinkedList<>();
        q.add(cars[s - 1]);
        visit[s - 1] = true;
        while (!q.isEmpty()) {
            Car now = q.poll();
            for (int j = now.idx - 1; j >= 0; j--) {
                Car temp = cars[j];
                if (temp.pos < now.pos - now.gas) { //도달 못한다면
                    break;
                }
                if (visit[temp.idx]) {
                    continue;
                }
                q.add(temp);
                visit[temp.idx] = true;
            }

            for (int j = now.idx + 1; j < n; j++) {
                Car temp = cars[j];
                if (temp.pos > now.pos + now.gas) {
                    break;
                }
                if (visit[temp.idx]) {
                    continue;
                }
                q.add(temp);
                visit[temp.idx] = true;
            }
        }
        for (int i = 0; i <= n; i++) {
            if (visit[i]) {
                sb.append(i + 1).append(" ");
            }
        }
    }

    static class Car {
        int idx, pos, gas;

        Car(int idx, int pos) {
            this.idx = idx;
            this.pos = pos;
        }

        Car(int idx, int pos, int gas) {
            this.idx = idx;
            this.pos = pos;
            this.gas = gas;
        }
    }
}