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

1.解法一:用全排列将所有的组合（m个0，n个1组成）但是这样有问题，本来的方案是将这个数组选的时候化为三种状态选零选一，不选。但是这样情况有重叠。	这样转换为了另一种全排列。（长度不相同的全排列）（这比较复杂还要考虑几个一几个零的多种情况在同一个长度内）（暂时做不出来用这种方法找不到所有长度的全部组合）

2.有点像多重背包

3.这是一道多重背包的问题（物体是字符串）而01的数量看成是两个维度的限制。

4.这个题的初始化动态规划的初始状态可以从第一个参与计算的数值进行推算如本题会直接利用到dp00 的情况是dpij =max(dpij,dp i-czero j-cone)这个如果让初始值dp00 为一那么这个值就会等于二明显不符和题意。

```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        if(strs.length==0)return 0;
        int dp[][] =new int [m+1][n+1];
    //    dp[0][0]=1;
        for(String str:strs){
            int countZero=0;
            int countOne=0;
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)=='1'){
                    countOne++;
                }else{
                    countZero++;
                }
            }
            if(countOne>n||countZero>m)continue;
            for(int i=m;i>=countZero;i--){
                for(int j=n;j>=countOne;j--){
                  dp[i][j]=Math.max(dp[i][j],dp[i-countZero][j-countOne]+1);
                }
            }
        }
        return dp[m][n];
    }
}


```

## [1049. 最后一块石头的重量 II](https://leetcode-cn.com/problems/last-stone-weight-ii/)（more）

​	Method 1：每次让数组中两个最大的值相减。	一直这样减到只有数组中只有一个数或者没有数字（但是这样好像不能够覆盖所有情况）

错误的案例

```none
[31,26,33,21,40] 输出 9 预期 5
```

method 2:零一背包需要做一点转换，如前面的一道题一样，将其分成两个部分。

method 3:

```java
class Solution {
    public int lastStoneWeightII(int[] stones) {
        if(stones.length==0)return 0;
        int r=stones.length-1;
        while(r>0){
            int max=Integer.MIN_VALUE;
            for(int i=0;i<=r;i++){
                if(stones[i]>stones[max])max=i;
            }
            int v1=stones[max];
            swap(stones,max,r--);
            max=Integer.MIN_VALUE;
            for(int i=0;i<=r;i++){
                if(stones[i]>stones[max])max=i;
            }
            int v2=stones[max];
            swap(stones,max,r);
            if(v1==v2)r--;
            else{
                stones[r]=Math.abs(v1-v2);
            }
            
            
        }
        if(r<0)return 0;
        return stones[0];
    }
}
```



## [132. 分割回文串 II](https://leetcode-cn.com/problems/palindrome-partitioning-ii/)

## [518. 零钱兑换 II](https://leetcode-cn.com/problems/coin-change-2/)

直接套用零一被背包的模型：但是这个是可以重复选的（这个模型只能应对数组中的数都不一样而且都只能用一次）

那这样看待的话这个就是一个完全背包问题。出错的地方是递推方程写错了。初始值应该设为一，如果没有目标金额可选的货币为空时这个方案数字应为1.

```java
class Solution {
    public int change(int amount, int[] coins) {
        //容量是amout
        int [] dp=new int[amount+1];
        for(int v:coins){
            for(int i=amount;i>=v;i--){
                dp[i]=Math.max(dp[i],dp[i-v]+1);
            }
        }
        return dp[amount];
    }
}
```

```java
class Solution {
    public int change(int amount, int[] coins) {
        //容量是amout
        int [] dp=new int[amount+1];
        dp[0]=1;
        for(int v:coins){
            for(int i=v;i<=amount;i++){
                dp[i]=dp[i]+dp[i-v];//寻找到第i件物品的时候组合方式就是不选这个的加上选上这个的。
            }
        }
        return dp[amount];
    }
}
```



## 337：

## [39. 组合总和](https://leetcode-cn.com/problems/combination-sum/):	

回溯算法（error）

这个题使用回溯算法的关键：

1. 没有重复元素
2. 每个元素都可以无限次被选
3. 每个元素如果每次递归过程都可以被选那么就会出现重复的情况。

使用动态规划。

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> list=new ArrayList<>();
        List<List<Integer>>  res =new ArrayList<List<Integer>> ();
        help(candidates,list,res,target);
        return res;
        
    }
    public void help(int [] candidates,List<Integer>list,List<List<Integer>> res,int target){
        if(target<0)return;
        if(target==0){
            for(List<Integer> aList : res){
                if(list.size()==aList.size()&&list.containsAll(aList)){
                    return;//554,555
                }
            }
            res.add(list);
            return;
        }
        for(int i= 0;i<candidates.length;i++){
            int v=candidates[i];
            List<Integer> copy= new ArrayList<>(list);
            copy.add(v);
            help(candidates,copy,res,target-v);
        }
    }
}
```

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> list=new ArrayList<>();
        List<List<Integer>>  res =new ArrayList<List<Integer>> ();
        help(candidates,list,res,target);
        return res;
        
    }
    public void help(int [] candidates,List<Integer>list,List<List<Integer>> res,int target){
        if(target<0)return;
        if(target==0){
            for(List<Integer> aList : res){
                if(list.size()==aList.size()&&list.containsAll(aList)&&aList.containsAll(list)){//这样不能保证所有的解是相同的。这样也不能保证这两个集合的元素相同数量一一对应。
                    return;
                }
            }
            res.add(list);
            return;
        }
        for(int i= 0;i<candidates.length;i++){
            int v=candidates[i];
            List<Integer> copy= new ArrayList<>(list);
            copy.add(v);
            help(candidates,copy,res,target-v);
        }
    }
}
```

真正的回溯解法

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
    
        List<List<Integer>>  res=new ArrayList<>();
        Arrays.sort(candidates);
        List<Integer> list=new ArrayList<>();
        helper(candidates,target,res,list,0);
        return res;
    }
    public void helper(int [] candidates,int target,List<List<Integer>>res,List<Integer> list, int start){
    
        
        if(target<0)return;
        if(target==0){
            List<Integer> copy =new ArrayList<>(list);
            res.add(copy);
            return;
        }
        for(int i=start;i<candidates.length;i++){
            list.add(candidates[i]);
            helper(candidates,target-candidates[i],res,list,i);
            list.remove(list.size()-1);
        }
    }
}
```

动态规划的解

```java
class Solution {
  public List<List<Integer>> combinationSum(int[] candidates, int target) {
      List<List<Integer>> result = new ArrayList<>();
      Map<Integer,Set<List<Integer>>> map = new HashMap<>();
      //对candidates数组进行排序
      Arrays.sort(candidates);
      int len = candidates.length;
      for(int i = 1;i <= target;i++){
          //初始化map
          map.put(i,new HashSet<>());
          //对candidates数组进行循环
          for(int j = 0;j < len&&candidates[j] <= target;j++){
              if(i == candidates[j]){
                  //相等即为相减为0的情况，直接加入set集合即可
                  List<Integer> temp = new ArrayList<>();
                  temp.add(i);
                  map.get(i).add(temp);
              }else if(i > candidates[j]){
                  //i-candidates[j]是map的key
                  int key = i-candidates[j];
                  //使用迭代器对对应key的set集合进行遍历
                  //如果candidates数组不包含这个key值，对应的set集合会为空，故这里不需要做单独判断
                  for(Iterator iterator = map.get(key).iterator();iterator.hasNext();){
                      List list = (List) iterator.next();
                      //set集合里面的每一个list都要加入candidates[j]，然后放入到以i为key的集合中
                      List tempList = new ArrayList<>();
                      tempList.addAll(list);
                      tempList.add(candidates[j]);
                      //排序是为了通过set集合去重
                      Collections.sort(tempList);
                      map.get(i).add(tempList);
                  }
              }
          }
      }
      result.addAll(map.get(target));
      return result;
  }
}

```

这里可以看出动态规划与回溯之间是一对一的关系，回溯是自顶向下的而动态规划自下向上的。

## [322. 零钱兑换](https://leetcode-cn.com/problems/coin-change/)（fail)

这个题不能理解为一个完全背包问题。

用上回溯方法优化中的问题。（先写出递推方程）

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp=new int[amount+1];
        for(int i=1;i<dp.length;i++){
            dp[i]=1;
        }
        
        for(int v:coins){
            for(int i=v;i<=amout;i++){
                dp[i]=Math.min(dp[i],dp[i-v]+1);
            }
        }
        return dp[amout];
        
    }
}		
```

## [120. 三角形最小路径和](https://leetcode-cn.com/problems/triangle/)

Method1：回溯找出所有的路径,时间复杂度为n的阶乘，超时。在triangele.size()=196.

这种适用于寻找所有的路径其实不适合本题，本题具有明显的递推性质

```java
class Solution {
    int res;
    public int minimumTotal(List<List<Integer>> triangle) {
        //这是nim
        res=Integer.MAX_VALUE;
        helper(triangle,0,Integer.MAX_VALUE,-1);
        return res;
        
    }
    /**
    index是层数
    temp是中间值
    selected 上一个被选的值
    */
    public void  helper(List<List<Integer>>triangle, int index,int temp,int selected){//如果用把中间值用参数存起来那么就不容易发现重复的子问题。
        if(index==triangle.size()){
            System.out.println(triangle.size());
            res = Math.min(res,temp);
            return;
        }
        List<Integer> curList =  triangle.get(index);
        if(selected==-1){
            int v= curList.get(0);
            helper(triangle,index+1,v,0);
        }else{
            int candidate1 = selected;
            int candidate2 = selected+1;
            helper(triangle,index+1,temp+curList.get(candidate1),candidate1);
            if(candidate2<curList.size()){
                helper(triangle,index+1,temp+curList.get(candidate2),candidate2);
               
            }

        }
    }
}
```

另一种递归(注意这种回溯算法的形式)这种写法在这道题更好。

```java
public int minimumTotal(List<List<Integer>> triangle) {
    row=triangle.size();
    return helper(0,0, triangle);
}
private int helper(int level, int c, List<List<Integer>> triangle){
    // System.out.println("helper: level="+ level+ " c=" + c);
    if (level==row-1){
        return triangle.get(level).get(c);
    }
    int left = helper(level+1, c, triangle);//这两个参数可能遇到重复的情况。把这两个存起来。。。。
    int right = helper(level+1, c+1, triangle);
    return Math.min(left, right) + triangle.get(level).get(c);
}
改进,避免重复计算

自顶向下, 记忆化搜索 【AC】
int row;
Integer[][] memo;

public int minimumTotal(List<List<Integer>> triangle) {
    row = triangle.size();
    memo = new Integer[row][row];
    return helper(0,0, triangle);
}
private int helper(int level, int c, List<List<Integer>> triangle){
    // System.out.println("helper: level="+ level+ " c=" + c);
    if (memo[level][c]!=null)
        return memo[level][c];
    if (level==row-1){
        return memo[level][c] = triangle.get(level).get(c);
    }
    int left = helper(level+1, c, triangle);
    int right = helper(level+1, c+1, triangle);
    return memo[level][c] = Math.min(left, right) + triangle.get(level).get(c);
}

```

DP 。。。。

如果之前的理解是在第二种回溯的基础上那么就很容易得出这个。

效果有点不好

```java
执行用时 :
462 ms, 在所有 Java 提交中击败了5.06%的用户
内存消耗 :49 MB, 在所有 Java 提交中击败了5.00%的用户
```



```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int dp[] =new int [triangle.get(triangle.size()-1).size()] ;
        dp [0]=triangle.get(0).get(0);
        System.out.println(dp[0]);
        for(int i=1;i<triangle.size();i++){//深度方向的遍历.
            List<Integer> curList = triangle.get(i);
            for(int j=curList.size()-1;j>=0;j--){//横向遍历。。
                int candidate1= j==curList.size()-1?Integer.MAX_VALUE:dp[j]+curList.get(j);
                int candidate2 = j==0?Integer.MAX_VALUE:dp[j-1]+curList.get(j);
                System.out.println("*********");
                System.out.println(candidate1+"cnadidate1");
                System.out.println(candidate2+"candidate2");
                System.out.println("**********");
                dp[j] =Math.min(candidate1,candidate2);
            }
        }
        int res =Integer.MAX_VALUE;
        for(int e:dp){
            res = Math.min(res,e);
        }
        return res;
    }
}
```

## [198. 打家劫舍](https://leetcode-cn.com/problems/house-robber/)

```java
class Solution {
    public int rob(int[] nums) {
        int[] dp = new int [nums.length+1];
        if(nums.length==0)return 0;
        dp[0]=0;
        dp[1]=nums[0];
        if(nums.length==1)return dp[1];
        for(int i=2;i<=nums.length;i++){
            dp[i]=Math.max(dp[i-2]+nums[i-1],dp[i-1]);
        }
        return dp[nums.length];
            
    }
}
```

```java
//递归写法
class solution{
    public  int rob(int[] nums){       
        return helper(nums.length-1,nums);
    }
    public int  helper(int index, int[] nums){
        
        if(index==1) return nums[1];
        if(index==0)return nums[0];
        int candidate1 = helper(index-1,nums);
        int  candidate2 =helper(index-2,nums)+nums[index];
        return Math.max(candidate1,candidate2);
    }
}
```

## [213. 打家劫舍 II](https://leetcode-cn.com/problems/house-robber-ii/)(fail)

这个题的想法是如果最后一家没有打劫到那么就直接使用打劫前i-1家的钱，否则就找到加入最后一家的方案的源头（这个过程还是没有看到len =2 的特殊情况考虑清楚）看它是不是1,如果是的话就使用dp【i-1】否则使用dp[i]，（也就是假设最大值就是在这两个值之间找）。

```java
class Solution {
    public int rob(int[] nums) {
        
        int  len =nums.length+1;
        if(len==1)return 0;
        int[] dp =new int[len];
        dp[1]=nums[0];
        if(nums.length>=2){
            dp[2]=Math.max(nums[1],nums[0]);
        }
        if(nums.length==3){
            return Math.max(Math.max(nums[1],nums[0]),nums[2]);
        }
        for(int i=3;i<len;i++){
            dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i-1]);
        }
        if(len==2)return dp[1];
        if(dp[len-2]==dp[len-1]){
            System.out.println("fd"+dp[len-1]);
            System.out.println(len-1);
            return dp[len-2];
        }else{
            System.out.println("fd"+dp[len-2]);
            System.out.println("fds"+dp[len-1]);
            int origin=getOrigin(nums,dp,len-1);
         System.out.println("origin"+origin);
            return origin==1?dp[len-2]:dp[len-1];
        }
    }
    
    private int getOrigin(int[]nums,int [] dp,int len){
       // System.out.println("note len"+len);
         if(len==2){
            return nums[1]>=nums[0]?2:1;
        }
        if(len==1){
            return 1;
        }
        if(dp[len-1]==dp[len-2]+nums[len-1]){
            int candidate1= getOrigin(nums,dp,len-1);
            int candidate2= getOrigin(nums,dp,len-2);
            if(candidate1!=1)return candidate1;
            return candidate2;
        }
        if(dp[len]==dp[len-1]){
            return getOrigin(nums,dp,len-1);
        }else{
            return getOrigin(nums,dp,len-2);
        }
    }
```

两种范围的递归

```java
class Solution {
    public int rob(int[] nums) {
        if(nums.length==0)return 0;
        if(nums.length==1)return nums[0];
        if(nums.length==2)return Math.max(nums[1],nums[0]);
        return Math.max(help(nums,1,nums.length-1),help(nums,0,nums.length-2));
        
    
    }
    private int help(int[] nums,int start, int  end){
        int len = end-start+1;
        System.out.println(len);
        int[] dp=new int [len+1];
        dp[1] = nums[start];
        for(int i=start+1;i<=end;i++){
            dp[i-start+1]=Math.max(dp[i-start],dp[i-start-1]+nums[i]);
        }
        System.out.println(dp[len]);
        return  dp[len];
    }
    
    
}
```





## [357. 计算各个位数不同的数字个数](https://leetcode-cn.com/problems/count-numbers-with-unique-digits/)

一开始以为直接求所有不相同的数字会比较困难但是没有想到求相同的数字更加困难思路变成了下面这样。

```java
class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        int []dp =new int[n+1];
        dp[0]=0;
        dp[1]=0;
        dp[2]=9;
        dp[3]=9;
        int part1=power(9,(n-2))-dp[n-2]*9   //1.先填充两边（两边都相同）里面的是位 的不等于的数
        int part2=dp[n-1]*9     //2. 填充所有n-1的所有能符合的数字。
        int part3=power(9,n-1)*(n-2);  // 3. 填充单边的数字
        dp[n]=part1+part2+part3;
        return power(9,n)-dp[n];
    }
        123115
        512311
        123415
        112235
        511223
        512341
        512345
        123115
        123455
            12343
            31234
            12343
            12341
            
            
            16*15*14
            
        
        
}
```

正确的解

```java
class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        if(n==0)return 1;
        if(n==1)return 10;
        int temp=10;
        int pre=9;
        int res=0;
        for(int i=2;i<=n;i++){
            pre=pre*(10-i+1);
            res=temp+pre; 
            temp=res;
        }
        
        return res;
    }
        
}
```

