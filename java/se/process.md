## control the processing

1.各种循环结构：

注意一下do while

注意浮点数这种特殊的情况：

```java
  for(double i=0;i !=10;i+=0.1){
            System.out.println("i:----->>>>>> " + i);
        }
```

这样的循环永远都不会结束由于浮点数的精度问题。

2.switch:

case 标签可以是：

1. 类型为char，byte，short，int的常量表达式。
2. 枚举常量。
3. since java SE 7,可以是字符串字面量。
4. 在用与enum时不用在每个标签中指定标签名。
5. java中goto是保留字：但没有在语言中使用它。在Java中使用go to语句不会通过编译。

3.break 语句：

在嵌套很深的循环中如果跳到指定循环外的简洁方法（不用在循环各层添加相应的条件判断语句以及各种变量）

在循环声明外添加一个标签：

```java
first:while(1<3){    break first;}
```

标签可以放在任何想要跳到的语句块之前

continue 带标签会跳到相应标签所表示的语句的第一句。