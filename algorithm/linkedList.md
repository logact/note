# LinkedList

1.SortedListToBST_109

三种方法（问题本质就是在一堆有序数中构建出一个平衡树）

1. 使用快慢指针
2. 将链表转换为数组来操作
3. <u>使用中序遍历模拟（待完成）</u>

2.SortedArrayToBST_108

3.ReOrderList_143

1. 在同一个链表中操作时一定要注意插入时另一个节点的断开，可以使用滞后指针的方法。
2. 要注意奇数，偶数的情况。（最好的区分例如在本题区分出来的要素就是，指针是跳跃前进的）
3. 做指针的题一定要构思号各个指针变量是怎么前进的。

[147. Insertion Sort List](https://leetcode-cn.com/problems/insertion-sort-list/)

1. 方法一：模拟这个过程
2. <u>方法二；三步逆转(待完成)</u>（关于逆转问题的较为通用的解法）

#### [147. Insertion Sort List](https://leetcode-cn.com/problems/insertion-sort-list/)

1. 链表题，节点间的关系一定要想清，这个题目没有看到链表转换后的变化
2. 