# Greedy

## 392.Is Subsequence

## 455. Assign Cookies

## 860.Lemonada Change

## 1005. Maximize Sum Of Array After K Negations

对于取相反数的情况，每次都取一次的情况要联想到直接利用奇偶性只取一次就可以了。

## 1029. Two City Scheduling

```java
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return (a[1] - a[0]) - (b[1] - b[0]);
            }
        });
        int cost = 0;
        for (int i = 0; i < costs.length / 2; i++) {
            cost += costs[i][1] + costs[costs.length-i-1][0];
        }
        return cost;
    }
}
```

## 1046. Last Stone Weight（more）(do it with heap)



also can do it with a heap.

注意粉碎后的结果会破坏数组的有序性。

最大最小就用堆。

```java
 public int lastStoneWeight(int[] stones) {
        Arrays.sort(stones);
        int index=stones.length-1;
        while(index>0){
            int left = Math.abs(stones[index]-stones[index-1]);
            if(left==0){
                index-=2;
            }else{
                
                stones[index-1]=left;
                index--;
                //System.out.println(" index: "+index+":---->>>"   +Arrays.toString(stones));

                for(int i=index;i>0&&stones[i]<stones[i-1];i--){
                    swap(stones,i,i-1);
                }
                
                System.out.println(" index: "+index+":---->>>"   +Arrays.toString(stones));
            }
        }
        if(index<0)return 0;
        return stones[0];
        
    }
    private void swap(int [] stones,int i,int j){
        int temp=stones[i];
        stones[i]=stones[j];
        stones[j]=temp;
    }
}
```

## 1217. Play with Chips

第一遍题没看懂。

odd  and even 

奇偶之间的转换关系



## 1221. Split a String in Balanced Strings（5 min）

## 944. Delete Columns to Make Sorted(10 min)

## 763. Partition Labels(20min)（more elegant solution）

第一时间没有想清直接利用map来做。

## 921. Minimum Add to Make Parentheses Valid（20min）（more elegant solution）

第一次做错了，直接用左右括号数量的差值当作结果返回。这是错误的

左右括号问题

1. 用stack解决

2. 记录两个括号的数量的方式解决

   ```java
   class Solution {
       public int minAddToMakeValid(String S) {
           int leftExtra = 0, rightExtra = 0;
           for (int i = 0; i < S.length(); i++) {
               if (S.charAt(i) == '(') {
                   leftExtra ++;
               } else if (S.charAt(i) == ')') {
                  if (leftExtra > 0) {
                      leftExtra --;
                   
               } else {
                   rightExtra ++;
               }
           }
           }
           return leftExtra + rightExtra;
       }
   }
   ```

   



## 861. Score After Flipping Matrix(fail)(40min)（more elegant solution）（未完待续）

位运算

## 406. Queue Reconstruction by Height（fail）(more)



## 1111. Maximum Nesting Depth of Two Valid Parentheses Strings(fail)

这种括号的题目右括号不重要





## 1090. Largest Values From Labels(more)(没看懂题)

1. 使用优先队列



## 1094. car pool

1. 使用hashmap
2. 使用优先队列

```java
//hashMap 的遍历删除出错与这个遍历器有关。 
for(int i:map.keySet()){
                if(i<=trip[1]){
                    sum-=map.get(i);
                    map.remove(i);
                }
            }
```

```java
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        // 8:10
        Arrays.sort(trips,(t1,t2)->{
            return  t1[1]-t2[1];
        });
        int sum=0;
        Map<Integer,Integer> map =new HashMap<>();
        for(int[]trip:trips){
            
            map.put(trip[2],trip[0]+map.getOrDefault(trip[2],0));
            Iterator<Map.Entry<Integer,Integer>> iterator=map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Integer,Integer> item=iterator.next();
                if(item.getKey()<=trip[1]){
                    sum-=item.getValue();
                    iterator.remove();
                }
            }
            sum+=trip[0];
            System.out.println(sum+">>>"+map);
            if(sum>capacity)return false;
        }
        return true;
    }
    
}

```





## 714. Best Time to Buy and Sell Stock with Transaction Fee(fail)

动态规划

```java
 public int maxProfit(int[] prices, int fee) {
        int res =0;
        int cost=0;
        int lastBuyTime=-1;
        for(int i= 1;i<prices.length;i++){
            if(prices[cost]>=prices[i]){
                cost=i;
            }else if(lastBuyTime==cost||lastBuy){
                res+=prices[i]-prices[cost];
                lastBuyTime=i;
                cost=i;
            }else{
                if(prices[i]>prices[cost]+fee){
                    res+=prices[i]-prices[cost]-fee;
                    lastBuyTime=i;
                    cost=i;
                }
            }
            System.out.println(res);
        }
        return res;
    }
```

```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int res =0;
        int hold =-prices[0];
        for(int i=1;i<prices.length;i++){
            res = Math.max(res,hold+prices[i]-fee);
            hold =Math.max(hold,res-prices[i]);
        }
        return res;
    }
}
```



