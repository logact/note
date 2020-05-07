# sql语句操作的注意点

```sql
create table ingredient(
    xxx, varchar(10),constriants,
    
)
```

```sql
#使用des查看表的信息
```

AVG() 会忽略 NULL 行。

使用 DISTINCT 可以汇总不同的值。

```sql
SELECT AVG(DISTINCT col1) AS avg_col
FROM mytable;
```

