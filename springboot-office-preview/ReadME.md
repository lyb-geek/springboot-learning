本例主要演示springboot与jodconverter整合实现office在线预览

> 实现预览的核心步骤
  - 1.上传文档到office服务器（office服务器要自己实现，而且服务器要装有libreoffice）
  - 2.office服务器读取上传的文档，并将上传文档转换成pdf，或者html（通常excel格式建议转换成html）
  - 3.从office服务器上拉取已经转换好的文档，前端页面可以利用iframe或者embed标签来显示pdf文档，型如
    ```
    <iframe id="iframe" frameborder="0" src="/preview/readFile?fileName=${fileName}" style="width:100%;"></iframe>
    或者
    <embed src="/preview/readFile?fileName=${fileName}" width="100%" height="1500" />
    ```
    
> libreoffice安装
  - window安装，可以通过下方链接下载，并按提示一步一步安装
  [window安装](https://zh-cn.libreoffice.org/download/download/)
  
  - centos安装
    
    ```
    #安装文件
    
    yum -y install libreoffice
    
    #安装中文包
    
    yum -y install libreoffice-langpack-zh-Han*
    
    #安装的目录在/usr/lib64/libreoffice
    ```
    
> libreoffice在centos/window上通过命令行进行测试shell指令如下
  ```
  #centos
  /usr/bin/libreoffice  --invisible --convert-to pdf  <待转换的word路径> --outdir <生成的pdf路径>
  
  比如：/usr/bin/libreoffice  --invisible --convert-to pdf  test.txt --outdir abc
  
  
  # windows
  soffice.exe --headless --invisible --convert-to pdf test.txt --outdir d:\abc
  ```


 
 
  