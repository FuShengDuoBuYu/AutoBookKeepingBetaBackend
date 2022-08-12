package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Service.UserService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //添加用户注册
    @PostMapping("/addUser")
    public Response<?> addUser(@RequestBody JSONObject addUserJson){
        String phoneNum = (String) addUserJson.get("phoneNum");
        String password = (String) addUserJson.get("password");

        User addUser = new User(phoneNum,password,null,null,null,null);
        return userService.saveUser(addUser);
    }

    //用户修改昵称
    @PutMapping("/user/modifyNickname")
    public Response<?> modifyNickname(@RequestBody JSONObject modifyNicknameJson){
        String phoneNum = (String) modifyNicknameJson.get("phoneNum");
        String newNickname = (String) modifyNicknameJson.get("newNickname");

        User pendingModifyUser = userService.getUserByPhoneNum(phoneNum);
        pendingModifyUser.setNickname(newNickname);
        return userService.saveUser(pendingModifyUser);
    }

    //用户创建家庭
    @PostMapping("/user/createFamily")
    public Response<?> createFamily(@RequestBody JSONObject createFamilyJson){
        String phoneNum = (String) createFamilyJson.get("phoneNum");
        String familyIdentity = (String) createFamilyJson.get("familyIdentity");

        User pendingModifyUser = userService.getUserByPhoneNum(phoneNum);

        if((!(pendingModifyUser.getFamilyId()==null))&&(!("".equals(pendingModifyUser.getFamilyId())))){
            return new Response<>(false,"您已在家庭中",null);
        }

        pendingModifyUser.setFamilyIdentity(familyIdentity);
        String familyId = Utils.generate8FamilyId();
        pendingModifyUser.setFamilyId(familyId);
        Response r = userService.saveUser(pendingModifyUser);
        return  r.isSuccess()?
                new Response<>(true,"操作成功",familyId):
                new Response<>(false,r.getMessage(),null);
    }

    //用户加入家庭
    @PostMapping("/user/addFamily")
    public Response<?> addFamily(@RequestBody JSONObject addFamilyJson){
        String phoneNum = (String) addFamilyJson.get("phoneNum");
        String familyIdentity = (String) addFamilyJson.get("familyIdentity");
        String familyId = (String) addFamilyJson.get("familyId");

        User addFamilyUser = userService.getUserByPhoneNum(phoneNum);

        addFamilyUser.setFamilyIdentity(familyIdentity);
        addFamilyUser.setFamilyId(familyId);

        return  userService.saveUser(addFamilyUser);
    }

    //查询某个家庭的所有成员
    @GetMapping("/user/getFamilyMembers/{familyId}")
    public Response<?> getFamilyMembers(@PathVariable String familyId){
        System.out.println(familyId);
        return userService.getUsersByFamilyId(familyId);
    }
}
