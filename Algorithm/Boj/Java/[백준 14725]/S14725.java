import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, k;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        Trie trie = new Trie();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            String[] keywords = new String[k];
            for (int j = 0; j < k; j++) {
                String str = st.nextToken();
                keywords[j] = str;
            }
            trie.insert(keywords);
        }
        trie.print(trie.root, 0);
    }

    static class Node {
        HashMap<String, Node> child;

        Node() {
            child = new HashMap<>();
        }
    }

    static class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        public void insert(String[] str) {
            Node now = this.root;
            for (int i = 0; i < str.length; i++) {
                now.child.putIfAbsent(str[i], new Node());
                now = now.child.get(str[i]);
            }
        }

        public void print(Node root, int idx) {
            if (root == null) {
                return;
            }
            String[] keys = root.child.keySet().toArray(new String[0]);
            Arrays.sort(keys);
            for (int i = 0; i < keys.length; i++) {
                for (int j = 0; j < idx; j++) {
                    sb.append("--");
                }
                sb.append(keys[i]).append("\n");
                print(root.child.get(keys[i]), idx + 1);
            }
        }
    }
}