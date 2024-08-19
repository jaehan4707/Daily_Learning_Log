import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static BufferedReader br;

    static StringTokenizer st;
    static int n;
    static String first = "";
    static String second = "";

    static StringBuffer sb;

    static int[] fir;
    static int[] sec;


    public static void main(String[] args) throws IOException {
        input();
        if (solution()) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        fir = new int[27];
        sec = new int[27];
        first = br.readLine();
        second = br.readLine();
        for (int i = 0; i < n; i++) {
            fir[first.charAt(i) - 'a'] += 1;
            sec[second.charAt(i) - 'a'] += 1;
        }
    }

    public static boolean solution() {
        String regex = "[aeiou]";
        if (first.charAt(0) != second.charAt(0) || second.charAt(n - 1) != first.charAt(n - 1)) { //첫글자 , 마지막 글자
            return false;
        }
        if (!first.replaceAll(regex, "").equals(second.replaceAll(regex, ""))) { //aeiou 뺏을때 같은지?
            return false;
        }
        //재배열했을때 같은지?
        for (int i = 0; i <= 26; i++) {
            if (fir[i] != sec[i]) {
                return false;
            }
        }
        return true;
    }
}