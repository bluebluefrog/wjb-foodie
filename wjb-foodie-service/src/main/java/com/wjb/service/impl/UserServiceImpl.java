package com.wjb.service.impl;

import com.wjb.mapper.FoodieUserMapper;
import com.wjb.pojo.FoodieUser;
import com.wjb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FoodieUserMapper userMapper;

    @Override
    public boolean queryUsernameExist(String username) {

        //Example传入操作的class
        Example example = new Example(FoodieUser.class);
        //创建条件
        Example.Criteria criteria = example.createCriteria();
        //参数1对比值参数2传入值
        criteria.andEqualTo("username",username);
        //通过条件查询
        FoodieUser foodieUser = userMapper.selectOneByExample(example);


        return foodieUser == null ? false : true;
    }
}
