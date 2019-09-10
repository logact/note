# Java ee on linux

## 搭建开发环境

### 安装jdk

1. 安装jdk

   ```shell
   rpm -ivh jdk-8u221-linux-x64.rpm 
   ```

2. 查找jdk的相关信息

   1. ```shell
      rpm -qai | grep jdk 
      #搜索出带jdk的信息文字，里面有jdk 的完整包名
      #再使用
      rpm -qi [完整包名]
      #查询出所有的信息。
      ```

3. 设置环境变量

   ```shell
   export JAVA_HOME=/usr/java/jdk1.8.0_221-amd64
   export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
   export PATH=$PATH:$JAVA_HOME/bin
   #export 输出变量让变量生效 ：为windows下的；分隔作用
   ```

4. 需要注销用户环境变量才能生效。logout

5. javac编译Hello.java 

6. java Hello 运行 Hello.java

### 安装tomcat

```shell
yum -y install tomcat
#-y选型代表是否每次操作前都询问
systemctl status tomcat.service
#查询服务状态

```

使用

```shell
rpm -ql tomcat
#查询tomcat文件的安装路径
```

开启tomcat服务

```shel
systemctl start tomcat.service
//或者 
systemctl start tomcat
#或者运行start.sh 文件用 . 加文件名的方式。
```

安装失败（使用yum的安装的情况下）

### 安装MySQL

查询是否安装了某个软件

```shell
rpm -qa |grep mysql
```

创建用户和组

```shell
useradd -r -g mysql mysql #-r 系统用户
```

