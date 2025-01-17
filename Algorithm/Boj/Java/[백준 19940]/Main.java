import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int t;

    static StringBuilder sb;

    static int[] order = {60, 10, -10, +1, -1};
    static Oven[] ovens;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        t = Integer.parseInt(br.readLine());
        ovens = new Oven[61];
        initMinute();
        for (int i = 0; i < t; i++) {
            int time = Integer.parseInt(br.readLine());
            int hour = time / 60;
            int minute = time % 60;
            sb.append(ovens[minute].cnt[0] + hour).append(" ");
            for (int j = 1; j <= 4; j++) {
                sb.append(ovens[minute].cnt[j] + " ");
            }
            sb.append("\n");
        }
    }

    public static void initMinute() {
        Queue<Oven> q = new LinkedList<>();
        Oven[] dp = new Oven[61]; // 각 시간에 대해 최소 버튼 클릭 상태 저장

        for (int i = 0; i <= 60; i++) {
            dp[i] = new Oven(i, new int[5]); // 초기값
            Arrays.fill(dp[i].cnt, Integer.MAX_VALUE);
        }
        q.add(new Oven());
        dp[0] = new Oven(0, new int[5]);
        while (!q.isEmpty()) {
            Oven now = q.poll();
            ovens[now.time] = now;
            for (int i = 4; i >= 0; i--) {
                int time = now.time + order[i];
                if (time < 0 || time > 60) {
                    continue;
                }
                Oven next = new Oven(time, now.cnt);
                next.clickButton(i);
                if (next.totalCount()<dp[time].totalCount()){
                    dp[time] = next;
                    q.add(next);
                }
            }
        }
    }

    static class Oven {
        int[] cnt;

        int time;

        Oven() {
            this.time = 0;
            this.cnt = new int[5];
        }

        Oven(int time, int[] cnt) {
            this.time = time;
            this.cnt = new int[5];
            for (int i = 0; i < cnt.length; i++) {
                this.cnt[i] = cnt[i];
            }
        }

        void clickButton(int idx) {
            cnt[idx] += 1;
        }

        int totalCount() {
            int count = 0;
            for (int c : this.cnt) {
                count += c;
            }
            return count;
        }
    }
}