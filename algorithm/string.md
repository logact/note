# String

## [58. 最后一个单词的长度](https://leetcode-cn.com/problems/length-of-last-word/)

1. two methods

## [30. Substring with Concatenation of All Words](https://leetcode-cn.com/problems/substring-with-concatenation-of-all-words/)

1. 第一种方法超时而且由于注意力不集中造成了许多书写错误，但是复习了一下全排列（带重复字符的）明天做全排列。在这个方法中使用一个HashSet存放所有的排可能，时间复杂度为n的平方。遍历整个字符串时间复杂度为0.
2. 使用两个hashMap依然超时。
   1. 对hashMap 的操作函数：
      1. containsKey();
      2. getOrDefault();
3. 滑窗法

## 待做的项

1. 有限状态机[65. 有效数字](https://leetcode-cn.com/problems/valid-number/)编译原理



## [67. Add Binary](https://leetcode-cn.com/problems/add-binary/)

使用数组拼接字符串比StringBuffer快的多。

