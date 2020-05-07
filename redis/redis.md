# redis 常见命令

## key:

dbsize 
keys 
move 
select

exists key
#关于键的存活时间
expire key
ttl key

type key

## String :

### 获得值设置值

set get
setrange 
getrange
setex
setnx
setget

### 如何使用setnx，setget实现分布式锁。

### 批量处理

mget 
mset
msetnx

### 加减：

incr 
decr
incrby
decrby

## list:

### 设置值，获取值

lpush  lrange  rpush rrange lindex llen

删除

lpop rpop

ltrim

lrem

