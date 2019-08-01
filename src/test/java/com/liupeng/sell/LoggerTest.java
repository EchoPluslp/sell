package com.liupeng.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    @Before
    public void testBefore(){
        System.out.println("before=================");
    }


    @Test
    public void test() {
        String name = "echo";
        Integer age = 12;
        log.info("info....name:{},age:{}", name, age);
        log.debug("debug...");
        log.error("error...");
    }

    @After
    public void testAfter(){
        System.out.println("after!!=================");
    }
}
