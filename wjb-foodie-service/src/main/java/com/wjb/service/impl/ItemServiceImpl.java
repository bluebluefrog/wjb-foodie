package com.wjb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjb.enums.CommentLevel;
import com.wjb.mapper.*;
import com.wjb.pojo.*;
import com.wjb.pojo.vo.CommentLevelCountsVO;
import com.wjb.pojo.vo.ItemCommentVO;
import com.wjb.pojo.vo.SearchItemsVO;
import com.wjb.pojo.vo.ShopcartVO;
import com.wjb.service.ItemService;
import com.wjb.utils.DesensitizationUtil;
import com.wjb.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {

        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {

        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer goodCounts = getComments(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getComments(itemId,CommentLevel.NORMAL.type);
        Integer badCounts = getComments(itemId,CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();

        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setTotalCounts(totalCounts);

        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        //分页
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVO> itemCommentVOList = itemsMapperCustom.queryItemComments(map);

        for(ItemCommentVO itemCommentVO:itemCommentVOList){
            itemCommentVO.setNickname(DesensitizationUtil.commonDisplay(itemCommentVO.getNickname()));
        }

        PagedGridResult pagedGridResult = setterPagedGrid(itemCommentVOList, page);

        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public  PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize){

        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        //分页
        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> searchItemsVOS = itemsMapperCustom.searchItems(map);

        PagedGridResult pagedGridResult = setterPagedGrid(searchItemsVOS, page);

        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public  PagedGridResult catItems(String catId, String sort, Integer page, Integer pageSize){

        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        //分页
        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> searchItemsVOS = itemsMapperCustom.searchItemsByThirdCat(map);

        PagedGridResult pagedGridResult = setterPagedGrid(searchItemsVOS, page);

        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecId(String specIds){

        //id拼接转数组
        String ids[] = specIds.split(",");
        List<String> specIdsList=new ArrayList<>();
        Collections.addAll(specIdsList, ids);

        for (String spec:specIdsList
             ) {
            System.out.println(spec);
        }
        List<ShopcartVO> shopcartVOS = itemsMapperCustom.queryItemsBySpecIds(specIdsList);
        return shopcartVOS;
    }

   private Integer getComments(String itemId,Integer level){

       ItemsComments condition = new ItemsComments();
       condition.setItemId(itemId);
        if(level!=null){
            condition.setCommentLevel(level);
        }
       int count = itemsCommentsMapper.selectCount(condition);
       return count;
    }

    private PagedGridResult setterPagedGrid(List<?>list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        //设置总页数
        grid.setTotal(pageList.getPages());
        //设置数据总数
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
