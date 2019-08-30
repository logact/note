# Input&Output

Scanner 类定义在Java util类中，Java lang包是默认引入的包，其他的包都要进行import操作。

## Input

关于使用console类来进行密码的加密操作：

查看System.console() api可得知该方法返回的是系统的console而在ide环境Java程序是当作后台程序运行的，所以它不会获得一个系统的console，而返回空。

```java
 public static void main(String[] args) {
        //Console 类实现密码加密 since Java se6
        Console cons =System.console();//The system console, if any, otherwise null.
        if (cons!=null) {
            String username= cons.readLine("user name:");
            char[]passwd=cons.readPassword("password:");
        }
    }
```

关于在命令行下运行程序的总结（并未解决）

一、java执行class文件是根据CLASSPATH指定的地方来找，不是我们理解当前目录。如果希望它查询当前目录，需要在CLASSPATH中加入“.;”,代表当前目录。

二、java执行class文件对package的路径是强依赖的。它在执行的时候会严格以当前用户路径为基础，按照package指定的包路径转化为文件路径去搜索class文件。各位同学以后注意就OK啦。至于网上说的要在CLASSPATH要加各种包等等都是泛泛而谈，真正静下心分析这个问题的资料不多。很多都没有说到点子上，会误导人的。

###  格式化输出

可以使用s转换符格式化任意对象，对实现了formattable接口的对象会调用formatTo（）方法，否则将调用toString();

可以使用静态方法 String.format()来创建一个格式化字符串而不输出。

格式化输出日期

```java
        System.out.println(datatime);//这里输出的时中文的时间星期五八月
        System.out.printf("%tB",new Date());//输出的也是中文
 System.out.printf("%1$s %2$tB ","time",new Date());
```

## 文件的输入与输出

1. 对文件读取的基本方法：

```
Scanner in=new Scanner(Path.get("myfile.txt"),"UTF-8");
//如果没有声明编码那么就会使用Java运行机器上的默认编码。
```



2. 对文件进行写操作的基本操作：

   ```java
   PrintWriter out =new PrintWriter("myfile.txt","UTF-8");//注意他的路径，如果是相对路径那么就会以虚拟机启动的位置来决定。但是如果由在ide环境下这个任务将有ide来接管。
   
   ```

   java 输入输出流是以程序为中心来说明输入输出的...

   