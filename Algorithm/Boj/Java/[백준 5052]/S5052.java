import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder sb;

    static int n, t;

    public static void main(String[] args) throws IOException {
        input();
        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        sb = new StringBuilder();
        t = Integer.parseInt(st.nextToken());
        while (t > 0) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            ArrayList<String> inputs = new ArrayList<>();
            Trie trie = new Trie();
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                String str = br.readLine();
                inputs.add(str);
                trie.insert(str);
            }
            for(int i=0; i<inputs.size();i++){
                if(trie.search(inputs.get(i))){
                    flag = false;
                    break;
                }
            }
            if(!flag){
                sb.append("NO").append("\n");
            } else{
                sb.append("YES").append("\n");
            }
            t--;
        }
    }


    static class Node {
        HashMap<Character, Node> child;
        boolean endOfWord;

        Node() {
            this.child = new HashMap<>();
            this.endOfWord = false;
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
                char c = str.charAt(i); //문자열 찾기
                now.child.putIfAbsent(c, new Node()); //없으면 추가하기
                now = now.child.get(c);
            }
            now.endOfWord = true;
        }


        public boolean search(String str) {
            Node now = this.root;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                now = now.child.get(c);
                if(now==null){
                    return false;
                }
            }
            if(now.endOfWord){
                if(now.child.isEmpty()){
                    return false;
                }
            }
            return true;
        }
    }
}