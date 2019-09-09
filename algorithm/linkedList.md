# LinkedList （do them again）

## 1.SortedListToBST_109

三种方法（问题本质就是在一堆有序数中构建出一个平衡树）

1. 使用快慢指针
2. 将链表转换为数组来操作
3. <u>使用中序遍历模拟（待完成）</u>

## 2.SortedArrayToBST_108

## 3.ReOrderList_143

1. 在同一个链表中操作时一定要注意插入时另一个节点的断开，可以使用滞后指针的方法。
2. 要注意奇数，偶数的情况。（最好的区分例如在本题区分出来的要素就是，指针是跳跃前进的）
3. 做指针的题一定要构思号各个指针变量是怎么前进的。

1. 方法一：模拟这个过程（如上述的123等步骤）
2. <u>方法二；三步逆转(待完成)</u>（关于逆转问题的较为通用的解法）

## [147. Insertion Sort List](https://leetcode-cn.com/problems/insertion-sort-list/)

1. 链表题，节点间的关系一定要想清，这个题目没有看到链表转换后的变化


## [234. Palindrome Linked List](https://leetcode-cn.com/problems/palindrome-linked-list/)

1. 可以将链表转化为数组的方法后将对数组进行操作（下标操作）
2. 逆转链表的方式。

## [237. Delete Node in a Linked List](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/)

1. 可以用快慢节点来判断整个节点的数量是奇数还是偶数（fast为null是为奇数）
2. 链表逆转复习。

## [328. Odd Even Linked List](https://leetcode-cn.com/problems/odd-even-linked-list/)

1. 时刻考虑对node.next.next这样的表达式考察是否为零

## [445. Add Two Numbers II](https://leetcode-cn.com/problems/add-two-numbers-ii/)

1. 方法一 ：链表翻转加链表相加。

2. 方法二：不改变链表的结构。

3. ```java
   * 三种方法：* 1.逆转链表后相加* 2.用栈将在不改变链表的顺序下逆转* 前面两种方法的基本目的都是为了要得到链表的逆转顺序。* 3.用递归。
   ```

4. 一定集中注意力不然写的和想的不一样







## [725. 分隔链表](https://leetcode-cn.com/problems/split-linked-list-in-parts/)

中等难度

分组问题除数余数（这点没考虑好）

```java
class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        int len=0;
        ListNode[] res=new ListNode[k];
        for(ListNode p=root;p!=null;p=p.next){
            len++;
        }
        int base=len/k;
        int extra=len%k;
        ListNode p=root;
        for(int i=0;i<k;i++){
            int sum=0;
            if(extra>0){
                sum =base +1;
                extra--;
            }else{
                sum =base;
            }
            ListNode aRes=p;
            sum--;
            while(p!=null&&sum>0){
                p=p.next;
                sum--;
            }
            if(p!=null){
                ListNode temp=p.next;
                p.next =null;
                p=temp;
            }
            res[i]=aRes;
        }
        return res;
    }
}

```

## [61. 旋转链表](https://leetcode-cn.com/problems/rotate-list/)

链表中倒数第k个节点

## [430. 扁平化多级双向链表](https://leetcode-cn.com/problems/flatten-a-multilevel-doubly-linked-list/)

看上去有点像二叉树，也有点像图的深度优先搜索。

使用一个显示栈来保存遍历中的节点（如这种递归或者用栈的题应该先要想清程序运行过程）要想到什么变量是符合栈的行为的。

方法二递归解法（这个题的递归解法很难理解更适合用显示的stack）

 运行时间很慢有其它的解法吗?

```java
class Solution {
    public Node flatten(Node head) {
        Stack<Node> stack=new Stack<>();
        Node p=head;
        Node tail=null;
        while(!stack.isEmpty()||p!=null){
            if(p==null)p=stack.pop();
            if(tail==null){
                tail=p;
            }
            else{
                 tail.next=p;
                 p.prev=tail;
                 tail=p;
            }
            if(p.next!=null)stack.push(p.next);
            Node child=p.child;
            p.child=null;
            p=child;//应该将所有的孩子节点置为空
            
        }
        return head;
        
    }
}
```

递归的解法

```java
class Solution {
    public Node flatten(Node head) {
        if (head == null) {
            return null;
        }
        dfs(head);
        head.prev = null;
        return head;
    }

    /**
     * 展开链表，展开后链表的头结点的 prev 指向尾结点
     *
     * @param head 头结点
     * @return 展开后链表的头结点
     */
    private Node dfs(Node head) {
        Node cur = head;
        while (cur != null) {
            head.prev = cur;    // 更改头结点的 prev 指向尾结点
            Node next = cur.next;
            if (cur.child != null) {
                Node h = dfs(cur.child);
                cur.child = null;
                Node t = h.prev;//这个t就是返回节点的末尾节点
                // 链接 cur、h、t、next
                cur.next = h;
                h.prev = cur;
                t.next = next;
                if (next != null) {
                    next.prev = t;
                }
                head.prev = t;  // 更改头结点的 prev 指向尾结点
            }
            cur = next;
        }
        return head;
    }
}

```

```java
//用递归的解的思路：
首先需要接受到以子节点（child）为头节点，处理后的头节点其次还需要这个结果的最末尾节点（依然这一层的next节点能够连接）（或得这个末尾节点就很有技巧性），让这个末尾节点指向next节点。
```

