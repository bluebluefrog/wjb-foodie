package com.wjb.controller;

import com.wjb.bo.UserBO;
import com.wjb.pojo.FoodieUser;
import com.wjb.service.UserService;
import com.wjb.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;


    @GetMapping("usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username){

        //使用工具类判断入参是否为空
        if(StringUtils.isBlank(username)){
            return JSONResult.errorMsg("用户名不能为空");
        }

        //查找用户名是否存在
        boolean isExist = userService.queryUsernameExist(username);
        if(isExist){
            return JSONResult.errorMsg("用户名已存在");
        }

        return JSONResult.ok();
    }
    
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO){

        //校验基本信息
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //用户名不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名密码不能为空");
        }

        //用户名是否存在
        boolean isExist = userService.queryUsernameExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已存在");
        }

        //用户密码是否小于六位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }

        //用户两次密码是否一致
        if(!password.equals(confirmPassword)){
            return JSONResult.errorMsg("密码不一致");
        }

        //注册
        FoodieUser user = userService.createUser(userBO);

        return JSONResult.ok(user);
    }

}
