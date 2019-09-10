# Dynamic plan

## [139. 单词拆分](https://leetcode-cn.com/problems/word-break/)(success)

简单的动态规划，很容易想到递归关系

假如词典中的的单词不允许重复呢？

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
    if((wordDict.size()==0||wordDict==null)&&(s==null||s.length()==0))return true; 
        if(s==null||s.length()==0)return false;
        boolean [] dp=new boolean[s.length()+1];
        dp[0]=true;
        for(int i=1;i<=s.length();i++){
            for(String word : wordDict){
                if(i>=word.length()&&word.equals(s.substring(i-word.length(),i))){
                    dp[i]=dp[i-word.length()];
                }
                if(dp[i]){
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}
```

## [140. 单词拆分 II](https://leetcode-cn.com/problems/word-break-ii/)（待做）

## [416. 分割等和子集](https://leetcode-cn.com/problems/partition-equal-subset-sum/)（fail | more）

没有想到用零一背包问题去解这道题（实际上这是一道没有重量只看体积的背包问题），在使用动态规划时这个题的难点就是初始值的设定。主要考察01背包问题的初始设定，这里的初始值与01背包中恰好装满的情况时一样的。

这是关键点：

 [***`初始时应该就是这一种的合法情况。在装入一件物品的时候只有刚好与它体积相等的情况才是合法的。这是关键的一点`***]( //初始时应该就是这一种的合法情况。在装入一件物品的时候只有刚好与它体积相等的情况才是合法的。这是关键的一点)

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum=0;
        for(int e:nums){
            sum+=e;
        }
        sum/=2;
        if((sum&1)==1)return false;//这里有个脑残错误这两句应该调换位置。
        boolean [] dp =new boolean [sum+1];
      //  dp[0]=true;//这句就有问题
        //初始化的技巧问题的关键
         for (int j = 1; j < sum + 1; j++) {
            if (nums[0] == j) {
                dp[j] = true;
              //初始时应该就是这一种的合法情况。在装入一件物品的时候只有刚好与它体积相等的情况才是合法的。这是关键的一点
                break;
            }
        }
        for(int i=1;i<nums.length;i++){
            for(int j=sum;j>=nums[i];j--){
               	    dp[j]=dp[j]||dp[j-nums[i]];          
            }
        }
        return dp[sum];
    }
}

```

```java
class Solution {
   public boolean canPartition(int[] nums) {
        int size = nums.length;
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if ((s & 1) == 1) {
            return false;
        }

        int target = s / 2;
        // 从第 2 行以后，当前行的结果参考了上一行的结果，因此使用一维数组定义状态就可以了
        boolean[] dp = new boolean[target + 1];
        // 先写第 1 行，看看第 1 个数是不是能够刚好填满容量为 target
        for (int j = 1; j < target + 1; j++) {
            if (nums[0] == j) {
                dp[j] = true;
                // 如果等于，后面就不用做判断了，因为 j 会越来越大，肯定不等于 nums[0]
                break;
            }
        }
        // 注意：因为后面的参考了前面的，我们从后向前填写
        for (int i = 1; i < size; i++) {
            // 后面的容量越来越小，因此没有必要再判断了，退出当前循环
            for (int j = target; j >= 0 && j >= nums[i]; j--) {
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }
        return dp[target];
    }

}

```

## [494. 目标和](https://leetcode-cn.com/problems/target-sum/)

第一眼看过去就像是回溯算法(很容易做).确实可以用回溯算法做

方法二使用呢动态规划做（先将问题转换为子数组组合问题需要一步数学推理）。同样这个问题就是一个零一背包问题的变种（它对应于体积恰好相等的情况）注意初始化的情况。

这也说明了动态规划与回溯（递归）之间有着千丝万缕的关系。

```java
class Solution {
    int res;
    public int findTargetSumWays(int[] nums, int S) {
        res=0;
        helper(nums,S,0,0);
        return res;
        
    }
    public void helper(int[]nums,int target,int p,int tempV){
        if(p==nums.length){
            if(tempV==target){
               res++;
            }
            return;
        }
        int nextTemp=tempV-nums[p]; 
        helper(nums,target,p+1,nextTemp);
        nextTemp =tempV+nums[p]; 
        helper(nums,target,p+1,nextTemp);
           
    }
}
```

```java
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum =0 ;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        if(sum<S)return 0;
        if(((sum+S)&1)==1)return 0;
        int target= (sum+S)/2;
        int[] dp=new int[target+1];
        dp[0]=1;
        //到此问题转换为一个求组合数的问题
        for(int i=0;i<nums.length;i++){
            for(int j=target;j>=nums[i];j--){
                dp[j]=dp[j]+dp[j-nums[i]];
            }
        }
        return dp[target];
    }
   
}
```

## [474. 一和零](https://leetcode-cn.com/problems/ones-and-zeroes/)（待做）

1.解法一:用全排列将所有的组合（m个0，n个1组成）但是这样有问题，本来的方案是将这个数组选的时候化为三种状态选零选一，不选。但是这样情况有重叠。	这样转换为了另一种全排列。（长度不相同的全排列）（这比较复杂还要考虑几个一几个零的多种情况在同一个长度内）



