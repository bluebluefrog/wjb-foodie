package com.wjb.service.impl;

import com.wjb.bo.UserBO;
import com.wjb.enums.Sex;
import com.wjb.mapper.FoodieUserMapper;
import com.wjb.pojo.FoodieUser;
import com.wjb.service.UserService;
import com.wjb.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FoodieUserMapper userMapper;

    @Autowired
    private Sid sid;

    public static final String USER_FACE = "src/main/resources/img/user.png";

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public FoodieUser createUser(UserBO userBO){

        String userId = sid.nextShort();


        FoodieUser user = new FoodieUser();

        user.setId(userId);

        user.setUsername(userBO.getUsername());
        //密碼加密
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //默认用户昵称和用户名一样
        user.setNickname(userBO.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(new Date());
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        userMapper.insert(user);

        return user;
    }
}
