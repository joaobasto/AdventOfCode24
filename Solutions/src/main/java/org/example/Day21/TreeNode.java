package org.example.Day21;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    String value;
    List<TreeNode> children = new ArrayList<>();
    boolean full; //if set to true, node is a full node, otherwise is a subsequence node

    public TreeNode(String value, boolean full) {
        this.value = value;
        this.full = full;
    }

    public void addChild(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public long calculateSize() {
        if (full) {
            if (children.isEmpty()) {
                return value.length();
            }
            long result = 0L;
            for (TreeNode node : children) {
                result += node.calculateSize();
            }
            return result;
        } else {
            long result = Long.MAX_VALUE;
            for (TreeNode node : children) {
                long childSize = node.calculateSize();
                result = Math.min(childSize, result);
            }
            return result;
        }
    }
}
