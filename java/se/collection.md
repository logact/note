# collection

java集合的操作



## Java集合框架

### 将集合接口与实现分离

在研究api文档时其中的一些abstract类是位类库实现者所准备的（如果像要实现自己的集合类那么用这个抽象类会比实现那些所有的接口中的方法要好很多。）

### collection接口

Collection 接口有两个基本方法。

```java
public interface Collection<E>{
    boolean add(E elemnet);//集合中不允许有重复的元素如果插入重复的元素就会返回false
    Iterator<E> iterator();//返回一个实现了iterator接口的对象。
    ...//还有方法在之后介绍。
}
```



### 迭代器

Iterator 接口的4个方法

```java
public interface Iterator<E> {
    E next();
    boolean hasnext();
    void remove();
    default void forEachRemaining(Consume<? super E> action);//这里就是用了向上的限定的泛型。。。
}

```

foreache支持任何实现了iterator接口的类。

使用forEachRamaing方法

```java
iterator.forEachRemaing(element-> do somthing with element );
//为这个方法传递一个λ表达式。
```

iterator 的remove()方法：

删除上次调用next方法时 返回的元素。

```java
//必须在next()方法之后删除，必须用next方法越过这个数才能用remove删除
it.remove();
it.remove();//wrong
//
it.next();
it.remove();
```



### 泛型实用方法

Collection 的实用方法

```java
int size();
boolean isEmpty();
boolean contains();
boolean containsAll(Colleaction<?> c);//注意这里使用的不限定的通配符，因为这个Collection指示他可以接受任意参数类型的集合（不必和接口的参数类型相同）
boolean equals(Object other);
boolean remove(Object obj);//删除 与obj相同的对象。
boolean removeIf();
boolean clear(); //删除集合的所有元素。
Object[] toArray();//返回集合的对象数组；
<T> T[] toArray(T[] arrayToFill);//返回这个集合的对象数组，如果arrayToFill足够大，就将集合中的元素全部填入其中，剩余空间用null补齐。否则分配一个数组，成员类型与arrayToFill的成员类型相同，长度与集合元素相同。（这返回的是一个安全的数组，记住了这个泛型类型）

```



### 集合框架中的接口

## 具体集合

### 链表

### 数组列表

### 散列表

### 树集

### 队列与双端队列

### 优先级队列

## 映射

### 基本映射操作

### 更新映射操作

### 映射视图

### 弱散列映射

### 链接散列与映射

### 枚举集与映射

### 标识散列映射

## 视图与包装器

### 轻量级集合包装器

### 子范围

### 不可修改的试图

### 同步视图

### 受查视图

### 关于可选操作的说明

## 算法

### 排序与混排

### 二分查找

### 简单算法

### 批操作

### 集合与数组转换

### 编写自己的算法

## 遗留的集合 

### Hashtable类

### 枚举

### 属性映射

### 栈

### 位集

*XMind: ZEN - Trial Version*