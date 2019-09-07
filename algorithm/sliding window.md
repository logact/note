

# Sliding Window

## [239. 滑动窗口最大值](https://leetcode-cn.com/problems/sliding-window-maximum/)

使用双端队列构造一个单调队列：

自己用了一个双向链表来实现了一个双端队列但是出现了两个问题

1. 在实现删除时忘记改变pre指针的指向
2. 超时
3. 同时存了数组下标和数组的值
4. 要解决这道问题就要解决两个问题
   1. 如何实现双端队列（用循环数组）
   2. 理解进队出队的操作
   3. 控制队列中的元素过期的问题（链表中最多只有一个过期的元素）。

于是只能够将这个使用Java自带的一个双端队列（ArrayDeque，使用了循环数组）

对于用堆来做（不合适）

```java
class Solution {
    class ListNode{
        int index;
        int val;
        ListNode pre;
        ListNode next;
        public ListNode(int v,int index){
            this.val=v;
            this.index=index;
            this.pre=null;
            this.next=null;
        }
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res=new int[0];
        if(nums.length==0)return res;
        res=new int[nums.length-k+1];
        ListNode head=new ListNode(0,0);
        ListNode p=head;
        for(int i=0;i<k&&i<nums.length;i++){//处理前k个情况
            if(head.next==null){
                ListNode node= new ListNode(nums[i],i);
                node.pre=p;
                p.next=node;
                p=node;
            }else{
                if(p.val>nums[i]){
                    ListNode node= new ListNode(nums[i],i);
                    node.pre=p;
                    p.next=node;
                    p=node;
                }else{
                    while(p!=head&&p.val<nums[i]){
                        p.pre.next=null;
                        p=p.pre;
                    }
                    ListNode node=new ListNode(nums[i],i);
                    p.next=node;
                    node.pre=p;
                    p=node;
                }
            }
        }
        res[0]=head.next.val;

        //处理后面的情况
        for(int i=k;i<nums.length;i++){
            if(head.next==null){
                ListNode node= new ListNode(nums[i],i);
                node.pre=p;
                p.next=node;
                p=node;
            }else{
                if(p.val>nums[i]){
                    ListNode node= new ListNode(nums[i],i);
                    node.pre=p;
                    p.next=node;
                    p=node;
                }else{
                    while(p!=head&&p.val<nums[i]){
                        p.pre.next=null;
                        p=p.pre;
                    }
                    ListNode node=new ListNode(nums[i],i);
                    p.next=node;
                    node.pre=p;
                    p=node;
                }
            }
            if(i-head.next.index<=k-1){
                res[i-k+1]=head.next.val;
            }else{
                head.next.next.pre=head;
                head.next=head.next.next;
                res[i-k+1]=head.next.val;
            }

        }
        return res;
    }
}
```

## [567. Permutation in String](https://leetcode-cn.com/problems/permutation-in-string/)

借鉴30题用两个hashmap存入数据构造一个滑动窗口但是第一次构造的滑动窗口并不好（不是滑动窗口因为窗口的头尾已经被固定了）进一步优化可以使用ascll码特性用数组代替这个hashmap,字符频率，哈希表，字符数组代替哈希表

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if(s1==null||s1.length()==0)return true;
        if(s2.length()<s1.length())return false;
        Map<Character,Integer> set1=new HashMap<>();
        Map<Character,Integer> set2=new HashMap<>();
        for(int i=0;i<s1.length();i++){
            int v= set1.getOrDefault(s1.charAt(i),0);
            set1.put(s1.charAt(i),v+1);
        }
        int p1=0;
        int p2=s1.length()-1;
        while(p1<s2.length()&&p2<s2.length()){
            for(int i=p1;i<=p2;i++){
                int v1=set1.getOrDefault(s2.charAt(i),0);
                if(v1==0){
                    p1=i+1;
                    p2=p1+s1.length()-1;
                    set2.clear();
                    break;
                }
                int v2=set2.getOrDefault(s2.charAt(i),0);
                if(v2==v1){
                    int j=p1;
                    while(s2.charAt(i)!=s2.charAt(j)){
                        j++;
                    }
                    set2.clear();
                    p1=j+1;
                    p2=p1+s1.length()-1;
                    break;
                }else{
                    v1=set2.getOrDefault(s2.charAt(i),0);
                    set2.put(s2.charAt(i),v1+1);
                    
                }
                if(i==p2){
                    System.out.println(p1);
                    System.out.println(p2);
                    return true;
                }
            }
        }
        return false;
    }
}
```

第二次该进的。。。

```java
public boolean checkInclusion(String s1, String s2) {
        if(s1==null||s1.length()==0)return true;
        if(s2.length()<s1.length())return false;
        Map<Character,Integer> set1=new HashMap<>();
        Map<Character,Integer> set2=new HashMap<>();
        for(int i=0;i<s1.length();i++){
            int v= set1.getOrDefault(s1.charAt(i),0);
            set1.put(s1.charAt(i),v+1);
        }
        int p1=0;
        while(p1+s1.length()<=s2.length()){
            for(int i=p1;i<s2.length();i++){
                char key = s2.charAt(i);
                if(set1.containsKey(key)){
                    int num1=set1.get(key);
                    int num2=set2.getOrDefault(key,0);
                    if(num2==num1){
                        char temp;
                        int j=p1;
                        while((temp=s2.charAt(j))!=key){
                            set2.put(temp,set2.get(temp)-1);
                            j++;
                        }
                        p1=j+1;
                    }else{
                        set2.put(key,num2+1);
                    }
                    if(i-p1+1==s1.length()){
                         return true;
                    }
                }else{
                    p1=i+1;
                    set2.clear();
                    break;
                }
            
            }
        }
        return false;
        
        
        
        
    }
```

下面是leetcode 题解中的滑动窗口法

```java
/**
上一种方法可以优化，如果不是比较每个更新的 s2maps2map 的哈希表的所有元素，而是对应于 s2s2 考虑的每个窗口，我们会跟踪先前哈希表中已经匹配的元素数量当我们向右移动窗口时，只更新匹配元素的数量。

为此，我们维护一个 countcount 变量，该变量存储字符数（26个字母表中的数字），这些字符在 s1s1 中具有相同的出现频率，当前窗口在 s2s2 中。当我们滑动窗口时，如果扣除最后一个元素并添加新元素导致任何字符的新频率匹配，我们将 countcount 递增1.如果不是，我们保持 countcount 完整。但是，如果添加频率相同的字符（添加和删除之前）相同的字符，现在会导致频率不匹配，这会通过递减相同的 countcount 变量来考虑。如果在移动窗口后，countcount 的计算结果为26，则表示所有字符的频率完全匹配。所以，我们立即返回一个True。

Java


*/
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;
        int[] s1map = new int[26];
        int[] s2map = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            s1map[s1.charAt(i) - 'a']++;
            s2map[s2.charAt(i) - 'a']++;
        }//存入短的字符串的频率，以及s2前s1长度的字符串的频率。
        int count = 0;
        for (int i = 0; i < 26; i++)
            if (s1map[i] == s2map[i])
                count++;//为两个字符频率相等字符串的个数。
        for (int i = 0; i < s2.length() - s1.length(); i++) {
            int r = s2.charAt(i + s1.length()) - 'a', l = s2.charAt(i) - 'a';
            if (count == 26)
                return true;
            s2map[r]++;//s2行进第一个数字++
            if (s2map[r] == s1map[r])//r是新引进的字符号如果这个新进去就使得有新的匹配
                count++;
            else if (s2map[r] == s1map[r] + 1)//是原来已有的匹配值破坏
                count--;
            s2map[l]--;
            if (s2map[l] == s1map[l])//同理应用在这个抛弃的这个位置。
                count++;
            else if (s2map[l] == s1map[l] - 1)
                count--;
        }
        return count == 26;
    }
}

```



## [480. 滑动窗口中位数](https://leetcode-cn.com/problems/sliding-window-median/)

数据流中的中位数。

小顶堆大顶堆的变式

```java
public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k +1];
        PriorityQueue<Double> minHeap = new PriorityQueue<>();
        PriorityQueue<Double> maxHeap = new PriorityQueue<>(new Comparator<Double>() {//重定义比较方法
           //maxHeap放入最小的那几个数字
            //minHeap放入最大的那几个数字
            @Override
            public int compare(Double o1, Double o2) {
                if (o2 - o1 > 0.0d){//数字后面加入了一个d表示这是一个double类型的数 Double 类型对减法运算进行了重载。
                    return 1;
                }else if (o2 - o1 < 0.0d){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        int index = 0;
        /**
         * 维持平衡 maxHeap[1....size-1] <= maxHeap[0] <= minHeap[0] <= minHeap[1....size-1]
         * 且保证 Math.abs(maxHeap.size()- minHeap.size()) <= 1
         */
        for (int i =0; i < nums.length; i++){
            minHeap.offer((double) nums[i]);

            //移除窗口外的值
            if (minHeap.size() + maxHeap.size() > k){
                if (minHeap.contains((double) nums[i-k])){
                    minHeap.remove((double)nums[i-k]);
                }else {
                    maxHeap.remove((double)nums[i-k]);
                }
            }
            //维持平衡
            if (minHeap.size() - maxHeap.size() > 1){
                maxHeap.offer(minHeap.poll());
            }
            if (maxHeap.size() > 0 && minHeap.size() > 0 && maxHeap.peek() > minHeap.peek()){
                maxHeap.offer(minHeap.poll());
            }
            if (maxHeap.size() - minHeap.size() > 1){
                minHeap.offer(maxHeap.poll());
            }

            //求窗口中间值
            if (minHeap.size() + maxHeap.size() == k){

                if (minHeap.size() > maxHeap.size()){
                    res[index++] = minHeap.peek();
                }else if (minHeap.size() < maxHeap.size()){
                    res[index++] = maxHeap.peek();
                }else {
                    res[index++] = minHeap.peek()/2.0d + maxHeap.peek()/2.0d;
                }
            }
        }
        return res;
    }

```





