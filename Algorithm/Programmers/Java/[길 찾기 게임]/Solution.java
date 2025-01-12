import java.util.*;
class Solution {

    static int n;
    static Node root;
    static ArrayList<Integer> pre;
    static ArrayList<Integer> post;
    static Point[] points;
    public int[][] solution(int[][] nodeinfo) {
        init(nodeinfo);
        int[][] answer = new int[2][n];
        preOrder(root);
        postOrder(root);
        for(int i=0; i<n; i++){
            answer[0][i] = pre.get(i);
            answer[1][i] = post.get(i);
        }
        return answer;
    }

    public void init(int [][] nodeInfo){
        n = nodeInfo.length;
        points = new Point[n];
        pre = new ArrayList<Integer>();
        post = new ArrayList<Integer>();
        for(int i=0; i<n; i++){
            points[i] = new Point(nodeInfo[i][0], nodeInfo[i][1], i+1);
        }
        Arrays.sort(points, (p1,p2)->{
            if(p1.y==p2.y){ // y좌표가 같을 때,
                return p2.x-p1.x;
            }
            return p2.y-p1.y;
        });

        root = new Node(points[0]);
        for(int i=1; i<n; i++){
            makeTree(root, new Node(points[i]));
        }
    }

    public void makeTree(Node parent, Node child){
        if(parent.p.x > child.p.x){
            if(parent.left == null){
                parent.left = child;
            } else {
                makeTree(parent.left, child);
            }
        } else {
            if(parent.right==null){
                parent.right=child;
            } else {
                makeTree(parent.right,child);
            }
        }
    }
    public void preOrder(Node node){
        // 부모 -> 왼쪽 -> 오른쪽
        pre.add(node.p.idx);
        if(node.left!=null){
            preOrder(node.left);
        }
        if(node.right!=null){
            preOrder(node.right);
        }
    }

    public void postOrder(Node node){
        // 왼쪽 오른쪽 부모
        if(node.left!=null){
            postOrder(node.left);
        }
        if(node.right!=null){
            postOrder(node.right);
        }
        post.add(node.p.idx);
    }

    static class Point {
        int x, y,idx;
        Point(int x, int y, int idx){
            this.x=x;
            this.y=y;
            this.idx=idx;
        }
    }

    static class Node {
        Point p;
        Node left, right;

        Node(Point p){
            this.p=p;
        }

        Node(Point p, Node left, Node right){
            this.p=p;
            this.left=left;
            this.right=right;
        }
    }
}
