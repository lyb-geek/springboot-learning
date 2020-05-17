该demo主要演示springboot与apollo整合实现配置刷新，配置刷新的方式有如下

> 1、普通字段的刷新

通过@value注解实现实时刷新

> 2、bean字段的刷新

bean的刷新只能手动刷新，可以通过RefreshScope.refresh()刷新或者通过 applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));进行刷新

> 3、bean字段上有@ConditionalOnProperty的刷新







