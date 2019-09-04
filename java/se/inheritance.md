# inheritance

## inheritance

1. ## 子类覆盖父类方法

   1. 子类不能直接读取父类的私有属性
   2. 如果需要获取到父类的私有属性那么就要使用super.getX()来获取相应的父类私有域。
   3. 父类与子类之间可以有相同的变量名，用super关键字加以区分。在父类中定义方法，变量意味着可以由父类声明的实例来读取数值。

2. ## 父类与子类之间的构造器：

   1. 子类在构造器必须要调用父类构造器（且必须第一行调用）
   2. 子类必须能够调用父类的构造器
   3. 父类如果有带参构造器，那么子类必须要显式重写它（第一句也必须使用父类构造器，但是这个构造器可以是子类能够访问父类的任意构造器）

3. ## 父类与子类之间的方法调用；

   1. 将一个子类的实例赋给一个父类变量时（例如 Son  s=new Son()  Parent p= s;）那么在调用父类与子类同签名的方法时候，就会调用子类的方法。（虚拟机知道对象实际引用的类型所以能够调用正确的方法）但是在这种情况下编译器会将这个son类看成一个parent类所以如果调用子类特有的方法就会出错。

4. ## 多态（polymorphism）

   1. 一个对象变量可以指示多种实际类型的现象叫做多态

   2. 在运行时能够自动选择调用哪个方法称为动态绑定

   3. 数组在第一次定义的时候会牢牢记住它的类型也就是如以下的操作是非法的

   4. ```java
      boss[] bosses=new boss[10];employee[] employees=bosses;employees[1]=new employee("sdf");System.out.println("在中存入boss 中填入employee");bosses[1].oder();
      ```

5. ## 关于方法重载与重载解析

   1. 在方法解析的过程中能够接受类型转换（合法转换）如子类可以匹配父类，int可以转为double
   2. 方法签名等于方法参数加上参数名。返回值不会计入参数名。
   3. 如果子类方法覆盖父类方法时候改变了方法签名那么编译器就会报错。允许将返回类型覆盖为原类型的子类。
   4. 子类方法不能低于父类方法的可见性。如果父类方法是public那么子类方法一定要是public，如果子类方法遗漏了public修饰符那么就会造成编译器将其解释为更加严格的访问权限。因为这个方法覆盖后对外的形式还是不变的。如果子类的访问权限比父类的高可以理解为，这是这个子类的新的特性。但是不能低因为子类需要具有父类的所有的特性。super.f（）这种方法调用仅仅是提供给子类自己的方法调用的。不应该被外界所调用,也就是说外界无法跳过子类这层直接区访问父类的方法。
   5. 对于方法如果参数类型不同（即使是原参数的子类）那么这个方法是与原方法无关的。不算做重载。

6. ## 静态绑定与动态绑定：

   1. 静态绑定
      1. 由private，final，static修饰的方法。
   2. 动态绑定
      1. 程序运行的时候：会调用最符合函数签名的方法。从子类沿着父类链寻找最适合的。
      2. 动态绑定的运行过程。
         1. 首先创造子类与父类的方法表。
         2. 虚拟机提取出实例的实际类型的参数表。
         3. 搜索定义的方法签名的类，虚拟机获知应该调用哪一个方法。
         4. 虚拟机调用该方法

7. ## 组织继承

   1. 如何阻止一个类被继承？

      1. 使用final类修饰这个类。
      2. 如果父类方法被final所修饰那么这个方法就会被禁止被覆盖。

      

   2. 组织被继承的场景？

      1. 禁止子类对某些事物进行操作。
      2. 禁止子类对某些事物进行重新定义。
      3. 将方法定义为final能够减少使用动态绑定带来的开销。
      4. 如果一个方法被定义为final并且很短那么他就会被编译器所优化，变为内联方法（inline）。相比较而言，动态绑定的方法就不能进行这种优化（因为编译器并不知道这个方法应该具体执行哪一个）
      5. java 虚拟机种的即时编译器能够检查出某个方法是否真的被覆盖了，如果这个方法没有被覆盖而且很简短被频繁调用，那么虚拟机就会将它设置为内联方法，如果虚拟机在这个过程后加载了另一个子类并且这个子类覆盖了这个方法那么虚拟机就会取消内联，这个过程很慢但是很少发生。

8. ## 强制类型转换

   1. 使用instance of 在进行向下转型时检测类型转换是否合法。
   2. if x=null then x instance of 就会输出法false。

9. ## 抽象类

10. ## Java可见性

    1. protected
       1. 表明子类收到了父类的信任可以访问这个类的相关方法和域。
       2. 对于将某个类中的某个域设置为protected这样由于可以由这个类派生出许多类，那么这些类都能访问proected域那么当这个类发生了修改的时候那么就会造成要通知这些所有的类，这样就破坏了数据封装 的原则
       3. 相比之下使用protected修饰方法更加由意义。（例如clone（）这个方法）
    2. Java可见性修饰符：
       1. private
       2. public
       3. protected（子类和包可见）
       4. 默认（不需要修饰符）（包可见）

11. ## Object 

    1. equal()方法
       1. 在object中默认为判断为两个对象是否是同一个引用
       2. Java 语言规范规定equal方法应该具有的下面特性：
          1. 自反性
          2. 对称性
          3. 传递性
          4. 一致性
          5. 任意非空对象调用x.equals(null)应该返回false
       3. 对于编写equal方法的一些建议：
    2. getClass()方法

12. ## hashcode

    1. String对象采用的声明算法计算散列值？
    2. 每个对象都有一个默认的散列码。（这个值为对象的存储地址）
    3. 字符串的散列码是由字符内容导出来的所以不同应用的散列可能相同。
    4. 而StringBuffer的使用的是object默认的散列函数。
    5. hashcode方法应该返回一个整数值，可以是负数。
    6. 使用Double.hashCode()来计算double类型数的散列码，使用objects.hashCode()来null安全的计算对象的散列码（如果对象为null就会输出0）
    7. Objects(name,salary,....)
    8. 如果重新定义equals()方法那么就要重新定义hashCode()方法，以便用户可以将对象插入到散列表中。？

13. toString()

14. 泛型数组列表（ArrayList<>）

    1. 数组列表管理一个内部数组。当数组的容量不够时就会自动扩充数组的大小。
    2. timToSize()
       1. 在确定数组大小不变的时候将存储区域大小调整为当前数组的大小。垃圾回收器会回收多余的内存。
    3. set() ,add()方法
       1.  set()方法必须在已经加入值的位置添加值。
    4. list.toArray()
    5. list.remove(),staff.add(n,e);a
    
15. 对象包装器与自动装箱

    1. 对象包装器的值是不可变的，如果设定了某个值那么这个值就不能在被改变（内部属性为final也没有提供过设置器）

    2. 对象是final类禁止被继承。

    3. 由于被封装在类中，ArrayList<Integer> 的效率远比int[] 低，所以它应当只用来构造小型集合。

    4. 自动装箱与自动拆箱

    5. 对于对象包装器：考察以下的代码：

       ```java
       Integer a=1000;
       Integer b=1000;
       if(a==b){
           
       }
       //a==b通常不会成立但是有的时候也可能会成立（java将经常出现的值包装在一个对象中）
       
       ```

    6. 自动装箱规范要求Boolean,byte，char<=127,介于-128~127之间的收人头和int被包装到固定的对象中。

    7. 如果在条件表达式中混用不同的装箱类，那么就会自动向精度高的哪一个数提供转型。

       ```java
       Integer n=1;
       Double x=2.0;
       true?n:x;//由于编译器在检查类型时不能确定运算结果所以直接按最高精度的型
       ```

    8. 装箱与拆箱是由编译器认可的，而不是虚拟机。编译器在生成类的字节码是，插入必要的方法调用。虚拟机只是执行这些字节码。自动装箱和自动拆箱是编译器的动作与java虚拟机无关。

    9. IntHolder :可以通过它来改变访问存储在其中的值。

    10. 在方法的调用中（形参的传递中会自动实现拆装箱）

    11. 在遍历如一个list中也能自动实现拆装箱。

    12. 综上自动装箱的行为与合法的类型转换一致。

16. 可变参数

    1. since java se 5.0

    2. 可变参数的定义方法

       1. ```java
          public PrintStream printf(String fmt,object...args){
              return format(fmt,args);
          }
          
          ```

17. 枚举类

    1. 枚举变量中定义的那些值都是实例

       ```java
       
               Size a=Size.LARGE;//获得得的是一个常量所以枚举变量相比较的时候不必使用equals（）可以直接使用==
               a.setParameter("hello");
               System.out.println("a.getParameter()"+a.getParameter());
               Size b=Size.LARGE;
               System.out.println("b.getParameter()"+b.getParameter());
               System.out.println("b==a"+(b==a));
       ```

       b.所有的枚举类型都继承字enum这个类

       c.所有的枚举类型的构造器都强制为private(如果没有指定的话在一般的类中时default（包可见）但是在枚举类型中就会自动变成private)，由于枚举类型的所有构造器都是私有的所以它也不能被继承。

18. 父类子类构造时代码执行的顺序：

    1. 父类静态代码块（只要掉用了该类的静态方法就会被触发（第一次使用这个类就会触发，但是如果只是声明则不会））
    2. 子类静态代码块。
    3. 在执行Son son = new Son();时会先执行父类的代码块，在执行父类的构造方法主体。
    4. 执行子类代码块和子类构造方法主体。
    5. 无论是静态代码块还是代码块都只会被触发一次，普通代码块会在每次构造新的对象时触发。

## reflection

1. 能够分析类能力的程序称为反射。

2. 反射机制可以用来：

   ```java
   1.在运行时分析类的能力
   2.在运行时查看对象
   3.实现通用的数组操作代码
   4.利用Method对象，例如c中的函数指针
   ```

3. 主要使用人员是工具构造者。

4. 程序运行时系统始终为所有对象维护一个运行时的类型标识。虚拟机利用它来选择相应的方法来执行。

5. 而Class 类就能够访问这些信息。

6. java core 中介绍的一种另类的启动方法

   ```java
   1.在main函数中避免一切需要加载的类。
   2.然后通过Class.forName()手动的加载类。
   ```

7. 获得Class 对象的几种方法。

   1. 通过实例的getClass();

   2. 通过全类名Class.forName("className");

   3. 直接通过x.class(x可以是基本类型，数组)

      ```java
      size.getClass().getName():com.logact.Size
      main.getClass().getName():com.logact.Main
      s.getClass().getName():java.lang.String
      i.getClass().getName():java.lang.Integer
      int.classint
      void.class.getName():void
      int[].class.getName():[I//由于历史原因会返回奇怪的名字。
      boss[].class.getName()[Lcom.logact.ParentAndSon.boss;
      
      ```

      ```
       Size size=Size.LARGE;
              System.out.println("size.getClass().getName():"+size.getClass().getName());
              System.out.println("main.getClass().getName():"+main.getClass().getName());
              String s="fs";
              System.out.println("s.getClass().getName():"+s.getClass().getName());
              Integer i=1;
              System.out.println("i.getClass().getName():"+i.getClass().getName());
              System.out.println("int.class"+int.class);
              System.out.println("void.class.getName():"+void.class.getName());
              System.out.println("int[].class.getName():"+int[].class.getName());
              System.out.println("boss[].class.getName()"+boss[].class.getName());
      ```

8. 一个class对象实际上表示的是一个类型，而这个类型未必一定是一个类。

9. class对象是一个泛型类。（这点是怎么做到的呢？）

10. 虚拟机为每一个类型管理一个泛型类。属于一个类的不同对象的地址是相同的。

11. 构造方法不允许使用static修饰符

13. 捕获异常：

    1. 异常分为两种：
       1. 已检查异常（由编译器负责）
       2.  未检查一场（如空指针运行时的异常）
    
14. 获取类的结构：
    
    1. 使用getDeclared***()返回所有的状态的方法，域 ，构造器，不然只能返回由public修饰的方法，域，构造器。

14. 通过反射在运行时查看某个参数的值

    1. ```java
       XXX obj=new XXX();
       Class clz=Class.forName("className");
       Field f =clz.getDeclaredFiled("FiledName");
       Object v= f.get(XXX);
       ```

    2. 在这段代码中对域的设定依然会遵循Java安全机制。（private,public ,protected依然有效）

    3. 反射机制的默认行为受限于Java访问控制。然而如果，一个Java程序没有收到安全管理器的控制，就可以覆盖访问控制。为了达到这个目的，可以调用Filed,Method或Construct对象的setAcessible方法。

    4. 通过反射机制查看某个私有属性的值并给它设置值

       ```java
       Parent parent=new Parent("logact");
               Class clz=parent.getClass();
               Field f=clz.getDeclaredField("private_String");
               f.setAccessible(true);
               Object v=f.get(parent);
               System.out.println(v);
               f.set(parent,"taishan");
               Object v1=f.get(parent);
               System.out.println("after set by the filed set");
               System.out.println(v1);
       
       ```

    5. 对与final 修饰的变量即使使用Java反射也不能将它的值真正的改变。而private的修饰的变量却可以真正的改变。

15. 关于泛型的toString方法
    
    1. 使用ObjectAnalyzer避免对泛型对象避免tostring方法被一直递归使用。 
    
16. 用反射来编写泛型数组。

    1. 问题：java会记住每一个数组的类型(记住的是在Java new这一句时就记住了这个时候的类型)

    2. 注意下面两句这样很奇怪的现象？？？？（涉及泛型以后再做）

       ```java
             a = (Item[]) new Object[2];
       //应用泛型时这时正确的
               a = (int[])new Object[2];
       //明确指定类型时只是错误的。
       ```

    3. 下面的转型会造成运行时异常(向下转型是运行是错误)

       ```java
          Son[] sons=(Son[])new Object[2];
               Son[] sons1=(Son[])new Object();
       ```

    4. 扩充任意类型数组的通用代码（使用反射机制获取这个数组的类型）

       ```java
       /**
       这个方法似乎与Arrays.copyof的结果是一样的
       */
       public static Object goodCopyOf(Object a,int newLength){
               Class c1=a.getClass();
               if(!c1.isArray())return null;
               Class componentType=c1.getComponentType();
               int length= Array.getLength(a);
               Object newArray=Array.newInstance(componentType,newLength);
               System.arraycopy(a, 0, newArray, 0, Math.min(length,newLength));
               return newArray;
           }
       
       ```

    5. 反射中的调用任意的方法（这种做法是极具隐患的）

17. java继承种的建议：

    1. protected的使用：

       1. 不要使用这个修饰符去修饰变量，但是对于专门提供给子类重新定义的方法是一个很好的选择。

    2. 除非所有继承的方法都有意义不然就不要使用继承。

       1. LocalDate is immutable （它是用final来修饰的）
       2. 扩展localDate???(java core)

    3. 覆盖方法的是时候应该符合置换原则：

       

​    

​    

​    

​    

​    
