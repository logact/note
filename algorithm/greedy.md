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