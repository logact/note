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







