package top.huajieyu001.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author huajieyu
 * @Date 2026/3/23 22:56
 * @Version 1.0
 * @Description TODO
 */
public class AnalysisNode {

    public static void main(String[] args) {
        AnalysisNode instance = new AnalysisNode();
        instance.test();
    }

    public void test(){
        Node root = new Node(1);
        Node left = new Node(2);
        Node right = new Node(3);
        Node left1 = new Node(4);
        Node left2 = new Node(5);
        Node left3 = new Node(6);
        Node right1 = new Node(7);

        root.children = new Node[]{left, right};
        left.children = new Node[]{left1, left2, left3};
        right.children = new Node[]{right1};

        int[] array = getArray(root);
        System.out.println(Arrays.toString(array));
    }

    public int [] getArray(Node root){
        List<Integer> list = new ArrayList<>();
        resolve(root, list);
        return list.stream().mapToInt(i->i).toArray();
    }

    public void resolve(Node node, List<Integer> list){
        if(node == null){
            return;
        }
        if(list.isEmpty()){
            list.add(node.value);
        }
        if(node.children == null){
            return;
        }
        for(Node child : node.children){
            list.add(child.value);
        }
        for(Node child : node.children){
            resolve(child, list);
        }
    }
}
