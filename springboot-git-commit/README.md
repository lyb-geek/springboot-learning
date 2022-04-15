## 本示例主要演示利用git-commit-id-plugin来显示git的版本信息以及代码依赖包安全漏洞检测dependency-check-maven

利用git-commit-id-plugin可以用来

> 1、明确线上当前版本
> 2、明确线上代码是从哪个分支拉取

执行mvn dependency-check:check进行代码依赖包漏洞检查

在执行的过程中，可能会报Failed to initialize the RetireJS repo

下载jsrepository.json到maven私仓地址\org\owasp\dependency-check-data\7.0目录下

jsrepository.json这个文件从本示例的resources拿即可
