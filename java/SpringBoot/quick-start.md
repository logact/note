# quick start

## 1.spring boot 的项目目录意义

1. resources/static / : 静态资源存放 css js images
2. template: 保存所有的模板页面，（spring boot 项目默认使用嵌入式的tomcat 不支持jsp 页面）;可以使用模板引擎（freemaker,thymelaf）
3. application.properties :spring boot 的应用配置文件 修改spring boot 的默认配置

## 2.spirngboot 的配置

### 1. 配置文件

springboot  的全局配置文件，文件名是固定的

application.properties

appllication.yml

YAML(YAML Ain't a markup language)

1. YAML 是一个配置文件
2. 不是一个配置文件

YAML 比json 和xml更适合做配置文件

1. 以数据为中心 

2. 配置格式不同

   ```yaml
   server:
     port: 8081
   ```

## 3.grammer of yaml

1. basic grammer of yaml

   1. key + space(必须有)+value:

   2. 以空格的缩进来控制层级关系；只要能左对齐就是同一层级
   3. 大小写敏感

2. 值的写法

   1. 字面量的写法 k: v:  字面直接写；

   2. 字符串默认不加引号

      双引号：不会转义字符串中的特殊字符

      单引号：会转义

   3. 对象 map(属性和值)（键值对）

      1. 例如

         ```yaml
         friends
           name: 20
           age：20 
         ```

         注意空格缩进

      2. 行内写法

         ```yaml
         friends: {name: lisa,age: 20} 
         ```

   4. 数组（list set）

      1. 用- 指表示数组的一个元素

         ```yaml
         pets:
          - cat
          - dog
          - pig
         ```

      2. 行内写法

         ```yaml
         pets: [pets,dog,pig]
         ```

## 配置文件与类之间的映射关系

####  对这个类进行映射使用@ ConfigurationProperties

1. 特点： 映射全部属性
2. 如果使用 类中的每个属性就要与配置文件中的每个属性一一对应(好像也没有强行这样)

### 使用@value 注解对某一个属性进行映射

1. 特点： 映射某一个属性（在只要配置文件中的某一个属性时使用） 

### 两种方式的对比

|               | @ConfigurationProperties | @Value | 描述                   |
| ------------- | ------------------------ | ------ | ---------------------- |
| 松散绑定      | 支持                     | 不支持 | spring-bean springBean |
| SpEL          | 不支持                   | 支持   | #{}                    |
| JRS30数据校验 | 支持                     | 不支持 | @Email                 |
| 复杂数据类型  | 支持                     | 不支持 |                        |

## 4.占位符

1. 写随机数
2. 写属性的名字

## 5.Profile

### 创建多配置环境方式

1. 多profile文件

   可以创建多个application-{profile}.properties/yml

   默认使用application.properties/yml 配置文件

   yml 文档快语法

2. yml 文档快语法

   语法： ---

### 2.激活profile

1. 在默认配置文件中激活

   spring.profiles.active

2. 在命令行中指定

   --spring.profiles.active={profilename}(最高优先级)

3. 在打包后

   --在命令行中 Java -jar --spring.profiles.active={profilename}(最高优先级)

4. jvm 参数

   -Dspring.profiles.active={profilename}(最高优先级)

## 6.springboot 配置文件的加载位置

### 1.默认情况

​	file../config/

​	file../

​	classpath：/config/

​	classpath:/

​	优先级从高到低高优先级覆盖第优先级

​	形成互补配置

### 2.通过属性改变配置文件位置

1. spring.config.location
2. 情景：在项目打包后以命令行参数 java -jar {filename.jar} --spring.config.location="***"

## 7.外部配置文件加载位置

1. 命令行
2. jar包外的优先级高于jar包内的优先级，带profile的优先级高于不带profile的

## 8.自动配置原理？

配置文件能够添什么类就是来自于properties类

springBoot会加载大量的自动配置类,我们再来看这个自动配置类中配置了那些组件；（只要我们要用的组件有，我们就不要配置了），给容器的配置文件指定这些属性的值；

xxxAutoConfiguration:自动配置类

```java
@EnableConfigurationProperties({HttpProperties.class})protected static class StringHttpMessageConverterConfiguration {    private final Encoding properties;    protected StringHttpMessageConverterConfiguration(HttpProperties httpProperties) {        this.properties = httpProperties.getEncoding();    }
```

xxxProperties:封装配置文件中相关的属性

```java
@ConfigurationProperties(    prefix = "spring.http")
```

@Conditonxxx(条件判断类)（判断条件）

条件成立才往容器内添加组件

能配置的功能都来源于这个配置类

自动配置类只有在一定的条件下才生效

springboot 启动过程:



```java
1.@EnableAutoConfiguration ：开启自动配置
2.@Import({AutoConfigurationImportSelector.class})：利用selector导入一些组件
3.AutoConfigurationImportSelector.class
    有获得配置文件信息的方法
protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata, AnnotationMetadata annotationMetadata) {
        if (!this.isEnabled(annotationMetadata)) {
            return EMPTY_ENTRY;
        } else {
            AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
            List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
```

在idea中用ymal文件配置默认没有提示

