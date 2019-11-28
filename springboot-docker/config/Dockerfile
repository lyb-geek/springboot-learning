#使用Jdk8环境作为基础镜像，如果镜像不在本地则会从DockerHub进行下载
FROM openjdk:8-jdk-alpine
#VOLUME 指定了临时文件目录为/tmp。其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp。
#该步骤是可选的，如果涉及到文件系统的应用就很有必要了。
#/tmp目录用来持久化到 Docker 数据文件夹，因为 SpringBoot使用的内嵌Tomcat容器默认使用/tmp作为工作目录
VOLUME /tmp
#设置镜像的时区,避免出现8小时的误差
ENV TZ=Asia/Shanghai
#拷贝文件并且重命名
ADD springboot-docker.jar springboot-docker.jar
#过-D参数在对jar打包运行的时候指定需要读取的配置,为了缩短 Tomcat 启动时间，添加一个系统属性指向 "/dev/urandom"
ENTRYPOINT ["java","-Xms256m","-Xmx512m","-Djava.security.egd=file:/dev/./urandom","-jar","/springboot-docker.jar"]