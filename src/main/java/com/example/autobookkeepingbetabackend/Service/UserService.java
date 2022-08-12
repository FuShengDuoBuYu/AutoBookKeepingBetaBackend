package com.example.autobookkeepingbetabackend.Service;

import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Util.Response;

import java.util.List;

public interface UserService {
    Response<?> saveUser(User user);

    public Response<?> getUsersByFamilyId(String familyId);

    public User getUserByPhoneNum(String phoneNum);

}
