1. windows 查看端口占用的情况，查看进程的信息，杀死进程的命令？

2. LinkedHashSet 的实现就是继承了hashSet，与普通的HashSet的区别在于它的构造函数中，构造的是一个linkedHashMap

3. LinkedHashMap继承自HashMap,的entry中有一个before指针和after指针，维护了一个双链表。它的构造函数中有一个带三个参数的，其中最后一个Boolean值参数决定是否是按照读取的顺序来排序的。

4. 如何用linkedHashMap构造一个lru缓存？

5. ConcurrentHashMap效率比HashTable 的效率更高，因为它引用了分段锁。使用3个**CAS操作来确保node的一些操作的原子性**，这种方式代替了锁。

6. LinkedList 双向链表

7. Java双向队列

8. deque queue 接口的函数LinkedList 实现了这两个接口

9. Arrays.asList 方法返回的是一个实现了List接口的实现类，它是Arrays中的一个内部类。

10. vector使用了synchronized进行同步

11. ```java
    List<String> list = new ArrayList<>();
    List<String> synList = Collections.synchronizedList(list);
    ```

12. 使用Collections.synchronizedXX()方法能够返回一个并发安全的集合视图，通过观察源码可以发现这个视图通过构造一个Collections 中的一个内部类，通过一系列的synchronized,来实现对集合的并发控制。

13. CopyOnWriteArrayList优缺点

14. HashMap 源码分析：

    1. 拉链法：使用的是拉链法来处理hash冲突
    2. 扩容时的操作

15. WeakHashMap

16. Java I/O的分类，Java I/O 体系

17. Java File 实现了serializable comparable 接口

18. File类可以和Path 类相互转换

19. java I/O 采用了装饰者模式，InputStream 是抽象组件，FileInputStream 是具体组件，FilterInputStream 是抽象修饰器，BufferedInputStream 是具体的修饰者。

20. 编码：

    1. GBK 英文使用1个字节，中文采用两个字节
    2. UTF-8 英文使用1个字节，中文采用3个字节
    3. UTF-16英文使用2个字节，中文采用2个字节

21. 对象操作：

    1. 序列化：就是将一个对象转换成字节序列
       1. 序列化：ObjectOutputStream.writeObject()
       2. 反序列化：ObjectOutputStram.readObject()
    2. 不会对静态变量进行序列化，因为序列化只是保存对象的状态，静态变量属于类的状态。
    3. 序列化的类必须实现Serializble接口
    4. transient 在ArrayList中的应用

22. 网络操作

    server与client创建socket的过程。

23. NIO

    NIO常被称为非阻塞I/O

    弥补了原来的I/O的不足，提供了高速的，面向块的I/O。

    流与块：

    1. 流：一次处理一个字节数据。优点：创建过滤器的方式非常方便。缺点：非常慢。
    2. 块：一次处理一个数据块。优点：快，缺点：不能像流那样进行流式处理。
    3. Java.io.* 已经以NIO为基础重新实现了。

24. 通道

    1. 通道Channel 的概念；
    2. 通道是双向的。
    3. 通道的类型。
    4. 通道的写入写出都要通过缓冲区。

25. 

