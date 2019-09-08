# 接口，lambada ，内部类

## Interface

1. java 1.8可以在方法中提供简单的方法但是不能访问实例域。

2. Interface 中的方法都是公共的没有私有的（如果定义为私有的那么就会报错），如果没有定义修饰符那么就会自动的变为public.接口中的方法都自动从属与public。（在声明中可以不用提供关键字public）。

3. 在接口中如果定义了一个静态方法那么一定要有方法体。

4. 接口中禁止定义所有的代码块（包括静态代码块和普通代码块）

5. default方法是在java8中引入的关键字，也可称为Virtual extension methods——虚拟扩展方法。是指，在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，这打破了Java之前版本对接口的语法限制），从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码。

6. 在对于int 之间的比较时要注意两个数相减溢出的情况（在用compareTo()方法）

7. 相减的技巧不适用double类型的数据因为精度原因会让两个相差非常相近的数相减的结果为0（四舍五入）；

8. compareTo 方法要符合反对称的要求。

9. 在不同的关系之间实现compareTo（）这样的函数。
   1. 如果只有一种通用的方法能够比较所有的对象则应该在父类中定义compareTo()并将其设置为final
   2. 在比较之前就判断两个是否属于同一种类型。
   
10. 接口中定义的变量是都是共有的 （the private isn't allowed）可以在接口中定义静态变量。

11. java单继承的好处？？？？？？？？

12. 将静态方法添加到接口中是一个不太好的处理。有违将接口作为抽象规范。一般的做法是将静态方法放在与之相关的伴随类（工具类）。

13. 接口演化与默认方法的关系：

    1. 应用场景：

       比如在Java8中给collection接口添加新的方法如果没有将这个方法设置为默认方法那么已经所有以前实现了这个接口的类都要受到影响。而使用默认方法则可以避免这个问题。

14. 解决默认方法冲突

    1. 问题：如果在超类或另一个接口中定义了同样的方法会发生声明情况？
    2. 规则：
       1. 超类优先，如果超类和接口继承了同一个具体的方法那么超类优先。
       2. 接口冲突。如果俩个接口you'yi'ge柄部实现了方法那么会自动继承某一个接口默认方法会让程序员来解决这个二义性。
       3. 如果都是两个接口都没有为共享方法提供默认实现那么就和之前的情况是一样的。
       4. 如果方法签名相同但是返回值不同：？？？然后文本继续指出，如果方法签名具有不同的返回类型，比如`double`和`int`，那么将无法在同一个类中实现两个接口，并且会产生编译时错误。也就是说这样的形式是Java所不允许的。
       5. 不能为让一个默认方法重新定义Object类中的方法。因为类优先的规则下面所有的接口实现的object类中的同名方法是无法超过Object类中方法使用的优先级的。

15. Java回调。

    1. 这里有一个监听器的实例。

16. comparator接口

    在Arrays.sort(array1,比较器)这里要注意我们会想当然的认为这个调用比较器的时候应该调用他的静态方法（因为比较哪两个对象与是哪个比较器的实例无关但是由于这是实现的接口所有的类（通过接口性质的来）所以它的比较方法肯定不能是静态方法）接口中要继承的方法肯定是不能用static所定义的，因为接口方法在所有的所属同一类的对象都是相同的，这就要求这个方法的实现应该要在接口或者超类这个层级实现。

17. 对象与克隆

    1. 从object 对象继承的clone()是一个protected方法,只有这个类的方法，和所属这个类的实例可以访问这个对象，Java对象能够访问父类的protected方法（如果父类没有覆盖祖先类的方法那么这个方法就会被视为子类的方法而不是父类的方法这个子类是不能访问这个方法的）但是如果祖先类中如果显示的定义了一个受保护的方法那么这个子类通过这个祖先类是可以访问这个对象的受保护方法。

    2. 利用子类可以提升父类方法访问权限的特性，虽然存在超类优先但是通过这种扩展访问权限的方法就可以破除（父类的方法不能被找到那么就只能在子类中找了）所以可以直接给让它实现cloneable接口。
    3. 对于protected的总结在一个类中如果定义了一个protected那么后续继承的类都可以访问它的这个方法，但是如果在这个继承链中间有类没有覆盖这个方法，那么继承它的那个类将不能访问这个类的protected方法，这就像把这个继承访问的链条打断了（如果这个继承过程中没有一直将这个方法覆盖过去）(只能访问父类的protected方法),但是能够用super的方式调用父类的所有方法（包括父类从祖先类上继承的方法）。父类祖先类可以访问子孙类从父类中继承过去的protected方法,protected包可见是指与第一定义这个类的方法的包相同。

18. 如果类没有实现cloneabel接口那么就会出.CloneNotSupportedException

19. 所有的数组类型都有一个clone方法。

## lambda表达式

1. 目的：
   1. 简化传递代码块的方式。
   2. 使用与要传递某一个方法。
   
2. 参数形式

3. 函数式接口
   1. 由于接口中可以定义非抽象类所以可以将父类中的抽象方法变为非抽象的方法。
   2. 只能将lambda表达式赋值给一个函数是接口（只有一个抽象方法的接口）
   
4. 方法引用
   1. 传递已有的方法
   2. 格式 :: 方法名
   3. 有三种形式的方法：
      1. object::instanceMethod(); System.out::prinltn;
      2. Class::staticMethod(); Math::pow;
      3. Class::instanceMethod();String::compareToIgnoreCase;
   4. 方法引用不能独立的存在，总是会转换为函数式接口实例。
   5. 使用this::,super::也是合法的。
   
5. 构造器引用
   1. person::new
   2. 可以用数组类型来建立构造器的引用。int[]::new 等价于x->new int[x]
   3. 一点点流的操作（）；
   
6. 变量作用域
   2. lambda表达式有三个部分：
      1. 一个代码块
      2. 参数
      3. 自由变量，非参数而且不在代码块中定义的变量。
   3. 对于lambda表达式的自由变量的问题。
      1. 他能够存储这个变量的值、
      2. 不能在他之前改变这个变量的值
      3. 它能读取这个方法的参数或者final变量。
      4. 在表达式中也不允许改变变量的值。（造成线程不安全）以后再议。。。。。。。。。。
      5. lambda表达式中捕获的变量必须是最终变量。（这个变量在初始化之后就不会在为它赋新的值）
      6. lambda表达式中声明与一个局部变量同名参数或局部变量是不合法的。
      7. 在lambda表达式中引用this和在这个类其它的位置引用this是一样的。
      8. 它的作用域嵌套在它所属的方法内。
   
7. 使用lambda表达式的原因：

   1. 使用它的重点是延迟执行。
   2. 在一个单独的线程中运行代码
   3. 多次运行代码
   4. 发生某种情况时执行代码
   5. 在算法适当的时候执行代码（如在排序算法运行时的比较操作）

8. 常用函数式接口

   1. 如果自己设计函数式接口那么就可以用@FunctionalInterface来修饰方法。）

   2. 在接口中只设计一个方法最好这样做，这样如果在后续如果继续添加抽象方法那么编译器会保存，而且在javadoc中会指明这个接口是一个函数式接口。

   3. 对于这句的理解

      ```java
      Arrays.sort(people,Comparator.comparing(Person::getLastname).thenComparing(Person::getfirstName))
      ```

      首先sort 的第二参数需要提供一个构造器（一个Comparator 对象）而Comparator.comparing 的一个静态方法提供了一种接受函数接口的简易构建简易构造器的方法，它的参数是keyExtractor - the function used to extract the Comparable sort key，而thenComparing 接受一个比较器，thenComparing （）是一种java中的流式设计，sort函数通过Compartor.compare(fds,fd)时就会传入两个Person类。接下来就是这句会利用提供的方法将这个person的比较关键字提取出来。

      ```java
       return var1.compare(var0.apply(var2x), var0.apply(var3));//var0就是放进去的方法引用
      ```

      ```java
      Function<? super T, ? extends U> var0,
      ```

      而对于一个接受的thenComparing 方法执行的过程是一样的道理

      ```java
      default <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> var1) {    return this.thenComparing(comparing(var1));}
      ```

      而对于接下来的接受compartor的thenComparing 方法执行的过程又是不一样的因为它接受的是一个compartor他就不能够从一个对象中抽调出关键字。

      ```java
       return var4 != 0 ? var4 : var1.compare(var2x, var3);//直接将这个方法返回的值进行比比较
      ```

      

      可以看到thenComparing方法的原码

      ```java
      default Comparator<T> thenComparing(Comparator<? super T> var1) {
              Objects.requireNonNull(var1);
              return (Comparator)((Serializable)((var2x, var3) -> {
                  int var4 = this.compare(var2x, var3);
                  return var4 != 0 ? var4 : var1.compare(var2x, var3);
              }));//如果当前规则比较不出大小那么就是要用这个then比较器来比较。
          }
      ```

      当然最后Arrays.sort使用的方法就是这个流最后得到的那个比较器。从这里也可以看出 函数引用和lambda 表达式还是有一些不同的首先不用向lambda一样（var 1，var 2）一样显示显示的声明函数，调用它的方法可以根据它的参数列表形式填入参数

      ```java
      Function<? super T, ? extends U> var0
      var0.apply(var2x)
      ```

      使用下面的方式能够避免自动装箱（应为在默认的comparing方法中是调用类型自己的equal函数）如果是int等基本类型这个就可能会出现自动装箱。

      ```java
      static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> var0) {    Objects.requireNonNull(var0);    return (Comparator)((Serializable)((var1x, var2x) -> {        return ((Comparable)var0.apply(var1x)).compareTo(var0.apply(var2x));    }));}
      ```

      ```java
      static <T> Comparator<T> comparingInt(ToIntFunction<? super T> var0) {
              Objects.requireNonNull(var0);
              return (Comparator)((Serializable)((var1x, var2x) -> {
                  return Integer.compare(var0.applyAsInt(var1x), var0.applyAsInt(var2x));
              }));
          }
      ```

   4. 如果要允许空值（可以提供一个空的适配器）（java适配器）         

      ```java
      Arrays.sort(peopel,comparing(Person::getMiddleName,nullFirst(nauturalOrder)))
          //这一句现在也不能理解
          
      ```

      ```java
      static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> var0, Comparator<? super U> var1) {    Objects.requireNonNull(var0);    Objects.requireNonNull(var1);    return (Comparator)((Serializable)((var2x, var3) -> {        return var1.compare(var0.apply(var2x), var0.apply(var3));    }));}//这将会调用这个var1的比较规则而使用这个方法提供的关键字抽取方法。
      ```

9. 为什么函数式接口Comparator（使用了@FunctionalInterface）中有两个抽象方法呢？

   ```java
   public @interface FunctionalInterface 官方文档这样说：If an interface declares an abstract method overriding one of the public methods of java.lang.Object, that also does not count toward the interface's abstract method count  since any implementation of the interface will have an implementation from  java.lang.Object or elsewhere.
   如果接口声明了一个抽象方法覆盖的公共方法之一java.lang.Object ，也不会向接口的抽象方法计数统计以来的接口的任何实施都会有一个实现从java.lang.Object或其他地方。
   如果接口声明了一个覆盖java.lang.Object的全局方法之一的抽象方法，那么它不会计入接口的抽象方法数量中，a因为接口的任何实现都将具有java.lang.Object或其他地方的实现。因为任何一个类都继承自Object这个类所以任意一个类都最少有这个Object类public方法的实现。但是有个问题它为什么要这么做呢？
   ```

10. ```java
    泛型与lambda的类型推定十分重要
    PriorityQueue p;p=new PriorityQueue<Integer>(( x, y)->y-x);//泛型与lambda的类型推定十分重要
    ```

## 内部类

### 使用内部类的原因

1. 内部类方法可以访问该类定义所在的作用域中的数据，包括私有域。
2. 内部类可以对同一个包中的其他类隐藏起来。
3. 当想定义一个回调函数且不想编写大量代码时，使用匿名内部类比较便捷



### 使用内部类访问对象的状态：

1. 内部类中总有一个隐式的引用，它指向了创建它的外部类对象。

2. 对于静态访问外部类的方式

   ```java
   public class Class1 {
       private String private_string;
   
       public Class1(String private_string) {
           this.private_string = private_string;
       }
   
       public String getPrivate_string() {
           return private_string;
       }
       public void useTheInnerClass(){
           new Class2().outPrivate_string();
       }
       public static void UseTheInnerClassByStatic(){
       }
   
       class Class2{//这不是静态的所以这不能被静态的方法使用,就像非静态的方法一样的权限和使用不能被静态方法使用
           public void outPrivate_string(){
               System.out.println(private_string);
               private_string="fds";
           }
       }
   
       public static void main(String[] args) {
           Class1 lisa = new Class1("lisa");
           System.out.println(lisa.getPrivate_string());
           lisa.useTheInnerClass();
           System.out.println(lisa.getPrivate_string());
   
       }
   }
   ```

   对于调用内部类和调用非静态方法是一样的。也可以将它与内部变量相比较

3. 执行内部类方法的过程

   1. 再调用useTheInnerClass（）方法时编译器会及那个this引用传递给当前的内部类构造器

      就如偷偷的调用了一个这样的构造方法

      ```java
      InnerClass innerClass =new InnerClass(this);
      //这个时候就将当前类（this）传入内部类这样就可以让内部类访问外部类的域了。
      ```

4. 内部类可以署名为私有的，这样只有在它的外部类才可以构造这个类。只有内部类才可以声明为私有的，常规类最多只能够声明为包可见。

## 内部类的特殊语法规则

1. OutClassName.this 表示外围引用

2. outObject.new InnerClass(construction paramter);//指定创建哪一个类的静态变量

3. 内部类不允许有静态方法。

4. 内部类中的静态域必须是final。

5. 通过OuterClass.InnerClaas可以在外部类的作用域引用内部类对象。

6. 内部类中只能够定义静态常量（不能是引用）如下是错误的

   ```java
   //允许
   private static final int nextId = 1;
   //允许  虽然s不是基本类型，但由于java的字符串是不可变的所以这是可以的     
   private static final String s="df";
   //不允许
            private static final Person person=new Person("lia");
   
   ```

7. 静态域不允许定义在方法中,但是可以定义final变量



## 内部类的安全问题

内部类是一种编译现象，与虚拟机无关。编译器会将内部类翻译成用$分割外部类名与内部类名的常规文件，而虚拟机是不知道的。

可以这种方式来获得一个内部类

```java
Class1 class1 =new Class1("lisa");Class2 class21= class1.new Class2();Class innerClassByInstance=class21.getClass();System.out.println(innerClassByInstance.getName());try {    Class innerClassByName= Class.forName("com.logact.TestLambda.InnerClass.Class1$Class2");    System.out.println(innerClassByName);} catch (ClassNotFoundException e) {    e.printStackTrace();}
```



使用javap java编译器在编译阶段重写了内部类的构造函数，生成了一个附加的实例域（this$0)这个名字是由编译器合成的。在自己编写的代码中不能够应用它但是可以通过反射的方法来找出这个量：

```java
Class1 class1 =new Class1("lisa");Class2 class21= class1.new Class2();Class innerClassByInstance=class21.getClass();System.out.println(innerClassByInstance.getName());Field[] fields = innerClassByInstance.getDeclaredFields();System.out.println(fields.length);for (Field field : fields) {    System.out.println(field.getName());}
```

为了能够让内部类能够访问外部私有属性，所以编译器会在外部类中添加一个静态方法的获取私有属性。这个类中也是加了$的是一个非法的方法名，但是熟悉文件结构的用户会利用16进制编辑器创建一个用虚拟机指令调用那个方法的类文件。隐秘的访问方法需要包可见性。

由于在虚拟机中不存在 有私有类所以编译器会利用私有构造器生成一个包可见的类。

内部类可以声明为public默认情况下这个内部类只有包可见性，一个内部类的私有方法可以被外部类调用。不能够被其它的类调用。

一个java文件中的类最多只能有一个public类（可以没有），可以有其它的默认类在没有与Java文件名相同的public类，这个默认类甚至还可以和Java文件名同名（默认类只有包可见性）

### 局部内部类

如果某个类的只在某个方法中使用，那么就可以让它变为局部内部类（在方法体内定义），局部内部类不能够用public或者private修饰符修饰。它的作用域就是定在这个方法体内。对外部世界完全隐藏。在编译后也会生成一个有$拼接的类名。它还有一个优点就是它可以访问局部变量。

在局部内部类中获得局部变量的过程

由于不能通过像常规内部类一样通过编译器构造的方法来获得类中变量的值（局部变量的值是根据方法环境不停的在改变）因此只能够在方法创建局部内部类的时候由编译器将局部变量传递（传递的是这个变量的副本）过来给内部类的构造器，并储存在编译器为这个内部类所创建的一个变量中。

### 匿名内部类

现在最好使用lambda表达式

获得一个匿名的链表（双括号初始化技巧）

```java
f(new ArrayList<String>(){{add("logact");add("lisa")}});
第一层括号创建了一个ArrayList<String> 的一个匿名子类，它的类中给了一个构造代码块。
```



在静态方法中获取当前类的类

```java
new Object(){}.getClass().getEnclosingClass();
```

### 静态内部类

1. 产生的背景（计算一个数组中的最大最小值）因为要返回两个值，所以可以用一个类来处理这个过程，并提供两个方法一个返回最大值，一个返回最小值。
2. 问题：为了避免与项目中的其它类重名，所以使用一个内部类，但这个类不需要引用任何外部类的任何的变量所以他可以设置为一个静态类。局部内部类不可以被static修饰。但是可以被final修饰，也可以被继承。

## 代理

### 使用代理的场景

有一个表示接口的Class对象，它的确切类型在编译时无法知道。想要构造一个这样一个实现这样接口的类？

解决办法：

1. 自动生成这样的一个类并把代码放在一个文件下面。但这样比较慢。

2. 使用代理机制。代理类可以在运行时创建全新的类。这样代理类能够实现指定的接口。它具有下列的方法：

   1. 指定接口所需要的全部方法。
   2. Object类中的全部方法

   要在运行时提供一个调用处理器（invocationHandler）:一个实现了Invocationhandler接口的类对象。这个接口只有一个方法。

   ```java
   Invacatonhandler:
   Object invoke(Object proxy,Method method,Object[]args)如何创建一个代理对象：
   ```



### 创建一个代理对象

1. 使用proxy类的 newProxyInstance()方法；

   1. 三个参数

      ```java
      1.class loader .java 安全模型的一部分，对于系统类和从因特网上下载下来的欸，可以使用不同的类加载器。第卷二第九章中讨论累加载器。
      2.一个Class对象数组。
      3.一个调用处理器
      ```

2. 如何定义一个处理器（这和实际场景有关）：

3. 使用代理的实际场景

   1. 路由对远程服务器的调用。
   2. 在程序运行过程期间，将用户接口与事件联系起来。
   3. 为了调试，方便方法追踪。

4. 使用代理和调用处理器跟踪方法调用：

   ```java
   package com.logact.testProxy;
   
   import java.lang.reflect.InvocationHandler;
   import java.lang.reflect.Method;
   import java.lang.reflect.Proxy;
   import java.util.Arrays;
   import java.util.Random;
   
   /**
    * @author: logact
    * @date: Created in 2019/9/7 10:28
    * @description:
    * 代理类测试用于监听在二分排序中的行为
    */
   public class ProxyTest {
       /**
        *
        * @param args
        */
       public static void main(String[] args) {
           Object[] elements = new Object[1000];
           for(int i=0;i<elements.length;i++){
               TraceHandler traceHandler=new TraceHandler(i);
               //注意这里proxy产生的代理对象不是Proxy 而是Object
               Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, traceHandler);
               elements[i]=proxy;
           }
           Integer key = new Random().nextInt(elements.length)+1;
           int res = Arrays.binarySearch(elements, key);
           if(res>0){
               System.out.println(res);
               System.out.println(elements[res]);
           }
   
       }
   }
   class TraceHandler implements InvocationHandler{
       private Object target;
       public TraceHandler(Object t){
           target=t;
   
       }
       @Override
       public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
           System.out.print(target);
           System.out.print("." + method.getName() + "(");
           if(objects!=null&&objects.length!=0){
               int i=0;
               for (Object object : objects) {
                   System.out.print(object);
                   if(i!=objects.length-1){
                       System.out.print(",");
                   }
                   i++;
               }
           }
           System.out.println(")");
           return method.invoke(target, objects);
       }
   }
   
   ```

   所有的代理类都扩展与proxy类。一个代理类只有一个实例域-调用处理器。它装载了实际对象。

   ？？

   所有的代理类都覆盖了Object类中的方法to'Sting,equal hashcode.

   使用代理的条件

   1. 需要一个实现了所要接口的类
   2. 一个处理器（调用实际用例的方法时就是调用这个方法）
   3. 将这个处理器传递给代理类然后它实现的所有接口的Class对象数组（这是让外界知道它已经实现了找个接口）

   在显示调用的时候

   要先把代理对象强转为一个要实现接口的对象

   代理机制有底层机制支持接受某个方法后会解析（诸如这样的形式.f()）然后调用revoke(处理器).

   ```java
   /**
   Arrays.binarySearch0由于这个方法接受的是Object对象的参数
   所以他在比较的时候将这个object对象强转为一个comparable对象所以我们传进去的proxy（Object对象）可以使用compareto
   */
    private static int binarySearch0(Object[] var0, int var1, int var2, Object var3) {
           int var4 = var1;
           int var5 = var2 - 1;
   
           while(var4 <= var5) {
               int var6 = var4 + var5 >>> 1;
               Comparable var7 = (Comparable)var0[var6];
               int var8 = var7.compareTo(var3);
               if (var8 < 0) {
                   var4 = var6 + 1;
               } else {
                   if (var8 <= 0) {
                       return var6;
                   }
   
                   var5 = var6 - 1;
               }
           }
   
           return -(var4 + 1);
       }
   ```

   





