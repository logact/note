# DataType

Java 是一种强类型语言

八种基本类型：

1. 4种整型。

2. 2种浮点型。
3. 一种用于表示Unicode编码的字符单元数据类型char。
4. 一种用于表示真假的类型Boolean。

## Integer

4种整型：

1. int ： 4字节 , 0x8000,0000~0x7fff,ffff,
   1. 关于溢出：
      * 如果直接赋值一个溢出的值给一个int变量那么这都不会通过编译。
        * 如果在运算的时候溢出那么会产生8个二进制溢出运算后的错误结果。如令0x7fffffff+1会变成0x80000000.
   2. 表示范围为-20亿~20亿。
2. short： 2个字节
3. long： 8个字节
4. byte ：1个字节

由于Java代码与机器无关，所以Java程序的数据类型取值固定。

idea language level ide检查Java最低限度的支持。

Java7 后可以用下划线隔开数字，可以支持二进制(0b)形式表示整数。

## 进制转换：

```java
System.out.println(0x12ff343ffffL);//用0和0x表示进制时默认的表示的是int，可以在后面加上L表示这个是一个long型
```

```java
//十进制数转为任意进制的字符串
Integer.toString(s,r);
//任意进制字符串转为十进制数。
Integer.toString(numString,r);
```



## 浮点 类型

float 4个字节

当浮点数参加运算时候不用考虑溢出的情况，计算机会自动帮助处理。

double 8个字节

Java浮点数遵循IEEE 754 标准

Java中float数据后面会跟一个f没有这个f会默认为double类型的数据。

用16进制数来表示浮点数值，用p表示指数，尾数用16进制数表示，指数用十进制数表示，制数的基数为二不是十。

1. 表示溢出和出错的三种情况：
   1. 正无穷大（如果是某个整数除以整数0那么这个会造成运行时异常，但是在关于浮点数的运算中会输出Infinity）将其强转为int会输出int 的最大值。
   2. 负无穷大 
   3. NaN(0/0) NaN默认与任何值都不相等就算是Double.NaN==Double.NaN亦为假。
2. 由于二进制舍入误差（由于使用二进制表示的犹如十进制不能精准的表示1/3）所以不能用来表示精准度要求高的数据（如若表示使用DigeDcimal(类)mysql中的定点数）

## char类型

在Java中char 类型描述的是UTF-16编码中的一个基本单元（16位）：

Unicode码是在ascill码上扩展的

Unicode与utf-8：

1. unicode是定长的

关于ascll码：

 虽然标准 ASCII 码是 7 位编码，但由于计算机基本处理单位为字节（ 1byte = 8bit ），所以一般仍以一个字节来存放一个 ASCII 字符。每一个字节中多余出来的一位（最高位）在计算机内部通常保持为 0 （在数据传输时可用作[奇偶校验位](https://www.baidu.com/s?wd=奇偶校验位&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)）。 由于标准 ASCII 字符集字符数目有限，在实际应用中往往无法满足要求。为此，[国际标准化组织](https://www.baidu.com/s?wd=国际标准化组织&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)又制定了 ISO2022 标准，它规定了在保持与 ISO646 兼容的前提下将 ASCII 字符集扩充为 8 位代码的统一方法。 ISO 陆续制定了一批适用于不同地区的扩充 ASCII 字符集，每种扩充 ASCII 字符集分别可以扩充 128 个字符，这些扩充字符的编码均为高位为 1 的 8 位代码（即[十进制数](https://www.baidu.com/s?wd=十进制数&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao) 128~255 ），称为扩展 ASCII 码。

常见的ascll码记忆：

A ---65

a ---97

0 ---48

在Java中'/u+16进制表示的码'可以表示相应的字符。

注意注释中的\u //例如// look inside c:\users会在Java编译时候就报错。

Unicode  转义序列会在解析代码之前得到处理。

关于java语言解决utf-16字符数量超过16位容量能够表示的方法。

utf-16采用不同长度的编码来表示所有的Unicode编码。

UTF-8编码规则：如果只有一个字节则其最高二进制位为0；如果是多字节，其第一个字节从最高位开始，连续的二进制位值为1的个数决定了其编码的字节数，其余各字节均以10开头。

1. 一些概念：

   1. 码点: 表示字符的代码值(变长)
   2. 代码单元: 在基本多语言级别中用16位的值（定长）表示。
   3. 代码级别
   4. Unicode分为17个代码级别（基本多语言级别 U+0000,U+FFFF）
   5. 其余的十六个级别为U+10000到10FFFF
   6. 多语言级别中有空闲的2048个字节。
   7. UTF-16编码采用不同长度的编码表示所有的字节。
   8. **BOM**：Byte Order Mark 
   9. cpa:著名的Brewer猜想说：对于现代分布式应用系统来说，数据一致性(Consistency)、系统可用性(Availability)、服务规模可分区性(Partitioning)三个目标(合称CAP)不可同时满足，最多只能选择两个。
   10. 代理区域(surrogate)
   11. 辅助区域(supplementaryCodePoint)

2. 各种码制的比较：

   UTF-8因为能兼容ASCII而受到广泛欢迎，但在保存中文方面，要用3个字节，有的甚至要4个字节，所以在保存中文方面效率并不算太好，与此相对，GB2312，GBK之类用两字节保存中文字符效率上会高，同时它们也都兼容ASCII，所以在中英混合的情况下还是比UTF-8要好，但在国际化方面及可扩展空间上则不如UTF-8了。Unicode的定长使得在传输英文时浪费了很多的空间所以，有了不定长的utf-16.

java怎么处理超过16位的Unicode字符：

1. 基本多语言级别利用空闲区域
2. 将超出16位的值分解为空闲区域的两个代码单元的组合。

java核心技术：强烈不建议使用char类型的数据。

## Boolean

不能与int相互转换。

## 变量

1.变量的命名规范

变量名必须是由数字开头并由字母或数字构成的序列。

Unicode字符都属于上述所说的字母。

const是Java的保留字但是还没有用到，在Java中申明常量必须使用final定义常量。

## 运算:

由与不同的机器上的浮点运算器的差别，例如寄存器大小的不一致，造成在计算后不同的机器上计算的浮点数的结果可能不相同，Java对此有两种解决方案：

1. 采用严格的浮点数（在类名和方法名前添加关键字（strictfp）可以指示计算机进行严格的浮点运算）

2. 允许虚拟机的设计者进行中间结果的扩展处理。
3. 这两种的方式的区别仅在于如果采用第一种方式不会溢出，采用严格的计算方式可能会导致溢出。

### 关于math

1. 计算指数，对数（采用换底公式就可以计算任意的log ab）

2. 一般的math函数返回的都是double对象。注意类型的转换。

3. Java中允许余数为负数

4. 关于书中的时针转动的问题：

   1. 问题的提出：如果时针转动的调整值为负数可能会使得这个值变为负数在运用操作符%时会出现负数

   2. 在Java8之后有这样的函数Math。floorMod（int a，int b）

      这等价于((position+adjustment)%12+12)%12;

5. Math.PI Math.E.

## 数值类型的转换：

### 合法转换

1.损失精度：

1. byte short int 可以和char类型之间相互转换（因为char是）

2.不会损失进度的：



Unicode编码是在基本的 ASCII码上的一个改进，可以同时兼容两种语言（及拉丁语和当地语言），也就是说，[Unicode码](https://www.baidu.com/s?wd=Unicode码&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)是ASCII码的一个改进版本，这是不同点。

关于ASCll 



## 位运算

1.如果用& 去代替&& 进行布尔运算时那么不会采用短路的方式来求值

2.>>>运算符会用0填充高位.>>运算符会用符号位来填充。

## 运算级别

1.运算优先级

## 枚举类型

枚举变量不能是局部变量。

## 字符串

字符串就是Unicode字符序列

1. 字符串的字串：substring (a,b);a:第一个开始的小标，b：第一个不被复制的下标。字串长度b-a。
2. 拼接：一个字符串与一个非字符串进行拼接操作时就会转变为字符串。Java中任何对象都能够转换为字符串。
3. join()方法：



### 字符串不可变：

字符串不可是java虚拟机提供的，使用反射机制可以改变字符串的值。

java字符串不可变的优缺点？

1.每次拼接都会产生新的对象。

2.但是不可变字符串可以使得编译器让字符串共享。

3.各种字符串存放在公共的存储池中。字符串变量只需要存储池中的相应的位置。

如果复制一个字符串那么就会让原始字符串与复制后的字符串共享一个字符串。

java 会进行自动的进行垃圾回收。如果有哪一块的内存不用了那么就会自动回收。

### 空串与null串

注意拼接的时候任意字符串与布尔值直接相拼接会使得这个值一直输出为false;

```java
布尔值与字符串连接后可以当作布尔值来使用只不过他的值一直是false")
```

### 将字符串分解为词：

三种方式:(recommend the reg exp)

```java
        String s= "1 2 3 4 5 6 a d  gg dfg dfaf";
        //spilt the String by one whitespace
        for (String word: s.split(" ")) {
            System.out.println(word);
        }
        // use  reg exp  to spilt the String by one or more whitespace
        // 返回的是一个字符串数组。
        for (String word : s.split("\\s+")) {
            System.out.println(word);
        }
        //use the StringTokenizer
        //默认的会将连续的分割符都删去
        StringTokenizer st = new StringTokenizer("hello world, of    |java",",l |");
        while (st.hasMoreElements()) {
            System.out.println("Token: " + st.nextToken());
        }
```

### 让字符串对齐

### String 与正则表达式

如何打印所有匹配的字符串？

```java
//使用循环调用mather.match()方法可以不停的匹配字符串中所有匹配的字符串。
    public static void main(String[] args) {
        String s =" from  aaa from bbb jlj";
        Pattern p = Pattern.compile("\\s+(from|join)\\s+(\\w+)");
        Matcher m = p.matcher(s);
        m.find();
        m.group(2);
        System.out.println("after");
        while(m.find()){
            System.out.println(m.group(2));
        }


    }
```



可以直接使用

```java
//这样的形式能够直接用于匹配简单的表达式。
str.matches("reg");
//但是编译之后匹配的速度更快。

```

#### Mathcher 的api

```java
match();// 将整个字符串来进行比较。
lookingAt(); //只在字符串的开始匹配。
find();//在字符串中匹配模式（不一定非从字符串的第一个字符开始）
//这些方法的返回值都是Boolean。匹配成功失败的结果。
//以上都是判定这个字符是否是匹配的方法。
```

#### 输出匹配字符：

```java
//Mathcher 中的 api
start();
end();
//两个方法分别返回匹配字符串在字符串中的开始和结束位置。
groupCount();
//返回用括号括起来的捕捉组的数量
group(int i);
//返回与当前匹配中的分组i匹配的字符。


```

#### 正则表达式的捕捉组。

1. 捕捉组的编号规则（数左括号）0号代表整个模式串。如果有命名的捕捉组那么就可以在编码时就会跳过它。

将匹配的字符串替换。

#### 如何将所有的匹配的字符串用特定的字符串替换。

对于替换操作mathcher 中有三个函数第一个就是直接用replace ，还有不停的在一个stringBuffer 中加入匹配的部分（这里不能用stingBuilder只能够用这个StringBuffer）这个可能与匹配方法是否是并发执行的有关。。。

```java
        String patt = "\\bfavor\\b";
        String input = "Do me a favor? Fetch my favorite";

        System.out.println("input:" + input);

        Pattern pattern = Pattern.compile(patt);
        Matcher m = pattern.matcher(input);
        System.out.println("ReplaceAll: " + m.replaceAll("favour"));

        System.out.println("input:" + input);
        m.reset();
//        StringBuilder sb  =new StringBuilder();// 不能定义为StringBuider
        StringBuffer sb =new StringBuffer();
        System.out.println("Appended methods:");
        while(m.find()){
            m.appendReplacement(sb, "favour");
        }
        m.appendTail(sb);//将最后一个匹配的字符串后面的所有字符串都加在整个的后面。
        System.out.println(sb.toString());        


```

#### 如何将不同文件中所有符合的模式的字符都打印出来。

1. 使用传统io每一次读一行在每一行中寻找。
2. 使用nio,将文件资源映射到内存中开辟的一块缓冲区中就可以做到在内存中操作的效果了。

```java
static  void process (Pattern pattern ,String fileName) throws IOException {
        Path path = Paths.get(fileName);
        FileChannel fc = new FileInputStream(fileName).getChannel();
        
        //映射文件内容
        ByteBuffer buf =fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size());
        //将ByteBuffer 解码为CharBuffer
        CharBuffer charBuffer = Charset.forName("ISO-8859-1").newDecoder().decode(buf);
        Matcher m =pattern.matcher(charBuffer);
        while(m.find()){
            System.out.println(m.group(0));
        }
    }
```



#### 如何将不同文件中所有符合的模式字符串都替换成自己想要的字符串？？？（未解决）（可以套用NIO的套路将文件映射在内存中就可以一般的方法解决这个问题了。）

#### 打印匹配表达式的一整行

编写一个类似grep的工具。。。

每一次去一行在查看这一行是否有匹配的如果有的话就将这一行打印。

```java
    BufferedReader is = new BufferedReader(new InputStreamReader(System.in));    Pattern pattern = Pattern.compile("fds");    Matcher matcher = pattern.matcher("fdf");    String line =null;    while((line=is.readLine())!=null){        if(matcher.find()){            System.out.println("MATCH: " + line);        }    }}
```

使用构造对象的第二个参数标记来可以指定模式对象的一些属性，可以让它不区分大小写。或者允许使用空格和注释。指定多行模式。

#### 匹配重音符或复合字符：

对于某些个字符有多个unicode 形式显示例如 带重音字母e，他就有多个表现形式（\u00e9)和（\u0301)。在模式中设置CANON_EQ。就在匹配中让这些分解是等价的。

#### 在文本中匹配换行符

使用\n或\r

或者设置Pattern.MULTILINE 常量，使用起始行和结束符（\^和$） 进行换行匹配。

由于有多种与换行相关的字符：\r,\u，所以如果只想匹配\n可以在编译选项中指定就，UNIX_LINES 标记值。

#### Apache 日志文件分析：

利用一个正则表达式解析一条日志记录各个选项。。。

#### 数据挖掘



```java
//z
```





### 码点与代码单元：

1.获得字符串的实际长度：（码点数量）

string.charAt()函数是以代码单元为处理单位而不是以码点作为处理单位，如果遇到某些字符是以两个代码单元作为码点那么就会出错。

```java
//这是一个有两个代码单元构成的字符的例子。
String sentence = "\u03C0\uD835\uDD6B";
// 该字符串的第二个（从一开始计）字符是空格
        System.out.println(sentence);
```



可以使用以下代码实现依次查看每一个码点：

```java
。。。。
```

如何正确打印所有的字符？(即使这个字符不是由一个代码单元构成的)

```java
//
int [] codePoints =str.codePoints();
String str =new  String(codePoints,0,codePoints.length);
```



### StringBuilder and StringBuffer

1.区别：

1. 前者效率更高但是只能在单线程下的状态下使用，后者能够在多线程的状态下使用。




### 按单词或者字符颠倒字符串

```java
StringBuilder sb = new StringBuilder(str).reverse();
```

将所有的单词逆序打出

```java
Stack<String> stack  = new  Stack<>();
StringTokenizer st =new  StringTokenizer(s);
while(st.hasMoreToken){
    stack.push(st.nextToken());
}
while(!stack.isEmpty()){
    System.out.println(stack.pop());
}
```

### 空格符与制表符互转

### String 转换为小写大写互相转换

```java
upperString  upperStr=str.toUpperString();
lowerString  lowerStr =str.toLowerString();
```

### CSV







