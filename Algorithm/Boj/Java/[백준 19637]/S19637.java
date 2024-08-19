import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n, m;
    static long[] numbers;

    static List<Item> items;
    static StringBuffer sb;


    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuffer();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        items = new ArrayList<>();
        numbers = new long[m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String title = st.nextToken();
            long score = Long.parseLong(st.nextToken());
            items.add(new Item(title, i, score));
        }
        items.sort(Item::compareTo);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = solution(Long.parseLong(st.nextToken()));
            sb.append(items.get(idx).title).append("\n");
        }
    }

    public static int solution(long score) {
        int left = 0, right = items.size() - 1, mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (items.get(mid).score < score) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    static class Item implements Comparable<Item> {
        String title;
        int idx;
        long score;

        public Item(String title, int idx, long score) {
            this.title = title;
            this.idx = idx;
            this.score = score;
        }

        @Override
        public int compareTo(Item o) {
            if (o.score != this.score) {
                return Long.compare(this.score, o.score);
            }
            return Integer.compare(this.idx, o.idx);
        }
    }
}