package org.security.demo.authserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2020-08-08 16:01
 **/
@RestController
public class MyController {


    @RolesAllowed("adminManager")
    @GetMapping("/admin/talk")
    public Map talk() {
        Map<String, String> responseMap = new HashMap<>(10);
        responseMap.put("name", "liuzs");
        responseMap.put("talk", "hello word!");
        return responseMap;
    }


    @GetMapping("/open/send")
    public Map  send() {
        Map<String, String> responseMap = new HashMap<>(10);
        responseMap.put("name", "liuzs");
        responseMap.put("talk", "hello word!");
        return responseMap;
    }




}
