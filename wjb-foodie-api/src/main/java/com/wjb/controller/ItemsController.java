package com.wjb.controller;

import com.wjb.enums.YesOrNo;
import com.wjb.pojo.*;
import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.ItemInfoVO;
import com.wjb.pojo.vo.NewItemsVO;
import com.wjb.service.CarouselService;
import com.wjb.service.CategoryService;
import com.wjb.service.ItemService;
import com.wjb.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/info/{itemId}")
    public JSONResult info(@PathVariable String itemId){

        if(itemId==null){
            return JSONResult.errorMsg("商品不存在");
        }

        Items items = itemService.queryItemById(itemId);

        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);

        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);

        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemSpecList(itemsSpecs);
        itemInfoVO.setItemParams(itemsParam);

        return JSONResult.ok(itemInfoVO);
    }

}
