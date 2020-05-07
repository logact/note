# 问题记录

## 1.导入sql文件时出现中文乱码？

### 原因：

1. mysql 使用的字符集默认是Latin

### 方案：

1. 在创建数据库时指定charset=utf8
2. 在数据库文件的第一行加入set character	set utf8;

