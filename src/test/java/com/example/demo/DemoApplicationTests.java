package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void test() {
        String a="11";
        String b="11";
        System.out.printf(String.valueOf(a.equals(b)));
    }

}
