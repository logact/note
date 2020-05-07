# mybatis

1. 在springboot中设置 mapper-locations的位置：

语句：

```xml
<select id = "方法名" parameterType="int" resultType="com.kuang.pojo.User">
    select * from mybatis.usesr where id= #{id}
</select>
```

2. 在mybaits 中使用 #{} ${ } 的区别

3. 如何处理一对多，多对一的关系。

   1. 实现一对一的关系

      ```xml
      1. 使用association 标签
      有两种方式
      1. 首先查出id 然后再写一个一个sql语句查找出具体的对象
      2. 直接用联合查找
      ```

      b. 实现多对多的关系

      ```xml
      使用 collection接口
      ```

      

4. mybatis 中的标签

   1. <if test="">

   2. <where> 

   3. <choose> <when test="">

   4. <foreach>

   5. ### 3. foreach属性[#](https://www.cnblogs.com/coderzhw/p/11094300.html#3996180942)

      | 属性         | 描述                                                         |
      | ------------ | ------------------------------------------------------------ |
      | `item`       | 循环体中的具体对象。支持属性的点路径访问，如item.age,item.info.details。具体说明：在list和数组中是其中的对象，在map中是value。该参数为必选。 |
      | `collection` | 要做foreach的对象，作为入参时，List<?>对象默认用list代替作为键，数组对象有array代替作为键，Map对象用map代替作为键。当然在作为入参时可以使用@Param("keyName")来设置键，设置keyName后，list,array,map将会失效。 除了入参这种情况外，还有一种作为参数对象的某个字段的时候。举个例子：如果User有属性List ids。入参是User对象，那么这个collection = "ids"如果User有属性Ids ids;其中Ids是个对象，Ids有个属性List id;入参是User对象，那么collection = "ids.id"上面只是举例，具体collection等于什么，就看你想对那个元素做循环。该参数为必选。 |
      | `separator`  | 元素之间的分隔符，例如在in()的时候，separator=","会自动在元素中间用“,“隔开，避免手动输入逗号导致sql错误，如in(1,2,)这样。该参数可选。 |
      | `open`       | foreach代码的开始符号，一般是(和close=")"合用。常用在in(),values()时。该参数可选。 |
      | `close`      | foreach代码的关闭符号，一般是)和open="("合用。常用在in(),values()时。该参数可选。 |
      | `index`      | 在list和数组中,index是元素的序号，在map中，index是元素的key，该参数可选。 |

## mybatis 缓存

一级缓存:只在同一个session下。同一个mapper

二级缓存：

一级缓存的数据会传递到二级缓存中。

第三方的二级缓存 ehcach.	