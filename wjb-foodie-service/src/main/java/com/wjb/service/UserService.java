package com.wjb.service;

import com.wjb.bo.UserBO;
import com.wjb.pojo.FoodieUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {



    public boolean queryUsernameExist(String username);

    FoodieUser createUser(UserBO userBoO);

    @Transactional(propagation = Propagation.SUPPORTS)
    FoodieUser queryUserForLogin(String username, String password);
}


