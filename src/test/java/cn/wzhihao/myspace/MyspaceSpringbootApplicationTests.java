package cn.wzhihao.myspace;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class MyspaceSpringbootApplicationTests {

    @Test
    void contextLoads() {
        Date date = new Date();
        System.out.println(date);

        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.getTime());
        System.out.println(calendar.getTimeInMillis());
        System.out.println(calendar);
    }

}
