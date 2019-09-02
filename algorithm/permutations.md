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

   