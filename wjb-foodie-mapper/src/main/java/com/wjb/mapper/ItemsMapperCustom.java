package com.wjb.mapper;

import com.wjb.pojo.vo.ItemCommentVO;
import com.wjb.pojo.vo.SearchItemsVO;
import com.wjb.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComments(@Param("paramsMap")Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
}