package com.cbt.testspringapr24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @Autowired
    @Qualifier("MyNumBean1")
    MyNumber number1;

    @Autowired
    @Qualifier("getMyNumTwo")
    MyNumber number2;

    @GetMapping("greet")
    public String greet()
    {
        return "hello world";
    }

    @GetMapping("get/numberone")
    public MyNumber getNumberOne(@Autowired @Qualifier("MyNumBean1")  MyNumber numberx)
    {
        numberx.increment();
        return numberx;
    }

    @GetMapping("get/numbertwo")
    public MyNumber getNumberTwo()
    {
        return number2;
    }

}
