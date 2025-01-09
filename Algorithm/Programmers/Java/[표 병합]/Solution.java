import java.io.*;
import java.util.*;

class Solution {

    static String[] graph;
    static int[] parent;
    static ArrayList<String> answer;

    public String[] solution(String[] commands) {
        answer = new ArrayList<>();
        graph = new String[2500]; // 최대 2500개
        parent = new int[2500];
        run(commands);
        String[] result = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    static void run(String[] commands) {
        for (int i = 0; i < 2500; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < commands.length; i++) {
            String[] command = commands[i].split(" ");
            String opt = command[0];
            if (opt.equals("UPDATE")) {
                if (command.length == 4) {
                    update(command[1], command[2], command[3]);
                } else {
                    update(command[1], command[2]);
                }
            } else if (opt.equals("MERGE")) {
                merge(command);
            } else if (opt.equals("UNMERGE")) {
                unmerge(command);
            } else {
                answer.add(print(command));
            }
        }
    }

    static void update(String r, String c, String value) {
        int r1 = Integer.parseInt(r) - 1;
        int c1 = Integer.parseInt(c) - 1;
        graph[find(r1 * 50 + c1)] = value;
    }

    static void update(String v1, String v2) {
        for (int i = 0; i < 2500; i++) {
            int parentIdx = find(i);
            if (graph[parentIdx] != null && graph[parentIdx].equals(v1)) {
                graph[parentIdx] = v2;
            }
        }
    }

    static void merge(String[] commands) {
        int r1 = parseInt(commands[1]) - 1;
        int c1 = parseInt(commands[2]) - 1;
        int r2 = parseInt(commands[3]) - 1;
        int c2 = parseInt(commands[4]) - 1;
        int num1 = r1 * 50 + c1;
        int num2 = r2 * 50 + c2;
        if (graph[find(num1)] == null && graph[find(num2)] != null) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        union(num1, num2);
    }

    static void unmerge(String[] commands) {
        int r = parseInt(commands[1]) - 1;
        int c = parseInt(commands[2]) - 1;
        int p = find(r * 50 + c);
        String value = graph[p];
        for (int i = 0; i < 2500; i++) {
            find(i);
        }
        for (int i = 0; i < 2500; i++) {
            if (find(i) == p) {
                parent[i] = i;
                if (i == r * 50 + c) {
                    graph[i] = value;
                } else {
                    graph[i] = null;
                }
            }
        }
    }

    static String print(String[] commands) {
        int r = parseInt(commands[1]) - 1;
        int c = parseInt(commands[2]) - 1;
        int idx = r * 50 + c;
        if (graph[find(idx)] == null) {
            return "EMPTY";
        }
        return graph[find(idx)];
    }

    static int find(int idx) {
        if (parent[idx] == idx) {
            return idx;
        }
        return parent[idx] = find(parent[idx]);
    }

    static void union(int idx1, int idx2) {
        idx1 = find(idx1);
        idx2 = find(idx2);
        if (idx1 == idx2) {
            return;
        }
        parent[idx2] = idx1;
        graph[idx2] = null;
    }

    static int parseInt(String str) {
        return Integer.parseInt(str);
    }
}
