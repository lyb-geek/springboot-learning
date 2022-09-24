## 本文主要演示redis6.0版本提供的客户端缓存功能

当redis缓存变更时，会更新本地缓存。利用lettuce-core 6提供的ClientSideCaching进行实现