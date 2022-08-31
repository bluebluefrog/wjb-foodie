package com.wjb.service;

import com.wjb.pojo.Category;
import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    List<Category> queryAllRootCategory();

    List<CategoryVO> getSubCatList(Integer rootId);

    List<NewItemsVO> getNewItems(Integer rootId);
}
