package solution;

import data.structure.TreeNode;

import java.util.*;

public class BinaryTree {

    // Q1: the maximum depth of a binary tree
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return 1 + Math.max(left, right);
    }

    // Q2: the minimum depth of a binary tree
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return 1 + Math.min(left, right);
    }

    // Q3: the number of nodes of a binary tree
    public int nodeNum(TreeNode root) {
        if (root == null) return 0;
        int left = nodeNum(root.left);
        int right = nodeNum(root.right);
        return 1 + left + right;
    }

    // Q4: the number of leaf nodes of a binary
    public int leafNodeNum(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int left = leafNodeNum(root.left);
        int right = leafNodeNum(root.right);
        return left + right;
    }

    // Q5: the number of nodes at k-th level of a binary tree
    public int nodeNumAtKlevel(TreeNode root, int k) {
        if (root == null || k < 1) return 0;
        if (k == 1) return 1;
        int left = nodeNumAtKlevel(root.left, k - 1);
        int right = nodeNumAtKlevel(root.right, k - 1);
        return left + right;
    }

    // Q6: judge whether a binary tree is a balanced one
    public boolean isBalanced(TreeNode node){
        return maxDepth2(node) != -1;
    }
    private int maxDepth2(TreeNode node){
        if(node == null) return 0;
        int left = maxDepth2(node.left);
        int right = maxDepth2(node.right);
        if(left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    // Q7: judge whether a binary tree is a completed one
    boolean isCompleteTreeNode(TreeNode root){
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        boolean result = true;
        boolean hasNoChild = false;
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            if (hasNoChild) {
                if (current.left!=null || current.right != null) {
                    result = false;
                    break;
                }
            } else {
                if (current.left != null && current.right != null) {
                    queue.add(current.left);
                    queue.add(current.right);
                } else if (current.left != null && current.right == null) {
                    queue.add(current.left);
                    hasNoChild = true;
                } else if (current.left == null && current.right != null) {
                    result = false;
                    break;
                } else {
                    hasNoChild = true;
                }
            }
        }
        return result;
    }

    // Q8: 两个二叉树是否完全相同
    boolean isSameTreeNode(TreeNode t1,TreeNode t2){
        if (t1 == null && t2 == null) {
            return true;
        } else if (t1 == null || t2 == null) {
            return false;
        }
        if(t1.value != t2.value){
            return false;
        }
        boolean left = isSameTreeNode(t1.left, t2.left);
        boolean right = isSameTreeNode(t1.right, t2.right);
        return left && right;
    }

    // Q9: 两个二叉树是否互为镜像
    boolean isMirror(TreeNode t1,TreeNode t2){
        if(t1==null&&t2==null){
            return true;
        }
        if(t1==null||t2==null){
            return false;
        }
        if(t1.value != t2.value){
            return false;
        }
        return isMirror(t1.left,t2.right)&&isMirror(t1.right,t2.left);
    }

    // Q10: 翻转二叉树or镜像二叉树
    public TreeNode mirrorTreeNode(TreeNode root){
        if(root == null){
            return null;
        }
        TreeNode left = mirrorTreeNode(root.left);
        TreeNode right = mirrorTreeNode(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // Q11: 求两个二叉树的最低公共祖先节点
    TreeNode getLastCommonParent(TreeNode root,TreeNode t1,TreeNode t2){
        if(findNode(root.left,t1)){
            if(findNode(root.right,t2)){
                return root;
            }else{
                return getLastCommonParent(root.left,t1,t2);
            }
        }else{
            if(findNode(root.left,t2)){
                return root;
            }else{
                return getLastCommonParent(root.right,t1,t2);
            }
        }
    }
    // 查找节点node是否在当前 二叉树中
    public boolean findNode(TreeNode root,TreeNode node){
        if(root == null || node == null){
            return false;
        }
        if(root == node){
            return true;
        }
        boolean found = findNode(root.left,node);
        if(!found){
            found = findNode(root.right,node);
        }
        return found;
    }

    // Q12: 二叉树的前序遍历
    // 迭代求解
    public ArrayList<Integer> preOrder(TreeNode root){
        Stack<TreeNode> stack = new Stack<TreeNode>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(root == null){
            return list;
        }
        stack.push(root);
        while(!stack.empty()){
            TreeNode node = stack.pop();
            list.add((Integer)node.value);
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
        }
        return list;
    }

    // 递归求解
    ArrayList<Integer> preOrderReverse(TreeNode root){
        ArrayList<Integer> result = new ArrayList<Integer>();
        preOrder2(root,result);
        return result;
    }
    void preOrder2(TreeNode root,ArrayList<Integer> result){
        if(root == null){
            return;
        }
        result.add((Integer)root.value);
        preOrder2(root.left,result);
        preOrder2(root.right,result);
    }

    // Q13: 二叉树的中序遍历
    ArrayList<Integer> inOrder(TreeNode root){
        ArrayList<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while(current != null|| !stack.empty()){
            while(current != null){
                stack.add(current);
                current = current.left;
            }
            current = stack.peek();
            stack.pop();
            list.add((Integer) current.value);
            current = current.right;
        }
        return list;
    }

    // Q14: 二叉树的后序遍历
    ArrayList<Integer> postOrder(TreeNode root){
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(root == null){
            return list;
        }
        list.addAll(postOrder(root.left));
        list.addAll(postOrder(root.right));
        list.add((Integer) root.value);
        return list;
    }

    // Q15: 前序遍历和后序遍历构造二叉树
    TreeNode buildTreeNode(int[] preorder,int[] inorder){
        if(preorder.length!=inorder.length){
            return null;
        }
        return myBuildTree(inorder,0,inorder.length-1,preorder,0,preorder.length-1);
    }
    TreeNode myBuildTree(int[] inorder,int instart,int inend,int[] preorder,int prestart,int preend){
        if(instart>inend){
            return null;
        }
        TreeNode root = new TreeNode(preorder[prestart]);
        int position = findPosition(inorder,instart,inend,preorder[instart]);
        root.left = myBuildTree(inorder,instart,position-1,preorder,prestart+1,prestart+position-instart);
        root.right = myBuildTree(inorder,position+1,inend,preorder,position-inend+preend+1,preend);
        return root;
    }
    int findPosition(int[] arr,int start,int end,int key){
        int i;
        for(i = start;i<=end;i++){
            if(arr[i] == key){
                return i;
            }
        }
        return -1;
    }

    // Q16: 在二叉树中插入节点
    TreeNode insertNode(TreeNode root,TreeNode node){
        if(root == node){
            return node;
        }
        TreeNode tmp = new TreeNode();
        tmp = root;
        TreeNode last = null;
        while(tmp!=null){
            last = tmp;
            if((Integer)tmp.value>(Integer) node.value){
                tmp = tmp.left;
            }else{
                tmp = tmp.right;
            }
        }
        if(last!=null){
            if((Integer)last.value>(Integer) node.value){
                last.left = node;
            }else{
                last.right = node;
            }
        }
        return root;
    }

    // Q17: 输入一个二叉树和一个整数，打印出二叉树中节点值的和等于输入整数所有的路径
    void findPath(TreeNode root,int i){
        if(root == null){
            return;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int currentSum = 0;
        findPath(root, i, stack, currentSum);
    }
    void findPath(TreeNode r,int i,Stack<Integer> stack,int currentSum){
        currentSum+=(Integer)r.value;
        stack.push((Integer) r.value);
        if(r.left==null&&r.right==null){
            if(currentSum==i){
                for(int path:stack){
                    System.out.println(path);
                }
            }
        }
        if(r.left!=null){
            findPath(r.left, i, stack, currentSum);
        }
        if(r.right!=null){
            findPath(r.right, i, stack, currentSum);
        }
        stack.pop();
    }

    // Q18: 二叉树的搜索区间
    // 给定两个值 k1 和 k2（k1 < k2）和一个二叉查找树的根节点。找到树中所有值在 k1 到 k2 范围内的节点。即打印所有x (k1 <= x <= k2) 其中 x 是二叉查找树的中的节点值。返回所有升序的节点值。
    ArrayList<Integer> result;
    ArrayList<Integer> searchRange(TreeNode root,int k1,int k2){
        result = new ArrayList<Integer>();
        searchHelper(root,k1,k2);
        return result;
    }
    void searchHelper(TreeNode root,int k1,int k2){
        if(root == null){
            return;
        }
        if((Integer)root.value>k1){
            searchHelper(root.left,k1,k2);
        }
        if((Integer)root.value>=k1&&(Integer)root.value<=k2){
            result.add((Integer) root.value);
        }
        if((Integer)root.value<k2){
            searchHelper(root.right,k1,k2);
        }
    }

    // Q19: 二叉树的层次遍历
    ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            ArrayList<Integer> level = new ArrayList<Integer>();
            for(int i = 0;i < size ;i++){
                TreeNode node = queue.poll();
                level.add((Integer) node.value);
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    //Q20: 二叉树内两个节点的最长距离
    //二叉树中两个节点的最长距离可能有三种情况：
    //1.左子树的最大深度+右子树的最大深度为二叉树的最长距离
    //2.左子树中的最长距离即为二叉树的最长距离
    //3.右子树种的最长距离即为二叉树的最长距离
    //因此，递归求解即可
    private static class Result{
        int maxDistance;
        int maxDepth;
        public Result() {
        }
        public Result(int maxDistance, int maxDepth) {
            this.maxDistance = maxDistance;
            this.maxDepth = maxDepth;
        }
    }
    int getMaxDistance(TreeNode root){
        return getMaxDistanceResult(root).maxDistance;
    }
    Result getMaxDistanceResult(TreeNode root){
        if(root == null){
            Result empty = new Result(0,-1);
            return empty;
        }
        Result lmd = getMaxDistanceResult(root.left);
        Result rmd = getMaxDistanceResult(root.right);
        Result result = new Result();
        result.maxDepth = Math.max(lmd.maxDepth,rmd.maxDepth) + 1;
        result.maxDistance = Math.max(lmd.maxDepth + rmd.maxDepth,Math.max(lmd.maxDistance,rmd.maxDistance));
        return result;
    }

    // Q21: 不同的二叉树
    // 给出 n，问由 1…n 为节点组成的不同的二叉查找树有多少种？
    int numTrees(int n ){
        int[] counts = new int[n+2];
        counts[0] = 1;
        counts[1] = 1;
        for(int i = 2;i<=n;i++){
            for(int j = 0;j<i;j++){
                counts[i] += counts[j] * counts[i-j-1];
            }
        }
        return counts[n];
    }

    // Q22: 判断二叉树是否是合法的二叉查找树(BST)
    // 一棵 BST 定义为：
    // 节点的左子树中的值要严格小于该节点的值。
    // 节点的右子树中的值要严格大于该节点的值。
    // 左右子树也必须是二叉查找树。
    // 一个节点的树也是二叉查找树。
    public int lastVal = Integer.MAX_VALUE;
    public boolean firstNode = true;
    public boolean isValidBST(TreeNode root) {
        // write your code here
        if(root==null){
            return true;
        }
        if(!isValidBST(root.left)){
            return false;
        }
        if(!firstNode&&lastVal >= (Integer)root.value){
            return false;
        }
        firstNode = false;
        lastVal = (Integer)root.value;
        if (!isValidBST(root.right)) {
            return false;
        }
        return true;
    }

    // Q23: 层级打印二叉树
    public List<Integer> levelOrderTraversal(TreeNode<Integer> root) {
        int maxDepth = maxDepth(root);
        List<Integer> tree = new ArrayList<Integer>();
        for (int level = 1; level < maxDepth + 1; level++) {
            traversalKLevel(root, tree, level);
        }
        return tree;
    }
    private void traversalKLevel(TreeNode<Integer> treeNode, List<Integer> tree, int level) {
        if (treeNode == null || level < 1) {

        } else if (level == 1) {
            tree.add(treeNode.value);
        } else {
            traversalKLevel(treeNode.left, tree, level - 1);
            traversalKLevel(treeNode.right, tree, level - 1);
        }
    }
}
