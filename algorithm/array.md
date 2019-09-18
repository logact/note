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

