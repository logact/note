# ReentrantLock



## 1.unfairLock

### lock过程：

```java
final void lock() {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
            } else {
                this.acquire(1);
            }

        }
1.直接用cas操作设置state 来尝试加锁;state==0表示锁是空闲的，如果设置成功就成功cas方法返回true，方法直接退出获得了锁执行临界区的方法。否则跳到2.
2.获取锁失败。执行acquire（属于父类aqs的方法）
public final void acquire(int var1) {
        if (!this.tryAcquire(var1) && this.acquireQueued(this.addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), var1)) {
            selfInterrupt();
        }

    }
3.执行this.tryAcquire(int var)(属于子类的方法)
4.nonfairTryAcquire() (非公平锁的tryAcquire()方法)
    final boolean nonfairTryAcquire(int var1) {
            Thread var2 = Thread.currentThread();
            int var3 = this.getState();
            if (var3 == 0) {
                if (this.compareAndSetState(0, var1)) {
                    this.setExclusiveOwnerThread(var2);
                    return true;
                }
            } else if (var2 == this.getExclusiveOwnerThread()) {
                int var4 = var3 + var1;
                if (var4 < 0) {
                    throw new Error("Maximum lock count exceeded");
                }

                this.setState(var4);
                return true;
            }

            return false;
        }
这个方法中尝试获取一次锁（如果观察到锁的状态是空闲的state==0）如果成功方法返回true。aqs 的acquire退出开始执行临界区代码。如果当前锁的状态不是空闲的。那么就判断当前线程是否是持有的锁的线程this.getExclusiveOwnerThread()获得持有锁的线程，这个方法返回的持有线程是再加锁过程中，通过一个set方法赋值的。如果是就将acquire方法中传如的int 1，加到标志位上（如果线程的标志位加上后大于int最大值那么就会报错所重入这个锁的线程过多）（这个就是非公平锁重入的过程）
如果当前线程并不是这个锁持有的线程那么就会返回false。调用tryAcquire方法的acquire方法就会执行Boolean表达式的下个子项。
if (!this.tryAcquire(var1) && this.acquireQueued(this.addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), var1)) {
            selfInterrupt();
        }
        
5.开始进行入队的操作（在获取不到锁的情况下（在执行到这一步时已经总共尝试了两次获取锁的操作（最多两次，最少一次）））
6.入队的过程
触发aqs的addWaiter(Node node)方法。传入的是一个叫EXCLUESIVE的final修饰的node，这个对象的值为null
7.执行addWaiter方法。
private AbstractQueuedSynchronizer.Node addWaiter(AbstractQueuedSynchronizer.Node var1) {
        AbstractQueuedSynchronizer.Node var2 = new AbstractQueuedSynchronizer.Node(Thread.currentThread(), var1);
        AbstractQueuedSynchronizer.Node var3 = this.tail;
        if (var3 != null) {
            var2.prev = var3;
            if (this.compareAndSetTail(var3, var2)) {
                var3.next = var2;
                return var2;
            }
        }

        this.enq(var2);
        return var2;
    }
   首先新建一个以当前线程为基础的node var2每个这样的节点的next是都是一个为空的节点（final EXCLUSIVE）
  然后判断这个队尾是否为空（队列是否被初始化）如果队尾（tail 节点）不为空，那么就会将当前var2插入到队列中。（这一步用一次cas操作在设置队尾这一步的这一个步骤来保证这个队列操作的线程安全，如果设置失败了或者tail==null。那么就会触发enq(Node var1). 传入一个var2.
enq(NOde var2):设置队尾这一步失败，或者tail==null。
private AbstractQueuedSynchronizer.Node enq(AbstractQueuedSynchronizer.Node var1) {
        while(true) {
            AbstractQueuedSynchronizer.Node var2 = this.tail;
            if (var2 == null) {
                if (this.compareAndSetHead(new AbstractQueuedSynchronizer.Node())) {
                    this.tail = this.head;
                }
            } else {
                var1.prev = var2;
                if (this.compareAndSetTail(var2, var1)) {
                    var2.next = var1;
                    return var2;
                }
            }
        
    }
    
做成一个死循环（直到将队尾设置成功，也就是将当前线程插入进队列成功）。
循环中：
	首先判断这个tail是否为空，
		若为空：
			用cas操作设置队头为一个Thread=null的Node。并将tail也指			  向这个节点。
			
		若不为空：
			就用cas操作将tail设置为当前线程的节点为tail（尝试将当前线程入队列）。
enq方法结束。
开始执行acquireQueued(Node node,int 1);
final boolean acquireQueued(AbstractQueuedSynchronizer.Node var1, int var2) {
        boolean var3 = true;

        try {
            boolean var4 = false;

            while(true) {
                AbstractQueuedSynchronizer.Node var5 = var1.predecessor();
                if (var5 == this.head && this.tryAcquire(var2)) {
                    this.setHead(var1);//将阻塞队列的head设置为当前线程所在的node
                    var5.next = null;//将原来的对头的下一个节点置为空，将对象设置为游离态便于gc回收。
                    var3 = false;
                    boolean var6 = var4;
                    return var6;//第一次返回true/
                }

                if (shouldParkAfterFailedAcquire(var5, var1) && this.parkAndCheckInterrupt()) {
                    var4 = true;
                }
            }
        } finally {
            if (var3) {
                this.cancelAcquire(var1);
            }

        }
    }
    这个方法中如队之后查看当前线程节点的前一个节点是否是头节点如果是头节点说明前面没有等待要执行的线程那么就执行一次tryAcquire方法再尝试获取一次锁。如果在这个时候获取锁成功了，返回true或者false。true就执行一个selfInterrupt方法。
```



