package com.wjb.controller;

import com.wjb.bo.ShopcartBO;
import com.wjb.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/shopcart")
public class ShopCartController extends BaseController{

    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        System.out.println(shopcartBO);
        //TODO 前端用户登录后 添加商品到购物车 同步到后端缓存

        return JSONResult.ok();
    }

    @PostMapping("/del")
    public JSONResult delete(@RequestParam String userId,
                          @RequestBody String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("");
        }

        //TODO 前端用户登录后 购物车中删除商品 同步到后端缓存

        return JSONResult.ok();
    }

}
