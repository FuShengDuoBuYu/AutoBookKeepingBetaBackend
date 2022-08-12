package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Service.UserService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Response<?> saveUser(User user) {
        try {
            User u = userRepository.save(user);
            return new Response<>(true,"添加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }

    @Override
    public Response<?> getUsersByFamilyId(String familyId) {
        try {
            List<User> users = userRepository.findUsersByFamilyId(familyId);
            return new Response<>(true,"查询成功",users);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null );
        }
    }

    @Override
    public User getUserByPhoneNum(String phoneNum) {
        return userRepository.findUserByPhoneNumIs(phoneNum);
    }

}
