# dp

## 877.(fail 超时，more)

博弈问题的解法。

递归解法，动态规划解法。

这个题目初用动态规划会发现如果直接使用递推关系，这个递归的走势是斜的。也能够直接发现递归的过程。只要直接使用 一个数组存储起来就能够避免重复元素按。



## 712.(fail)

这个题目与编辑距离这道题目有关系。

## 647.(23min)

1. 回文串的动态规划。
2. 易错点回文串在只剩下两个时候的判断
3. 递归操作的走势。

## 714.（14min）

1. 有交易费用的买卖股票
2. 使用贪心算法
3. 买卖股票的一系列的问题。

## 931.（fail）（10min）

使用带返回值的回溯算法形式（这样方便对递归过程进行优化，可以清楚的看见这个优化的过程。）用一个二维数组存储中间产生的函数值避免重复运算。

## 413.

## 1143.(7 min)code-cn.com/problems/shopping-offers/)

## 638.(fail)大礼包

1. 状压dp，完全背包

2. 回溯算法

## 983.（fail）

无法找到dp方程。

使用回溯算法（带一个记忆数组）但是这个数组要有两个变量，因为这个方程是由两个变量唯一确定的值，这样就不能把这个maxdate设为全局变量。

## 1140.（fail）

回溯使用20min

## 343.fail

两种方法

1. 动态规划
2. 回溯算法。

## 1130.fail

## 646.（wrong）

```java
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));
```

如果是从后往前的dp就会增加复杂度。多了一个判断取最大值的过程。最好使用一个从前往后的方式。

## 279.（ace)

1.  完全背包
2. 直接用dp
3. the Lagrange's Four Square theorem

## 740（wrong）	

转换为打家劫舍

每次删除残存最大的值，错误的做法。如果有较小的值很多的情况下

error

```java
  // 9:52
        // 123456
        // 1236
        int lenN=nums.length;
        Arrays.sort(nums);
        Set<Integer> set =new HashSet<Integer>();
        for(int num:nums){
            set.add(num);
        }
        int res=0;
        for(int i=lenN-1;i>=0;i--){
            if(!set.contains(nums[i]))continue;
            set.remove(nums[i]);
            res=res+nums[i];
            if(set.contains(nums[i]-1)){
                set.remove(nums[i]-1);
            }
            if(set.contains(nums[i]+1))
            {
                set.remove(nums[i]+1);
            }
        }
        return res;
    }
    // [3,3,3,4,2]error
}
```



## 486 (wrong)

博弈问题 通用模板

```java
class Solution {
    public boolean PredictTheWinner(int[] nums) {
        // 10:08
        int lenN=nums.length;
        int[][]dp=new int[lenN+1][lenN+1];
        //dp[i][j] dp[i+1][j]||dp[i][j-1];
        int[]sum=new int[nums.length];
        sum[0]=nums[0];
        for(int i=1;i<lenN;i++){
            sum[i]=sum[i-1]+nums[i];
        }
        for(int i=lenN-1;i>=0;i--){
            for(int j=i;j<lenN;j++){
                if(j==i+1){
                    dp[i][j]=Math.max(nums[i],nums[j]);
                }else if(i==j){
                    dp[i][j]=nums[i];
                    System.out.println(dp[i][j]);
                }else if(j!=0){
                    // int max=Math.max(dp[i+1][j],dp[i][j-1]);
                    // dp[i][j]=sum[j]-sum[i]-max+nums[i];//这样就是放弃了先手的机会。
                    dp[i][j]=Math.max(sum[j]-sum[i]-dp[i+1][j]+nums[i],sum[j]-sum[i]-dp[i][j-1]+nums[i]);
                }
            }
        }
        for(int i=0;i<lenN;i++){
            for(int j=0;j<lenN;j++){
                System.out.print(dp[i][j]+"--");
                
            }
            System.out.println(">>>");
        }
        return dp[0][lenN-1]>=(sum[lenN-1]+1)/2;
    }
    
}
```



## 264

丑数之间的关系。构建丑的过程可以被规划为一个动态规划过程。

丑数求解过程：先除二除到不能除为止，再除三除到不能除为止，再除五除到不能除。如果剩下的数为零那么这个数就是丑数。



## 718

与最长公共子序列不同?这个必须是前缀都相同。

## 764（good problem）

## 516







