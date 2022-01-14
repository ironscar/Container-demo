package com.container.demo.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/msg")
    public Map<String, String> getMessages() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1 " + new Date().toString());
        map.put("key2", "value2 " + new Date().toString());
        map.put("key3", "value3 " + new Date().toString());
        return map;
    }
    
}
