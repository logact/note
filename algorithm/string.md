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

## [842. 将数组拆分成斐波那契序列](https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/)

1.这个题目与前面的306累加数很像几乎是一样的。

2.这个题目我是使用了字符串相加的操作避免整数溢出但是这个题目实际上是直接用long类型处理（事实上很多题目都是这样）

3.这个题目出错的地方：

a.对前导零的判断

b.处理字符串相加时 的操作。



```java
class Solution {
    char[] nums;
    List<Integer> res;
    public List<Integer> splitIntoFibonacci(String S) {
        res =new  ArrayList<>();
        if(S==""||S==null||S.length()<3)return res;
        nums =S.toCharArray();
        int len =S.length();
        for(int i=0;i<len/2&&i<10;i++){
            String num1 = get(0,i);
            if(isNotInteger(num1)){
                break;
            }
            if(num1.length()!=1&&num1.charAt(0)==0){
                break;
            }
         
            for(int j=i+1;j-i<=10&&j<len&&len-(j+1)>=j-i;j++){
                if(nums[i+1]=='0'&&j-i-1!=0)break;
                String num2 = get(i+1,j);
               
                if(isNotInteger(num2)){
                    break;
                }
                res.add(Integer.valueOf(num1));
                res.add(Integer.valueOf(num2));
                
                if(validate(0,i,j))return res;
                else res.clear();
            }
        }
        return res;
        
    }
    private boolean validate(int start,int mid,int end){
        String num1 = get(start,mid);
        if(num1.length()!=1&&num1.charAt(0)=='0')return false;
        String num2 = get(mid+1,end);
        if(num2.length()!=1&&num2.charAt(0)=='0')return false;
        String sum =sum(num1,num2);
        if(sum.length()!=1&&nums[end+1]==0)return false;
        if(isNotInteger(num1)||isNotInteger(num2)||isNotInteger(sum)){
            System.out.println("point0");
            return false;
        }
        for(int i=0;i<sum.length();i++){
            if(end+i+1>=nums.length){System.out.println("point1");return false;}
            if(sum.charAt(i)!=nums[i+end+1]){System.out.println("point2");return false;}
        }
        res.add(Integer.valueOf(sum));
        if(sum.equals("82"))System.out.println("dfs"+res);
        if(end+sum.length()==nums.length-1){
           // System.out.println("ffdssh8jhhk");
            return true;
            
        }
        
        if(sum.equals("82"))System.out.println("dfs"+res);
        if(validate(mid+1,end,end+sum.length())){
            
            System.out.println("res"+res);
            return true;
        }
        else {
             if(sum.equals("82"))System.out.println("dfs"+res);
            res.remove(res.size()-1);
            return false;
        }
        
    }
    private String get(int start ,int  end){
        String res="";
        for(int i=start;i<=end;i++){
            res+=nums[i];
        }
        return res;
    }
    private String sum(String num1,String num2 ){
        int pop=0;
        StringBuilder res =new StringBuilder();
        int  sum=0;
        int  next=0;
        int  p1=num1.length()-1;
        int p2=num2.length()-1;
        while(p1>=0||p2>=0||pop!=0){
            if(p1<0&&p2<0){
                res.insert(0,pop);
                pop=0;
            }else if(p1<0){
                sum = pop+num2.charAt(p2)-'0';
                next = sum%10;
                pop=sum/10;
                res.insert(0,next);
                p2--;
            }else if(p2<0){
                 sum = num1.charAt(p1)-'0'+pop;
                next = sum%10;
                pop=sum/10;
                res.insert(0,next);
                p1--;
            }else{
                sum = num1.charAt(p1)-'0'+num2.charAt(p2)-'0'+pop;
                next = sum%10;
                pop=sum/10;
                res.insert(0,next);
                p1--;
                p2--;
                
            }
        }
        System.out.println("test num1 :"+num1);
        System.out.println("test num2 :"+num2);
        System.out.println("test sum  :"+res);
        return res.toString();
        
    }
    private  boolean isNotInteger(String num){
        //如果这个字符串表示的是不是整数返回true
        if(num.length()>10)return true;
        if(num.length()<10)return false;
        String base = "2147483647";
        if(base.compareTo(num)<0){
            return true;
        }
        return false;
    }
}
```

改进了字符加法操作：

```java
class Solution {
    char[] nums;
    List<Integer> res;
    public List<Integer> splitIntoFibonacci(String S) {
        int len =S.length();
        res =new ArrayList<>();
        if(S==null||S.length()<3)return res;
        nums=S.toCharArray();
        for(int i= 0;i<10&&i<len/2;i++){
            if(i!=0&&nums[0]=='0')return res;
            String num1=getNum(0,i);
            if(isNotInteger(num1))return res;
            for(int j=i+1;j<len&&j-i<=10&&j-i<=len-(j+1);j++){
                if(nums[i+1]=='0'&&j!=i+1){
                    break;
                }
                String num2 =getNum(i+1,j);
                if(isNotInteger(num2))break;
                res.add(Integer.valueOf(num1));
                res.add(Integer.valueOf(num2));
                if(validate(0,i,j)){
                    return res;
                }else{
                    res.clear();
                }
            }
            
        }
        
        return res;
    }
    public boolean validate(int start,int mid,int end ){
        String num1 = getNum(start ,mid);
        String  num2 = getNum(mid+1,end);
        String sum =sum(num1,num2);
        if(isNotInteger(sum))return false;
        if(sum.length()==0&&nums[end+1]=='0'){
            return false;
        }
        for(int i=0;i<sum.length();i++){
            if(i+end+1>=nums.length)return false;
            if(sum.charAt(i)!=nums[i+end+1]){
                return  false;
            }
        }
          res.add(Integer.valueOf(sum));
        if(sum.length()+end==nums.length-1){
            return true;
        }
        
     
        if(validate(mid+1,end,end+sum.length())){
            return true;
        }else{
            res.remove(res.size()-1);
             return false;
        }
       
        
        
    }
    private String getNum(int start,int end){
        String res ="";
        for(int i =start;i<=end;i++ ){
            res+=nums[i];//字符串可以直接加上个字符型变量
        }
        return res;
    }
    private String sum(String num1,String num2){
        StringBuilder res =new StringBuilder();
        int pop=0;
        int p1=num1.length()-1;
        int p2=num2.length()-1;
        while(pop!=0||p1>=0||p2>=0){
            int sum =0;
            if(p1>=0){
                sum+=num1.charAt(p1)-'0';
                p1--;
            }
            if(p2>=0){
                sum+=num2.charAt(p2)-'0';
                p2--;
                
            }
            sum+=pop;
            pop=sum/10;
            int a =sum%10;
            res.insert(0,a);
        } 
        System.out.println("num1:"+num1);
        System.out.println("num2:"+num2);
        System.out.println("sum:"+res);
        return res.toString();
    }
    /**
    pan duan zhege shu shifou shiyige zhengxiingshu 
    */
    private boolean isNotInteger(String s){
        int len =s.length();
        if(len<10)return false;
        if(len>10)return false;
        String max =Integer.MAX_VALUE+"";
        return max.compareTo(s)<0;
    }

}
```









