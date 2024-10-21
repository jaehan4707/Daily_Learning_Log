import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, m;

    static ArrayList<String> keywords;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        keywords = new ArrayList<>();
        Trie trie = new Trie();
        long answer = 0;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            trie.insert(str);
        }
        for (int i = 0; i < m; i++) {
            String str = br.readLine();
            if (trie.search(str)) {
                answer += 1;
            }
        }
        System.out.print(answer);
    }

    static class Node {
        HashMap<Character, Node> child;
        boolean endOfWord;

        Node() {
            child = new HashMap<>();
            endOfWord = false;
        }
    }

    static class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        public void insert(String str) {
            Node now = this.root;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                now.child.putIfAbsent(c, new Node());
                now = now.child.get(c);
            }
            now.endOfWord = true; //끝을 지정함.
        }

        public boolean search(String str) {
            Node now = this.root;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                now = now.child.get(c);
                if (now == null) { //없다는 뜻
                    return false;
                }
            }
            return true;
        }
    }
}