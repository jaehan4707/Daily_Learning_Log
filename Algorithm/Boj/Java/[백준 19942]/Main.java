import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int n, mp, mf, ms, mv;

    static StringBuilder sb;
    static Ingredient[] ingredients;
    static String result = "";
    static int price = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        input();
        solution(0, 0, 0, 0, 0, 0, "");
        if (price == Integer.MAX_VALUE) {
            System.out.print(-1);
        } else {
            System.out.print(price + "\n" + result);
        }
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        mp = Integer.parseInt(st.nextToken());
        mf = Integer.parseInt(st.nextToken());
        ms = Integer.parseInt(st.nextToken());
        mv = Integer.parseInt(st.nextToken());
        ingredients = new Ingredient[n];
        for (int i = 0; i < n; i++) {
            ingredients[i] = new Ingredient(br.readLine());
        }
    }

    static void solution(int depth, int p, int f, int s, int v, int total, String answer) {
        if (total > price) {
            return;
        }
        if (validation(p, f, s, v) && price > total) {
            result = answer;
            price = total;
            return;
        }

        for (int i = depth; i < n; i++) {
            Ingredient ingredient = ingredients[i];
            solution(i + 1, ingredient.protein + p, ingredient.fat + f, ingredient.carb + s, ingredient.vitamin + v, ingredient.price + total, answer + (i + 1) + " ");
        }
    }

    static boolean validation(int p, int f, int s, int v) {
        if (p >= mp && f >= mf && s >= ms && v >= mv) {
            return true;
        }
        return false;
    }

    static class Ingredient {
        int protein, fat, carb, vitamin, price;

        Ingredient(String input) {
            st = new StringTokenizer(input);
            this.protein = Integer.parseInt(st.nextToken());
            this.fat = Integer.parseInt(st.nextToken());
            this.carb = Integer.parseInt(st.nextToken());
            this.vitamin = Integer.parseInt(st.nextToken());
            this.price = Integer.parseInt(st.nextToken());
        }
    }
}