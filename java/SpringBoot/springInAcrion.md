# Spring In Action

1. 在springboot 中可以使用多种的模板引擎（with only a few exception ）one such exception is the Thymeleaf's Spring Security dialect ,which will be talked in chapter 4.
2. 如果使用了jsp那么就不能用excutable jar file 文件运行这个项目，因为tomcat或者jetty会去在/WEB-INF 下寻找jsp页面。这只能使用war packaging.
3. by default the template are only parsed once once once once!!!!
4. devTools 会禁止所有的缓存（但是在项目部署的时候就会禁用他自己）diable itself。
5. 