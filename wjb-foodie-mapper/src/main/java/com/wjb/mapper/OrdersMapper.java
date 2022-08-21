package com.wjb.mapper;

import com.wjb.my.mapper.MyMapper;
import com.wjb.pojo.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersMapper extends MyMapper<Orders> {
}