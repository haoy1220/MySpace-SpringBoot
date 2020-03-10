package cn.wzhihao.myspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

//开启通用mapper自动扫描
@MapperScan(basePackages = {"cn.wzhihao.myspace.dao"})
@SpringBootApplication
public class MyspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspaceApplication.class, args);
    }

}
