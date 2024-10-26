import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n;

    static Trie dir = new Trie();

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            String[] inputs = br.readLine().split("\\\\");
            dir.insert(inputs);
        }
        dir.print(dir.root, 0);
    }


    public static class Node {
        HashMap<String, Node> child;
        boolean endOfWord;

        Node() {
            child = new HashMap<>();
            endOfWord = false;
        }
    }

    public static class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        public void insert(String[] inputs) {
            Node now = this.root;
            for (String input : inputs) {
                now.child.putIfAbsent(input, new Node());
                now = now.child.get(input);
            }
            now.endOfWord = true;
        }

        public void print(Node now, int idx) {
            if (now.child.isEmpty()) {
                return;
            }
            String[] keys = now.child.keySet().toArray(new String[0]);
            Arrays.sort(keys);
            for (String key : keys) {
                for (int j = 0; j < idx; j++) {
                    sb.append(" ");
                }
                sb.append(key).append("\n");
                print(now.child.get(key), idx + 1);
            }
        }
    }
}