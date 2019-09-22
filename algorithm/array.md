# Array



## [219. 存在重复元素 II](https://leetcode-cn.com/problems/contains-duplicate-ii/)

这个题目不能主要是注意标准题目的写法更优

```java
	class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer ,Integer> map=new HashMap<Integer,Integer>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                if(i-map.get(nums[i])<=k){
                    return true;
                }
            }
            map.put(nums[i],i);
        }
        return false;
        
    }
}
```

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < nums.length; ++i) {
        if (set.contains(nums[i])) return true;
        set.add(nums[i]);
        if (set.size() > k) {
            set.remove(nums[i - k]);
        }
    }
    return false;
}

```





## [873. 最长的斐波那契子序列的长度](https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/)

1.method 固定斐波那契数列让它的每个值从数组滑落（但是这并不一定是斐波那契数列）

```java
class Solution {
    public int lenLongestFibSubseq(int[] A) {
        //暴力列出所有的子序列肯定不行。。时间复杂度为2的n次方。
        int base = A[A.length-1];
    
        int temp=0;
        int res=0;
        int[] f= getF(base);//获得一个最大值为base的斐波那契递增数列
        int j=A.length;
        for(int i=f.length-1;i>=0;i--){
            int  v=f[i];
            temp=0;
            for(;j>=0&&v<=A[j];j--){//如果这个斐波那契数列的这个值比当前的数组下标的值要大那么就断掉了记下它的长度。
                if(v==A[j]){
                    temp++;
                }
            }
            res = Math.max(temp,res);
        }
        return res>=3?res:0;        
    }
    private int[] getF(N){
        int len=0;
        int pre1=0;
        int pre2=2;
        
    }
    
}

```

2.实际上直接使用暴力的方法就能够做出这个题来（不要以为的去套动态规划，回溯这些技巧）应该先看清这能不能使用这些技巧，这个题目看起来好像能用动态规划做但是却并不刻意（动态规划的问题大的问题与小的问题是无关的）但是在这道题中，第i个数到第j个数之间的序列长度好像并没有与它相邻扩展的问题有关联，所以不能使用动态规划。可以直接使用这个遍历的方法。（直接看问题得到思路）技巧就是使用了一个hashmap。

正确的解

```java
class Solution {
    public int lenLongestFibSubseq(int[] A) {
        Set<Integer> set =new  HashSet<>();
        for(int num:A){
            set.add(num);
        }
        int res=0;
        for(int i=0;i<A.length-2;i++){
            for(int j=i+1;j<A.length-1;j++){
                int pre1=A[i];
                int pre2=A[j];
                int pre3=pre1+pre2;
                int temp=2;
                while(set.contains(pre3)){
                    temp++;
                    pre1=pre2;
                    pre2=pre3;
                    pre3=pre2+pre1;
                }
                res =Math.max(res,temp);
            }
        }
        return res>=3?res:0;
        
            
            
    }
}

```

## [713. 乘积小于K的子数组](https://leetcode-cn.com/problems/subarray-product-less-than-k/)

```java
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        //1,2,34,53,,6,2,6,34,5
        int preMin=1;
        int count=0;
        int ll=0;
        int len=nums.length;
        for(int i=0;i<len;i++){
            if(nums[i]>=k){
                ll=0;
                preMin=1;
            }else{
                if(preMin*nums[i]<k){
                    ll=ll+1;
                    preMin=preMin*nums[i];
                }else{
                    preMin=nums[i];
                    ll=1;
                    for(int j=i-1;j>=0;j--){
                        if(preMin*nums[j]<k){
                            ll++;
                            preMin*=nums[j];
                        }else{
                            break;
                        }
                    }
                }
            }
            count+=ll;
          
        }
        return count;
        
    }
}
```

## [817. 链表组件](https://leetcode-cn.com/problems/linked-list-components/)

```java
class Solution {
    public int numComponents(ListNode head, int[] G) {
        int len =0;
        int res=0;
        Set<Integer> set =new  HashSet<Integer> ();
        for(int e:G){
            set.add(e);
        }
        for(ListNode n=head;n!=null;n=n.next){
            if(set.contains(n.val)){
                if(len==0)res++;
                len++;
            }else{
                len=0;
            }
        }
        return res;
            
    }
}
```

## [238. 除自身以外数组的乘积](https://leetcode-cn.com/problems/product-of-array-except-self/)

```java

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int right=1;
        int left=1;
        int len= nums.length;
        int[]res=new int[len];
        for(int i=0;i<len;i++){
            res[i]=1;
        }
        for(int i=0;i<len;i++){
            res[i]*=left;
            res[len-i-1]*=right;
            left=nums[i]*left;
            right=nums[len-i-1]*right;
        }
        return res;
    }
}
```

## [152. 乘积最大子序列](https://leetcode-cn.com/problems/maximum-product-subarray/)

进阶版最大子序和

```java
class Solution {
    public int maxProduct(int[] nums) {
        int max =Integer.MIN_VALUE;
        int minI=1;
        int maxI=1;
        for(int i=0;i<nums.length;i++){
            if(nums[i]<0){
                int tmp=minI;
                minI=maxI;
                maxI=tmp;
            }
            minI=nums[i]*minI<nums[i]?nums[i]*minI:nums[i];
            maxI=nums[i]*maxI>nums[i]?nums[i]*maxI:nums[i];
            max=max>maxI?max:maxI;//这一句还是不能省掉（2，3，-2，4）
        }
        return max;
    }
}

```
