# 目录结构
    
    appversion
    ├─config  			配置文件
    ├─frontend			前端代码
    ├─sqls    			数据库
    ├─src     			Java代码
    ├─apk     			apk上传目录
    ├─entry     		入口点配置目录
    └─upload  			icon等图片上传目录
    

# 编译&运行

1. 进入frontend目录，npm i & npm run build 编译前端项目
2. 编译完成后，src/main/resources/templates/dash目录下将生成`static`,`login.ftl`
3. 进入根目录，运行mvn install，编译java项目
4. 编译完成后，在target/目录下会生成`lib`,`appversion-1.0-SNAPSHOT.jar`
5. 在`appversion-1.0-SNAPSHOT.jar`同级目录下（运行环境目录，一般来说同级，具体看运行环境）创建config/config.properties，文件内容可参考根目录下config文件夹

# Docker

**获取当前版本**

`docker pull tctxl/appversion:1.1.20210113`

**数据库**

可以通过设置Environment参数来配置数据源地址等参数
`JDBC_URL`,`JDBC_USERNAME`,`JDBC_PASSWORD`
如不设置，则自动使用H2作为嵌入式数据库运行

**如何运行**

`docker run -it tctxl/appversion:1.1.20210113`

如要自定义数据源，可使用以下命令，目前只支持MYSQL（MARIADB）

`docker run -it
-e JDBC_URL="jdbc:mysql://127.0.0.1:3306/appversion?zeroDateTimeBehavior=convertToNull&useSSL=false"
-e JDBC_USERNAME="root"
-e JDBC_PASSWORD="123456"
tctxl/appversion:1.1.20210113`

**数据持久化**

可通过docker的volume功能将容器内的路径映射到本机

1. `/db`为H2生成的数据库目录
2. `/apk`为上传apk包的目录
3. `/upload`为上传App的ICON的目录
4. `/entry`入口点信息文件目录

示例：

`docker run  -it --name appversion -v $(pwd)/entry:/entry -v $(pwd)/db:/db -v $(pwd)/apk:/apk -v $(pwd)/upload:/upload -p 10005:10005 -d tctxl/appversion:1.1.20210113`

**演示**
```
地址：http://114.67.87.255:10007/oa4a0NhG7D
用户名：wHWFXE
密码：3NOZo5Wzufrf
```

# API

```
接口：/api/version/check
参数：
ver : 版本号
channel : 渠道号
appId : 应用ID
```

**演示接口**
```
http://114.67.87.255:10007/api/version/check?ver=2020.8&channel=default&appId=1000
```

**返回结果**

|字段名称|类型|描述|
| :------------ | :------------ | :----------- |
|id             |  Long         |  唯一主键       |
|appId          |  Long         |  应用ID         |
|appOpen        |  Integer      |  是否分发,0.未分发,1.已分发 |
|channel        |  String       |  渠道名称 |
|title          |  String       |  升级弹窗标题 |
|content        |  String       |  升级弹窗简介 |
|type           |  Integer      |  升级类型,1.非强制升级,2.强制升级,3.非强制升级-跳转浏览器,4.强制升级-跳转浏览器,5.非强制升级-跳转市场,6.强制升级-跳转市场 |
|url            |  String       |  应用下载地址 |
|versionCode    |  Integer      |  每次新建版本号会加一 |
|versionName    |  String       |  版本号 |
|createTime     |  Timestamp    |  条目创建时间 |
|updateTime     |  Timestamp    |  条目更新时间 |

**分发页面**
```
地址：/s/appId
演示：http://114.67.87.255:10007/s/1000
```

渠道分发机制还有些不完善，下一版本会修改分发测试包的机制，以及iOS端的manifest生成。

届时会将App下载地址页面与测试分发页面分离开，使可以多渠道进行分发测试包。

目前仅保证App端升级功能可用。
