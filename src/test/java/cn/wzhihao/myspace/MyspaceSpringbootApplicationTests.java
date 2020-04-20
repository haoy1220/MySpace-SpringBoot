package cn.wzhihao.myspace;

import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

@SpringBootTest
class MyspaceSpringbootApplicationTests {


    @Test
    void contextLoads() {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        Date date = new Date();
        System.out.println(date.getMonth());

    }

}
