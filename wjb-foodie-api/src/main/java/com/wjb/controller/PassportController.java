package com.wjb.controller;

import com.wjb.bo.UserBO;
import com.wjb.pojo.FoodieUser;
import com.wjb.service.UserService;
import com.wjb.utils.CookieUtils;
import com.wjb.utils.JSONResult;
import com.wjb.utils.JsonUtils;
import com.wjb.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public JSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

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
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("密码不一致");
        }

        //注册
        FoodieUser user = userService.createUser(userBO);

        user = setUserNull(user);

        //将user信息set到cookie里
        //参数1req2resp3name4string类型存储信息5isEncode代表加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        //TODO 生成用户token 存储redis会话
        //TODO 同步购物车数据

        return JSONResult.ok(user);
    }

    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //校验基本信息
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //用户名不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名密码不能为空");
        }

        //用户密码是否小于六位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }

        String md5Password = MD5Utils.getMD5Str(password);

        //注册
        FoodieUser user = userService.queryUserForLogin(username, md5Password);

        if (user == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }

        user = setUserNull(user);

        //将user信息set到cookie里
        //参数1req2resp3name4string类型存储信息5isEncode代表加密
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user),true);

        //TODO 生成用户token 存储redis会话
        //TODO 同步购物车数据

        return JSONResult.ok(user);
    }

    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response){

        //TODO 用户退出 清空购物车
        //TODO 分布式会话中需要清除用户数据

        //清除用户相关的cookie
        CookieUtils.deleteCookie(request, response, "user");

        //用户退出登录需要清空购物车

        //分布式会话清除用户数据

        return JSONResult.ok();
    }

    private FoodieUser setUserNull(FoodieUser user){
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setBirthday(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        return user;
    }


}
