# 复习全排列问题

## [46. Permutations](https://leetcode-cn.com/problems/permutations/)

1.最优解的关键，全排列就是在数组中将数组的位置做所有的交换所以可以用递归交换数组的到所有的排列

1. 两种方法
   1. 用一个hash表来存储已经访问过 的下标。
   2. 用交换的方法得出所有的排列就不用重复的查看已有的排列

## [47. Permutations II](https://leetcode-cn.com/problems/permutations-ii/)

1. 三种方法

   1. 依然如没有重复的情况一样将所有的结果都遍历一遍。在存入某一条记录的时候看结果集是否有与之相同的结果。
   2. 先排序（重复问题数组问题带的隐藏属性）在有序的环境下进行如没有重复情况下的交换操作。（一定要注意在整个递归过程都要保证这个特性）还有一点要注意的是用Arrays.sortz(nums,a,b).b的小标是第一个不排序的下标。
   3. 回溯+剪枝

2. 这道题可以作为理解“回溯算法”的入门题。这是一个非常典型的使用 回溯算法 解决的问题。解决回溯问题，我的经验是 一定不要偷懒，拿起纸和笔，把这个问题的递归结构画出来，一般而言，是一个树形结构，这样思路和代码就会比较清晰了。而写代码即是将画出的图用代码表现出来。

3. 对于方法一可以不用拷贝中间缓存的list，而是将list维持一个状态

4. 对于这种回溯算法的核心就是要注意状态的重置。

5. 错误示范

   ```java
    public List<List<Integer>> permuteUnique(int[] nums) {
           boolean[] visited=new boolean[nums.length];//这里可以用位图来节省空间
           Arrays.sort(nums);
           List<List<Integer>> res=new ArrayList<List<Integer>>();
           List<Integer>list=new ArrayList<Integer>();
           help(res,list,visited,nums);
           return res;
       }
       private void help(List<List<Integer>>res,List<Integer> list,boolean[]visited,int[] nums){
           if(list.size()==nums.length){
               System.out.println("**********************");
               System.out.println("size:"+list.size());
               System.out.println("length:"+nums.length);
               System.out.println("the number of the list");
               for(int e:list){
                   System.out.println("e:"+e);
               }
               res.add(list);//这里添加的是list的引用地址所以每次递归添加的引用都是一样的，随着递归结束list也变为空，所以最后的结果为空值。
               System.out.println("res.size:"+res.size());
               System.out.println("**********************");
               return;
           }
           for(int i=0;i<nums.length;){
               if(!visited[i]){
                   list.add(nums[i]);
                   visited[i]=true;
                   help(res,list,visited,nums);
                   //以下的两句都是为了在回溯算法中的状态重置。
                   list.remove(list.size()-1);
                   visited[i]=false;
                   i++;
                   while(i<nums.length&&nums[i]==nums[i-1]){
                       i++;
                   }
               }else{
                   i++;
               }
           }
       }
   ```




## [784. Letter Case Permutation](https://leetcode-cn.com/problems/letter-case-permutation/)

1. 这道题要区分与前面的全排列
2. 这个的顺序是不用改变的。
3. 使用字符数组而不使用string 加快处理速度。

## [526. Beautiful Arrangement](https://leetcode-cn.com/problems/beautiful-arrangement/)

## 对于回溯问题的总结

1. 这类问题的基本特征就是组合形式的问题，（常见的解决操作就是画出树状图）
2. 对递归方程的理解是
   1. 递归中传递的变量为将要处理的位置
   2. 递归中的循环（有时也不用循环如784）是为这个位置可能放的值。
3. 可以比较在全排列问题中用交换方法与直接存入数组方法的区别。
   1. 对于前者的递归中传递的变量为要交换的范围的起始地址（结束地址为数组的末尾）（这里的回溯递归中传递的变量的循环不应该理解为到达该层递归位置时应该能够在这个位置存的值的可能性，而是应该理解为该位置与其它多少位置有交换的可能性，所以回溯算法中递归函数中的循环也不尽是这些可能能够在该层位置赋值的值）

## [667. 优美的排列 II](https://leetcode-cn.com/problems/beautiful-arrangement-ii/)

1. 回溯法

2. 不用回溯的方法（题目只要求找到一个这样的数组就可所以可以用构造的方法（找规律））（do it again）

```java
思路分析： 这道题就是找规律吧。
当n = 50， k = 20时：
[1,21,2,20,3,19,4,18,5,17,6,16,7,15,8,14,9,13,10,12,11,
 
 ,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50]
当n = 50，k = 17时：
[1,18,2,17,3,16,4,15,5,14,6,13,7,12,8,11,9,10,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50]
当n = 80，k = 30时：
[1,31,2,30,3,29,4,28,5,27,6,26,7,25,8,24,9,23,10,22,11,21,12,20,13,19,14,18,15,17,16,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80]
是不是发现了规律，就是
下标从[0, k]中，偶数下标填充[1,2,3…]，奇数下标填充[k + 1, k, k - 1…]，后面[k + 1, n - 1]都是顺序填充
```

3. 双指针

4. 不停的翻转



