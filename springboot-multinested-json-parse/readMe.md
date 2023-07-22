## 本示例主要演示多层嵌套的json如何解析/替换

核心点：可以通过ognl表达式进行解析。对于确定的JSON格式，可以先转成对象，然后通过JSON序列化注解进行替换。
对于一些不怎么确定的JSON，可以通过com.alibaba.fastjson.JSON.put进行替换