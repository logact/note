# Overview

## 服務注冊中心 

### eureka(停止更新)

Eureka Server 提供服務注冊服務

Eurka Client 使用roud-robin (輪詢)負載算法的負載均衡器。在應用啓動後，將會向Eureka Server 發送心跳（默認周期為30秒）。如果Eureka Server 在多個心跳周期内沒有接受到某個節點的心跳，EurakaServer 將會從服務注冊表中把這個服務節點移除（默認90秒）。

1. 單機版：

   使用過程：

   1. 在pom文件中引入eureke server的依賴

   1. 啓動一個eureke server 的服務，配置實例名
   2. 在啓動類中加入@EnableEurekeServer

   ​            客戶端

   1. 在pom文件中引入eureka client 的依賴
   2. 配置server的地址
   3. 在啓動類中加入@EnableEurekaClient

2. 集群版：互相注冊，相互守望

   使用過程：

   1. 第一個機器指向第二個機器
   2. 第二個機器指向第一個機器
   3. 注意要修改127.0.1的映射文件
   4. 可以發現eureka集群搭建完成后即使order ,payment 的服務沒有注冊到一個eureka中但是所有的額eureka中都出現了它們的注冊信息。eureka集群之間會互相 複製信息。
   5. 在defaulZone 中添加兩個地址

3. 多個微服務構成集群

   1. 相同的微服務的applicaiton.name 都必須是一樣的。
   2. 在使用restTemplate方式向集群發送請求時需要在RestTemplate的配置類中加入LoadBalancer注解，restTemplate是springweb的類。
   3. LoadBalance這個注解是springcloud的注解但是這裏有一個疑問爲什麽springcloud申明是在父工程的裏面但是order這個子工程卻并沒有引入卻能夠直接使用這個標簽。
   4. 默認的是輪詢機制

4. 通過服務發現獲得服務信息

   1. 使用EnableDiscoveryClient （EnableEurekaClient 注解可以觸發發現服務，這個標簽在有了之後EnableEurekaClient 可以）
   2. 使用discoveryClient 獲得信息

5. eureka 的自我保護

   1. 保護模式主要用於一組客戶端和Eureka Server 之間存在網絡分區場景下的保護。一旦進入保護模式，Eureka Server 將會嘗試保護其服務注冊中的信息，不在刪除服務注冊表中的數據，也就是不會注銷任何的微服務。（ap）某時刻某一個微服務不可用了，Eureka 不會立刻清理，依然會對微服務進行保存。

   2. 默認情況下在沒有收到服務的心跳（默認90秒）就會注銷服務信息。如果在90秒鐘内丟失了大量的服務實例，就會開啓自我保護機制，因爲這種機制很有可能是網絡的通信。

   3. 怎麽禁止自我保護

      

### zookeeper

### consul

### nacos(推薦)

## 服務調用

### ribbon（維護）

實現客戶端負載均衡的工具

可以用loadBalance替換掉

ribbon的工作步驟：

1. 選擇一個EurekaServer,優先選擇在同一個區域内負載較少的server
2. 根據用戶指定的負載方式，從server取到的注冊列表中選擇一個地址
3. ribbon提供的負載方式：輪詢，隨機，根據相應時間加權
4. ribbon=負載均衡+restTemplate

RestTemplate

1. restTemplate.getForObject  返回對象為響應躰中的數據轉化城的對象類似與Json
2. restTemplate.getForEntity 包含一些響應頭，相應碼，

關於一些pom

```xml
引入了eureka-client-starter 就引用了ribbon不用再引入ribbon
```



ribbon與nginx的負載均衡的區別

1. ribbon是本地負載均衡，會在注冊中心上獲取注冊信息服務列表之後緩存到jvm本地，從而在本地實現負載均衡.
2. nginx

如何替換默認的負載均衡算法：

1. 負載均衡算法的原理：

   輪詢：用取餘數的算法

2. 源碼

IRule接口：

不能夠放在ComponentScan所掃描的包下

通過一個configuration 配置一個bean

Ribbon的負載均衡算法、

### LoadBalance

### OpenFegin(推薦)

fegin十一個申明式的WebService客戶端。使用Feign能讓編寫WebService 客戶端更加簡單。他的使用方法是定義一個服務接口然後在上面添加注解。fegin也可以支持可拔插式的編碼器和解碼器。Spring Cloud 對Fegin進行了封裝。可以與Eureka和Ribbon組合使用来实现负载均衡。fegin集成了ribbon

使用步骤

1. 在这里使用了OpenFegin之后就不用使用@EnableEurekaClient接口

OpenFeign 超时控制

1. 客户端默认等待1秒钟超过一秒就报错。
2. 设置Feign客户端的超时时间（由ribbon进行控制）

OpenFegin的日志增强

1. 提供了日志打印功能

## 服務降級

### hystrix(停更)

作用：hystrix 提供给一个在发生故障时的储备的函数,能让调用的方法不会一直占用线程浪费资源，方式故障在集群中大规模的传递。hystrix 一般用在客户端做降级.

1. 提供服务熔断
2. 提供服务降级
3. 提供限流

测试：给定两个服务一个正常一个超时如果单独点击那么不会出现任何错误，但是如果在高并发（jemeter工具模拟）的情况下就会出错，当超时的线程被大量的访问的时候，如果此时再次发出未超时的请求那么就会收到影响.由于超时的线程正在把所有的线程都占用完了

测试2:如果在消费者调用该服务时:

问题如何解决:

1. 解决超时: 
2. 解决异常:

解决方案:

1. 对方服务超时了,调用者不能一直卡死等待,必须要有服务降级
2. 对方服务down机了,调用者不能一直卡死等待,必须有服务降级
3. 对方服务ok,调用者自己出故障或有自我要求

具体步骤:

1. 服务提供方自己提供一个fallback,注解标签 @HystrixCommod

2. 使用@HystrixCommond(fallback)在方法名前指定定制的方法

3. 如果是要统一配置就使用@DefaultProperties()在一个类前,在要使用熔断的方法名前添加@HystrxCommmond(),这样就可以解决代码膨胀的问题

4. 如何使后备方法的逻辑与正常代码的逻辑分离开呢?

   1. 为fegin 接口添加一个服务降级的处理类.就可以实现解耦.

5. 服务熔断:服务降级是服务器忙,服务熔断是直接拒绝访问然后再调用降级的方法.熔断机制是应对雪崩效应的一种微服务链路保护机制,当扇出链路的某个微服务出错不可用或者响应时间太长的时候,会进行服务的降级.进而熔断该节点微服务的调用,快速返回错误的响应信息.当检测到该节点微服务调用响应正常后,恢复调用链路.当失败调用到一定的阈值是,缺省值是5秒内20次调用失败,就会启动熔断机制.

6. 熔断机制:

   测试: 

   1. 使用一个整数判定是否为正数如果不是就抛出异常触发后备的方法.

7. Hystrix 的工作流程

   1. 构造一个HystrixCommand 或者 HystrixOvservableCommand Object
   2. 执行command
   3. 响应是否被缓存
   4. 断熔器是否打开
   5. 线程池,队列,信号量是否满了
   6. 调用fallback.

8. HystrixDashboard

   在启动类中加入	@EnableHystrixDashboard

   通过/hystrix打开图形化页面

   在要监控的服务中要添加servelet 配置.

### resilience

### sentinel(推薦)

## 网关

### gateway（推薦）

gateway是基于异步非阻塞模型   

gateway的三个概念,predicate filter  route

集成了Hystrix的短路器功能

集成了spring cloud服务发现功能

基于编写的predicate 和filter 请求限流功能支持路径重写

1. gateway 的共组偶流程:

   1. 路由就是
   2. 断言使用
   3. filter 

   通过gateway mapping 找到一个gateway handler  经过一系列filter过后然后进入在找到服务.

2. 注意spring gate way 需要移除这个spring web starter 

3. 如何使用网关进行负载均衡

4. 常用的Route Predicate 

   1. Path
   2. After(ZoneDateTime.now())
   3. Method

5. filter

   1. 单一的
   2. 全局的:
      1. 全局日志配置
      2. 鉴权

## 配置



### config

功能集中管理配置文件,不同环境下不同配置,配置动态化更新,分环境部署.

运行期间动态调整配置,不在需要在每个服务部署的机器上编写配置文件,服务会象配置中心统一拉取.

当配置发生变动时,服务不需要重启即可感知到配置的变化并应用新的配置

将配置信息以rest接口的形式暴露.

配置文件读取规则:

1. /分支/配置文件.yml

2. 如果不加分支就去找master

3. ```
   {application}/{profile}/{label}
   ```

配置中心的客户端:

bootstrap.yml 的优先级更高

spring cloud 会创建一个 bootstrap context ,作为Spring 应用的Application Context 的上下文.初始化的时候,bootsstrap context 负责从外部源加载配置属性并解析配置.这两个上下文共享一个从外部获取Enviroment.

在spring cloud客户端需要引入springcloud客户端依赖.

但是这样的简答的配置在远端配置文件改变的时候,客户端并不能够自动刷新.

config 的手动刷新手动版

1. config 的服务端手动刷新
2. config 的客户端手动刷新 通过发送客户端请求 /acuator/refresh





### nacos



## 總綫

### bus

接着前面的东西如何解决config 的自动刷新

bus 支持两种消息代理: RabbitMQ 和 Kafka

spring cloud bus是用来将分布式系统 的节点 和轻量级信息系统链接起来的框架,它整合了Java事件处理机制和中间件功能.spring cloud 目前支持 rabbit Mq 和kafaka.可用于广播状态的更改,事件推送,也可以当作微服务之间的通信通道

基本原理 config实例都监听mq中同一个topic .当一个服务刷新数据的首,它会把这个信息放入到topic 这样它监听同一个topic 的服务就能得到通知.然后更新自身配置.

### nacos