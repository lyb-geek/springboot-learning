## 本示例主要演示如何实现kafka广播模式

示例背景：

某项目中需要做集群本地缓存同步刷新，因为项目的备选方案采用利用消息队列来实现刷新
而kafka本身是不提供广播机制，kafka默认同一个消费者组内的消费者，一个消息只能被一个消费者消费。

### 广播模式实现策略

> 1、策略一：我们可以将集群中的每个服务的消费者都放在不同的组内

我们可以通过配置随机消费组或者根据IP做区分，即可实现集群内的广播


示例

 @KafkaListener(topics = "${userCache.topic}",groupId =  "${userCache.topic}_group_" + "#{T(java.util.UUID).randomUUID()})")
 
 
> 2、策略二：使用kafka aassign模式

在Kafka中，assign模式是指不使用消费者组，直接订阅分区，所有消费者都订阅指定分区，也可以实现分区内消息的广播消费

在spring-kafka中通过指定在如果@KafkaListener中的属性topicPartitions有值，则使用assign模式。





