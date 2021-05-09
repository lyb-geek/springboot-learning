## 本文主要演示springboot如何集成arthas

通过arthas来排查死锁和oom，为了更快演示出oom效果
```
#在vm参数里面添加
 -Xmx100M -Xms100M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:HeapDumpPath=F:/gc.dump 
```
gc.dump可以通过jvisualvm.exe文件装入查看
