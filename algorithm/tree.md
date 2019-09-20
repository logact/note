# Tree

## [98. 验证二叉搜索树](https://leetcode-cn.com/problems/validate-binary-search-tree/)

易错点：右子树是bst左子树是bst根节点大于左子树根节点小于右节点整个数不一定是bst

用中序遍历的方法验证。还有一个细节要注意的是这里的是关于ArrayList<Integer\> 与int 数组转换的过程（Integer[] 与int[]是不能像自动装箱那样的）

```java
class Solution {
  public boolean helper(TreeNode node, Integer lower, Integer upper) {
    if (node == null) return true;

    int val = node.val;
    if (lower != null && val <= lower) return false;
    if (upper != null && val >= upper) return false;

    if (! helper(node.right, val, upper)) return false;
    if (! helper(node.left, lower, val)) return false;
    return true;
  }

  public boolean isValidBST(TreeNode root) {
    return helper(root, null, null);
  }
}

```

```java
class Solution {
    int maxLeft;//用Integer比用int 要好可以用null代表没有被初始的状态。
    boolean start;
    boolean res;
    public boolean isValidBST(TreeNode root) {
        maxLeft=0;
        start=false;
        res =true;
         helper(root);
        return res;
    }
    public void helper(TreeNode root){
        if(root==null)return;
        if(!res)return;
        helper(root.left);
        if(!res)return;
        if(!start){
            start=true;
            maxLeft=root.val;
            helper(root.right);
        }else{
            if(root.val>maxLeft){
                maxLeft=root.val;
                helper(root.right);
            }else{
                res=false;
            }
        }
    }
    
    
}
```

## [501. 二叉搜索树中的众数](https://leetcode-cn.com/problems/find-mode-in-binary-search-tree/)

利用bst中序遍历是递增序列，变成一个计数问题只不过要注意在这里的计数比较麻烦容易出错。

```java
class Solution {
    int pre;
    int count;
    int record;
    public int[] findMode(TreeNode root) {
        pre=0;
        count=0;
        record=0;
        List<Integer> list =new ArrayList<>();
        helper(list,root);
        int[] res =new int [list.size()];
        for(int i=0;i<res.length;i++){
            res[i] =list.get(i);
        }
        return res;
    }
    private void helper(List<Integer> list,TreeNode root){
        
        if(root==null)return;
        helper(list,root.left);
        System.out.println("pre"+pre);
        System.out.println(root.val);
        int size =list.size();
        if(size==0){
            list.add(root.val);
            record=1;
        }else{
            if(root.val!=pre){
                count=1;
                if(count==record){
                    list.add(root.val);
                }
            }else{
                count++;
                if(root.val!=list.get(size-1)){
                    if(count==record){
                        list.add(root.val);
                    }
                }else{
                    record++;
                    if(count>=record){
                        list.clear();
                        list.add(root.val);
                    }
                }
            }
        
        }
        pre =root.val;
        helper(list,root.right);
    }
}
```

```java
class Solution {
    List<Integer> list = new ArrayList<>();
    TreeNode pre;
    int max = 0;
    int cur = 1;
    public int[] findMode(TreeNode root) {
        if(root==null) {
            return new int[] {};
        }
        inorder(root);
        int[] a = new int[list.size()];
        for(int i=0;i<list.size();i++) {
            a[i]=list.get(i);
        }
        return a;
    }
    private void inorder(TreeNode root) {
        if(root==null) {
            return ;
        }
        inorder(root.left);
        if(pre!=null) {
            if(pre.val==root.val) {
                cur++;
            }
            else {
                cur=1;
            }
        }
        if(cur==max) {
            list.add(root.val);
        }
        if(cur>max) {
            list.clear();
            list.add(root.val);
            max=cur;
        }
        pre=root;
        inorder(root.right);
    }
}

```

## [100. 相同的树](https://leetcode-cn.com/problems/same-tree/)

尝试用非递归的方法做。





## [101. 对称二叉树](https://leetcode-cn.com/problems/symmetric-tree/)

尝试用非递归的方法解。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root==null)return true;
        return helper(root.left,root.right);
    }
    public boolean helper(TreeNode left ,TreeNode right){
        if(left==null||right==null){
            return (left==null&&right==null);
        }
        return left.val==right.val&&helper(left.left,right.right)&&helper(left.right,right.left);
        
    }
}

```

## [99. 恢复二叉搜索树](https://leetcode-cn.com/problems/recover-binary-search-tree/)

同样利用bst的中序遍历是递增序列的特点：注意两个节点相邻的情况。

```java
class Solution {
    TreeNode pre =null;
    TreeNode first = null;
    TreeNode second =null;
    
    public void recoverTree(TreeNode root) {
        helper(root);
        int temp = first.val;
        first.val =second.val;
        second.val=temp;
        
    }
    private  void helper(TreeNode root){
        if(root==null)return;
        helper(root.left);
        if(pre==null)pre=root;
        else{
            if(pre.val>root.val){
                if(first==null){
                    first=pre;
                }
                second =root;
            }
            pre =root;
        }
        helper(root.right);
    }
}
```

