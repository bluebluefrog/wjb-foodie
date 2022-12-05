package com.wjb.controller;

import com.wjb.enums.YesOrNo;
import com.wjb.pojo.*;
import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.CommentLevelCountsVO;
import com.wjb.pojo.vo.ItemInfoVO;
import com.wjb.pojo.vo.NewItemsVO;
import com.wjb.service.CarouselService;
import com.wjb.service.CategoryService;
import com.wjb.service.ItemService;
import com.wjb.utils.JSONResult;
import com.wjb.utils.PagedGridResult;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

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

    @GetMapping("/commentLevel")
    public JSONResult getCommentsCount(@RequestParam String itemId){

        if(itemId==null){
            return JSONResult.errorMsg("商品不存在");
        }

        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);

        return JSONResult.ok(commentLevelCountsVO);
    }

    @GetMapping("/comments")
    public JSONResult getComments(@RequestParam String itemId, @RequestParam Integer level,
                                  @RequestParam Integer page,@RequestParam Integer pageSize){

        if(itemId==null){
            return JSONResult.errorMsg("商品不存在");
        }

        if (page == null) {
            page = COMMENT_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);

        return JSONResult.ok(pagedGridResult);
    }

    @GetMapping("/search")
    public JSONResult searchItems(@RequestParam String keywords, @RequestParam String sort,
                                  @RequestParam Integer page,@RequestParam Integer pageSize){

        if (page == null) {
            page = COMMENT_PAGE;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);

        return JSONResult.ok(pagedGridResult);
    }

    @GetMapping("/catItems")
    public JSONResult catItems(@RequestParam String catId, @RequestParam String sort,
                                  @RequestParam Integer page,@RequestParam Integer pageSize){

        if (page == null) {
            page = COMMENT_PAGE;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.catItems(catId, sort, page, pageSize);

        return JSONResult.ok(pagedGridResult);
    }

    @GetMapping("/refresh")
    public JSONResult catItems(@RequestParam String itemSpecIds){

        System.out.println(itemSpecIds);

        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }

        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemSpecIds);


        return JSONResult.ok(itemsSpecs);
    }
}
