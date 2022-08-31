package com.wjb.service;

import com.wjb.pojo.Items;
import com.wjb.pojo.ItemsImg;
import com.wjb.pojo.ItemsParam;
import com.wjb.pojo.ItemsSpec;

import java.util.List;

public interface ItemService {

    Items queryItemById(String id);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);
}
