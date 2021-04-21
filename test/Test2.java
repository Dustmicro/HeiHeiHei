package com.kaikeba.test;

import com.kaikeba.bean.User;
import com.kaikeba.service.UserService;

public class Test2 {
    public static void main(String[] args) {
        //User user = new User();
        //user.setFace_id("12312313");
        //user.setCity("杭州heihei");
        //UserService.insert(user);
        //UserService.count("123");
        User user = new User();
        user.setUserName("老王");
        user.setDescription("锄禾日当午，汗滴禾下土");
        UserService.updateUserByFaceId("123",user);
    }
}
