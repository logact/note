

## Scope

compile:编译依赖范围。如果没有指定，就会默认使用该依赖范围。使用此依赖范围的Maven依赖，对于编译、测试、运行三种classpath都有效。典型的例子是spring-code,在编译、测试和运行的时候都需要使用该依赖。

test: 测试依赖范围。使用次依赖范围的Maven依赖，只对于测试classpath有效，在编译主代码或者运行项目的使用时将无法使用此依赖。典型的例子是Jnuit,它只有在编译测试代码及运行测试的时候才需要。

provided:已提供依赖范围。使用此依赖范围的Maven依赖，对于编译和测试classpath有效，但在运行时候无效。典型的例子是servlet-api,编译和测试项目的时候需要该依赖，但在运行项目的时候，由于容器以及提供，就不需要Maven重复地引入一遍。

import :但是，一般情况下，在我们自己的项目中，会定义一下自己的 parent 项目，这种情况下，上面的这种做法就行不通了。那么，该如何来做呢？其实，在spring的官网也给出了变通的方法的：

```java
//自己的parent
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>1.5.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

maven常用的scope有compile,provided,runtime,test。

complie是默认值，表示在build,test,runtime阶段的classpath下都有依赖关系。
test表示只在test阶段有依赖关系，例如junit
provided表示在build,test阶段都有依赖，在runtime时并不输出依赖关系而是由容器提供，例如web war包都不包括servlet-api.jar，而是由tomcat等容器来提供
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>3.0.1</version>
    <scope>provided</scope>
</dependency>

runtime表示在构建编译阶段不需要，只在test和runtime需要。这种主要是指代码里并没有直接引用而是根据配置在运行时动态加载并实例化的情况。虽然用runtime的地方改成compile也不会出大问题，但是runtime的好处是可以避免在程序里意外地直接引用到原本应该动态加载的包。例如JDBC连接池
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.4</version>
			<scope>runtime</scope>
		</dependency>
spring applicationContext.xml

	<!-- mysql -->
	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource">
			<ref bean="dataSource" />
		</property>
	</bean>
	<!-- Connection Pool -->
	<bean id="dataSource" destroy-method="close"
		class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="${ckm.jdbc.driver}" />
		<property name="url" value="${ckm.jdbc.url}" />
		<property name="username" value="${ckm.jdbc.username}" />
		<property name="password" value="${ckm.jdbc.password}" />
		<property name="initialSize" value="6" />
	</bean>





