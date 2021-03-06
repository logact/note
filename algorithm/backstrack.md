# 回溯算法

## 93. Restore IP Addresses

中间出现的调试点：

1. ip地址大小 0~255
2. 最后一个（IP 字段后面没有加 '   .  ' ）去最后一段的特殊情况没有在回溯的过程中而是在结束的时候。应该从最后一个没有加 '  .  '就可以看出来这个字段有一定的特殊性应该在后面处理和递归过程中选择处理。
3. 这个题在做第二遍的时候没有想到用数组而是使用了StringBuilder。所以速度变慢了很多。
4. 还有就是StringBuilder还有要给delete(i,j)的方法。

## [131. Palindrome Partitioning](https://leetcode-cn.com/problems/palindrome-partitioning/)

笔误

用动态递归的方法判断字符字串是否是回文串。

第二遍做这个题 的结果是没有掌握好判定回文字符串的dp写法导致各种问题。一定要注意判定回文字符串时的dp操作，这个很容易出错，注意两个下标的增长方向，不要搞反。

```java
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> res =new  ArrayList<>();
        List<String> list =new ArrayList<>();
        if(s==""||s==null){
            list.add(s);
            res.add(list);
        }
        helper(res,list,s,0);
        return res;
    }
    public void helper(List<List<String>> res ,List<String> list,String s, int index){
        int  len =s.length();
        if(len==index){
            List<String> aRes =new  ArrayList<>(list);
            res.add(aRes);
            return;
        }
        
        for(int  i=index;i<len;i++){
            String candidate = s.substring(index,i+1);
            if(isPalindrome(candidate)){
                list.add(candidate);
                helper(res, list,s,i+1);
              f  list.remove(list.size()-1);//这里出现了笔误。
            }
        }
        
    }
    private boolean isPalindrome(String s){
        int p=0;
        int p1=s.length()-1;
        while(p<=p1){
           if(s.charAt(p)!=s.charAt(p1))return false;
            p++;
            p1--;
        }
        return true;
    }
}
```





## [216. 组合总和 III](https://leetcode-cn.com/problems/combination-sum-iii/)

模板经典

```java
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res =new ArrayList<>();
        List<Integer> list= new ArrayList<>();
        helper(res,list,1,n,0,k);
        return res;
    }
    public void helper(List<List<Integer>> res,List<Integer> list,int index,int target,int times,int targetTimes){
        if(target==0){
            if(times==targetTimes){
                List<Integer> temp = new ArrayList<>(list);
                res.add(new ArrayList<Integer>(list));
            }
        }
        if(times>=targetTimes)return;
        if(target<0){
            return;
        }
        for(int i= index;i<10;i++){
            list.add(i);
            helper(res,list,i+1,target-i,times+1,targetTimes);//模板不要写helper(res,list, index+1,target-i,times+1,targetTimes);
           
            list.remove(list.size()-1);
            
        }
    }
}
```



##  [306. 累加数](https://leetcode-cn.com/problems/additive-number/)(fail)

1.没有优化 注意舒服相加的进位。循环过多注意跳步

2.先确定前面两个数。

3.第二次做的错误这里不要直接把将i++; 代入到循环中没有符合条件都会加上这个值，没有用long的方式做出来。

```java
class Solution {
    public boolean isAdditiveNumber(String num) {
        if(num==null||num.length()<3)return false;
        char[] nums=num.toCharArray();
        return  helper(nums,2);
    }
    private boolean helper(char[] nums,int index){
        if(index==nums.length){
            
            return true;
        }
        point1:
        for(int start=index;start<nums.length ;start++){//遍历右边数的起始点
            if(nums[start]=='0'){
                continue;
            }
        point2:
        for(int i=index;i<nums.length;i++){//遍历右边数的末端点所有可能性，i是 这个数字的终端。
            long  num1=getNum(nums,start,i);
            int  len1= i-start+1;
            
            
            for(int  j=start-1;j>0;j--){//遍历右边的紧挨着右边那个数第一个数。
                if(nums[j]=='0')continue;
                long num2=getNum(nums,j,start-1);//取j~i-1。
                int  len2=start-j;
                 
                for(int k=j-1;k>=0;k--){//遍历右边紧挨的右边数的第二个数。
                    if(nums[k]=='0'&&(j-1-k)!=0){
                        continue;
                    }
                    long num3=getNum(nums,k,j-1);//取k~k-1
                    int len3=j-k;
                    if(len3>Math.max(len1,len2)+1){
                        break point1;
                        
                    }
                    if(len2>Math.max(len1,len3)+1){
                        break point2;//num2 太大让num1 的末尾重置
                    }
                    
                    if(num1==num2+num3){
                        
                        if( helper(nums,i+1)){
                            return  true;
                        }
                    }
                }
            }
            
        }
            
        }
        System.out.println("1");
        return false;
    }
    private long getNum(char[]nums,int s,int e){
        long res=0;
        for(int i=s;i<=e;i++){
            long num =(long)( nums[i]-'0');
            res =10*res +num;
        }
        return res;
    }
}
```

```java
//第二次做的错误。。。。。。。
class Solution {
    int lenN;
    String num;
    public boolean isAdditiveNumber(String num) {
       //16:44
        lenN=num.length();
        this.num =num;
        for(int i=0;i<=32&&i<lenN/2;i++){
            String pre1=num.substring(0,i+1);
            for(int j=i+1;j-i<=32&&j<lenN-1;j++){//还有点细节为了速度暂时不考虑
                if(num.charAt(i+1)=='0'&&j!=i+1)break;
                String pre2=num.substring(i+1,j+1);
                if(helper(pre1,pre2,j+1))return true;
            }
        }
        return false;
    }
    private boolean helper(String pre1,String pre2,int index){
        if(index==lenN)return true;
        String sum =sum(pre1,pre2);
        int i=0;
        while(index<lenN&&i<sum.length()&&sum.charAt(i)==num.charAt(index)){//这里不要直接把将i++; 代入到循环中没有符合条件都会加上这个值
            i++;
            index++;
        };
        if(i!=sum.length())return false;
        return helper(pre2,sum,index);
    }
    private String sum(String s1,String s2){
        int p1=s1.length()-1;
        int p2=s2.length()-1;
        int pop=0;
        StringBuilder res =new StringBuilder();
        while(p1>=0||p2>=0||pop!=0){
            System.out.println(pop);
            int sum=0;
            if(p1>=0){
                sum+=s1.charAt(p1)-'0';
                p1--;
            }
            if(p2>=0){
                sum+=s2.charAt(p2)-'0';
                p2--;
            }
            if(pop>0){
                sum+=pop;
            }
            res.insert(0,sum%10);
            pop =sum/10;
        }
        System.out.println("s1:"+s1+"s2:"+s2+"res:"+res);
        return res.toString();
    }
    
}

```



## [90. 子集 II](https://leetcode-cn.com/problems/subsets-ii/)

复习

```java
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res =new ArrayList<>();
        List<Integer> list= new ArrayList<>();
        res.add(Collections.emptyList());
        helper(nums,0,res,list);
      
        return res;
    }
    private  void helper(int[] nums,int index, List<List<Integer>> res, List<Integer> list ){
        for(int i=index;i<nums.length;){
            list.add(nums[i]);
            List<Integer>copy =new ArrayList<>(list);
            res.add(copy);
            helper(nums,i+1,res,list);
            list.remove(list.size()-1);
            while(++i<nums.length&&nums[i]==nums[i-1]);
        }
    }
}
```

#### 



