# 毕业设计项目

### Git操作流程

1. git init
2. git remote add origin https://github.com/haoy1220/MySpace.git
3. git add .
4. git commit -m "first commit"
5. git push -u origin master
6. git checkout -b dev1.0 origin/master
7. git branch
8. git push origin HEAD -u

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
