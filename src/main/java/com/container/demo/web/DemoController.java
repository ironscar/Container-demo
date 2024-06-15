package com.container.demo.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.container.demo.domain.ExternalPropHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private ExternalPropHolder externalPropHolder;

    @GetMapping("/msg")
    public Map<String, String> getMessages() {
        Map<String, String> map = new HashMap<>();

        log.info("external properties = " + externalPropHolder.toString());
        String customPrefix = ("custom").equals(externalPropHolder.getProp1()) ? externalPropHolder.getProp2() : "";

        for (int i = 1; i <= 3; i++) {
            map.put("key" + i, customPrefix + "value" + i + ": :" + new Date().toString());
        }

        return map;
    }
    
}
