package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实验类
 *
 * @author banzhili1haha
 * @date 2023/5/23 23:39111
 */
@RestController
@RequestMapping("/customer")
@Controller
@Slf4j
public class domeController {
    @GetMapping("/dome")
    public String dome(){
        new ReentrantLock();
        new ConcurrentHashMap<>();
        new CopyOnWriteArrayList();


        log.info("进入了aa");
        return "哈哈1";
    }
}
