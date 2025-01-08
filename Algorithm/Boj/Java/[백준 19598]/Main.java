import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int n;

    static Meeting[] meetings;

    public static void main(String[] args) throws IOException {
        input();
        solution();
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            meetings[i] = new Meeting(x, y);
        }
        Arrays.sort(meetings, (m1, m2) -> {
            if (m1.x == m2.x) {
                return m1.y - m2.y;
            }
            return m1.x - m2.x;
        });
    }

    public static void solution() {
        PriorityQueue<Meeting> pq = new PriorityQueue<>((m1, m2) -> {
            return m1.y - m2.y;
        });
        pq.add(meetings[0]);
        for (int i = 1; i < n; i++) {
            if (pq.peek().y > meetings[i].x) { //현재 회의가 더 늦게 끝나는 경우 회의실 추가
                pq.add(meetings[i]); //회의실 추가
            } else {
                pq.poll();
                pq.add(meetings[i]);
            }
        }
        System.out.print(pq.size());
    }

    static class Meeting {
        int x, y;

        Meeting(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
