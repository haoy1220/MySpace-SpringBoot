package cn.wzhihao.myspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@EnableConfigurationProperties
//开启通用mapper自动扫描
@MapperScan("cn.wzhihao.myspace.dao")
@EnableScheduling
@SpringBootApplication
public class MyspaceSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspaceSpringbootApplication.class, args);
    }

}
