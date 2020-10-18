# 目录结构
    
    appversion
    ├─config  			配置文件
    ├─frontend			前端代码
    ├─sqls    			数据库
    ├─src     			Java代码
    ├─apk     			apk上传目录
    └─upload  			icon等图片上传目录
    

# 编译&运行

1. 进入frontend目录，npm i & npm run build 编译前端项目
2. 编译完成后，src/main/resources/templates/dash目录下将生成`static`,`login.ftl`
3. 进入根目录，运行mvn install，编译java项目
4. 编译完成后，在target/目录下会生成`lib`,`appversion-1.0-SNAPSHOT.jar`
5. 在`appversion-1.0-SNAPSHOT.jar`同级目录下（运行环境目录，一般来说同级，具体看运行环境）创建config/config.properties，文件内容可参考根目录下config文件夹

# Docker

**获取当前版本**

`docker pull tctxl/appversion:1.0.20201019`

**数据库**

可以通过设置Environment参数来配置数据源地址等参数
`JDBC_URL`,`JDBC_USERNAME`,`JDBC_PASSWORD`

**如何运行**

`docker run 
-e JDBC_URL="jdbc:mysql://127.0.0.1:3306/appversion?zeroDateTimeBehavior=convertToNull&useSSL=false"
-e JDBC_USERNAME="root"
-e JDBC_PASSWORD="123456"
 tctxl/appversion:1.0.20201019`
