## 本示例主要演示springboot项目如何利用jmh来进行基准测试

官网：https://github.com/openjdk/jmh
参考示例:https://cloud.tencent.com/developer/article/1760933

可以打成jar包运行，也可以直接在IDEA中运行

打成jar

java -jar jmh-demo.jar SpringBootJmhTest  -rf json -rff D:/jmhResult.json

-rf: 为输出的格式为json -rff: 为指定输出的位置