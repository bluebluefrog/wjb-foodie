package com.wjb.service;

import com.wjb.bo.UserBO;
import com.wjb.pojo.FoodieUser;

public interface UserService {



    public boolean queryUsernameExist(String username);

    FoodieUser createUser(UserBO userBoO);
}


