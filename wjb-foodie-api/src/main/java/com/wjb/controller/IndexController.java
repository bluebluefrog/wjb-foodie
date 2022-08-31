package com.wjb.controller;

import com.wjb.enums.YesOrNo;
import com.wjb.pojo.Carousel;
import com.wjb.pojo.Category;
import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.NewItemsVO;
import com.wjb.service.CarouselService;
import com.wjb.service.CategoryService;
import com.wjb.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/carousel")
    public JSONResult carousel(){

        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

        return JSONResult.ok(carousels);
    }

    /**
     * 首页需求
     * 1第一次刷新查询大分类，渲染
     * 2鼠标移动到大分类加载子分类渲染，如果已存在子分类不重新加载
     */

    @GetMapping("/cats")
    public JSONResult category(){

        List<Category> categories = categoryService.queryAllRootCategory();

        return JSONResult.ok(categories);
    }

    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCategory(@PathVariable Integer rootCatId){

        if(rootCatId==null){
            return JSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);

        return JSONResult.ok(subCatList);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItem(@PathVariable Integer rootCatId){

        if(rootCatId==null){
            return JSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> newItems = categoryService.getNewItems(rootCatId);

        return JSONResult.ok(newItems);
    }
}
