# Big Number in the java 

## 1.BigInteger

BigInteger convert to int

note that （this is the origin note from the Java api）：if this BigInteger is too big to fit in an int, only the low-order 32 bits are returned

```java
int c1 = c.intValue();
```

## 2.BigDecimal

运算时必须给出舍入方式（rounding mode）

## 3.数组

关于数组的几种命名的方法：

1. new int[]{};
2. int [] xx={};

Java中允许定义长度为零的数组

以下这样的语句是合法的：

int[] a= new int[0];

数组拷贝：

Java中的[]被预定义为检查数组边界，而且没有指针运算，不能通过a+1得到数组下一位的数字。

### Api

1. Arrays.toString(type[]);
2. Arrays.copyOfRange(type[]a,int s,int e);
3. Arrays.fill(type[] a,type v);
4. Arrays.equalst(type[]a,type[]b);

### 二维数组

如何快速打印一个二维数组？

使用deepToString 可以快速的访问高纬度的数组。

### 不规则数组

即每个数组的数组并不是相同的。