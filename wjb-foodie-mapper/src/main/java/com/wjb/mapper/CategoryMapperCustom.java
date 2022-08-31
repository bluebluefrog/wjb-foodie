package com.wjb.mapper;

import com.wjb.pojo.vo.CategoryVO;
import com.wjb.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);

    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);

}