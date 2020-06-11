# 毕业设计项目

### 涉及技术

1.使用JWT实现用户Token认证，保证了系统的权限安全；

2.采用RESTful风格接口，统一全局异常处理和规范的响应格式，代码结构清晰；

3.使用AOP技术对Controller层和Mybatis层进行日志打印，快速定位系统错误；

4.对接七牛云服务器接口，方便用户快速上传图片；

5.使用Quartz框架进行作业调度，结合mail邮箱工具，实现备忘录的定时邮件提醒；

6.使用JUnit进行单元测试，Nginx进行请求转发。

### 后端功能介绍

#### 一、用户模块


#### 二、日志模块


#### 三、备忘录模块


#### 四、项目管理模块



### Spring Boot 推荐目录结构

（1）代码层的结构

　　根目录：com.springboot

　　　　1.工程启动类(ApplicationServer.java)置于com.springboot.build包下

　　　　2.实体类(domain)置于com.springboot.domain

　　　　3.数据访问层(Dao)置于com.springboot.repository

　　　　4.数据服务层(Service)置于com,springboot.service,数据服务的实现接口(serviceImpl)至于com.springboot.service.impl

　　　　5.前端控制器(Controller)置于com.springboot.controller

　　　　6.工具类(utils)置于com.springboot.utils

　　　　7.常量接口类(constant)置于com.springboot.constant

　　　　8.配置信息类(config)置于com.springboot.config

　　　　9.数据传输类(vo)置于com.springboot.vo

（2）资源文件的结构

　　根目录:src/main/resources

　　　　1.配置文件(.properties/.json等)置于config文件夹下

　　　　2.国际化(i18n))置于i18n文件夹下

　　　　3.spring.xml置于META-INF/spring文件夹下

　　　　4.页面以及js/css/image等置于static文件夹下的各自文件下
