package com.wjb.service.impl;

import com.wjb.mapper.CategoryMapper;
import com.wjb.mapper.CategoryMapperCustom;
import com.wjb.pojo.Category;
import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.NewItemsVO;
import com.wjb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<Category> queryAllRootCategory() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        //roottype是1,这里查询root分类
        criteria.andEqualTo("type", 1);

        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootId) {

        List<CategoryVO> subCatList = categoryMapperCustom.getSubCatList(rootId);

        return subCatList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getNewItems(Integer rootId) {

        Map<String, Object> params = new HashMap<>();

        params.put("rootCatId", rootId);

        List<NewItemsVO> sixNewItemsLazy = categoryMapperCustom.getSixNewItemsLazy(params);

        return sixNewItemsLazy;
    }
}
