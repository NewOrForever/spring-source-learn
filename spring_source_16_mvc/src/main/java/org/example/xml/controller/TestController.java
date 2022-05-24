package org.example.xml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    // no mapping
    public void testMapping() {
        System.out.println("this is test mapping");
    }
}
