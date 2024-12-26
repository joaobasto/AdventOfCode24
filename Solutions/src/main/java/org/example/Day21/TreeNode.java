package org.example.Day21;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeNode {
    String value;
    List<TreeNode> children = new ArrayList<>();
    boolean full; //if set to true, node is a full node, otherwise is a subsequence node
    int level;

    public TreeNode(String value, boolean full, int level) {
        this.value = value;
        this.full = full;
        this.level = level;
    }

    public void addChild(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public long calculateSize(Map<CacheKey, Long> sizeByKey) {
        Long size = sizeByKey.get(new CacheKey(value, level));
        if (size != null && size != 0L) {
            return size;
        }
        if (full) {
            if (children.isEmpty()) {
                sizeByKey.put(new CacheKey(value, level), (long) value.length());
                return value.length();
            }
            long result = 0L;
            for (TreeNode node : children) {
                result += node.calculateSize(sizeByKey);
            }
            sizeByKey.put(new CacheKey(value, level), result);
            return result;
        } else {
            long result = Long.MAX_VALUE;
            for (TreeNode node : children) {
                long childSize = node.calculateSize(sizeByKey);
                result = Math.min(childSize, result);
            }
            sizeByKey.put(new CacheKey(value, level), result);
            return result;
        }
    }
}
