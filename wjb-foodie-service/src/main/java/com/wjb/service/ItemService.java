package com.wjb.service;

import com.wjb.pojo.Items;
import com.wjb.pojo.ItemsImg;
import com.wjb.pojo.ItemsParam;
import com.wjb.pojo.ItemsSpec;
import com.wjb.pojo.vo.CommentLevelCountsVO;
import com.wjb.pojo.vo.ItemCommentVO;
import com.wjb.utils.PagedGridResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemService {

    Items queryItemById(String id);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);

    CommentLevelCountsVO queryCommentCounts(String itemId);

    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);
}
