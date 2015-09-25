#简易脚手架 - mytemplate

##介绍

mytemplate是一个简易脚手架，mytemplate简易脚手架项目仍然处于项目开发阶段，还有很多功能没有完成。


##功能

mytemplate简易脚手架提供以下基本功能：

1. 系统管理
   1. 菜单管理
   2. 字典管理
   3. 日志管理
2. 机构用户
   1. 区域管理
   2. 机构管理
   3. 用户管理
   4. 角色管理
4. 系统监控
   1. ehcache监控
   2. jvm监控
   3. 执行sql
   4. 数据库监控
5. 其他

以上功能是mytemplate简易脚手架项目已经完成的功能，该项目仍然处于开发阶段，还有很多功能没有完成。

##技术

mytemplate使用了

- Servlet 3.0

- Spring4

- SpringMVC

- MyBatis

- 模板引擎beetl.

- 前端bootstrap.

- 数据源druid.

- 缓存ehcache.

- [MyBatis分页插件PageHelper](http://git.oschina.net/free/Mybatis_PageHelper)

- [MyBatis通用Mapper](http://git.oschina.net/free/Mapper).

##启动配置

1. 配置数据库，在项目的`src/main/webapp/`下面有一个<b>mytemplate.sql</b>文件，在mysql中执行该文件，创建数据库

2. 修改数据库配置文件，在`src/main/resources/`下面有一个<b>resources.properties</b>，修改数据库相关信息

3. 在项目根路径下打开CMD，执行<b>mvn tomcat7:run</b>服务就会启动，配置的端口为9090，需要修改是在pom.xml中修改即可

4. 输入网址`http://localhost:9090/mytemplate/`，用户名/密码:admin/admin


##演示地址

请大家不要随意的修改和删除重要的配置数据！

演示地址：[http://aieasy.oschina.mopaas.com/login](http://aieasy.oschina.mopaas.com/login)

测试用户：稍后提供...

##系统截图

###登录页面

![1](http://git.oschina.net/uploads/images/2015/0312/200734_076a13aa_8363.png)

###菜单配置

![2](http://git.oschina.net/uploads/images/2015/0312/200734_15486c1c_8363.png)

###[查看更多](http://git.oschina.net/danyuyingxin/mytemplate/tree/master/wiki/screenshot.md)