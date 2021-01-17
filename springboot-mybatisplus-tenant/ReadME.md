本例主要演示springboot与mybaits-plus实现多租户和自动填充。

主要实现的功能点

- 修复mybatis-plus 3.4+版本之前插入多租户会出现Column 'tenant_id' specified twice问题，这个问题发生的本质是实体自己手动去设置租户id【注：3.4+版本，官方已经修复这个问题】

- 解决mybatis-plus使用update(Wrapper<T> updateWrapper)，自动填充更新失效问题

