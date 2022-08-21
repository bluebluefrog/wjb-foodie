package com.wjb.controller;

import com.wjb.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;


    @GetMapping("usernameIsExist")
    public int usernameIsExist(@RequestParam String username){

        //使用工具类判断入参是否为空
        if(StringUtils.isBlank(username)){
            return 500;
        }

        //查找用户名是否存在
        boolean isExist = userService.queryUsernameExist(username);
        if(isExist){
            return 500;
        }

        return 200;
    }

}
