package com.example.autobookkeepingbetabackend.Controller;


import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.OrderMapPlace;
import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Service.OrderMapPlaceService;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Service.UserService;
import com.example.autobookkeepingbetabackend.Util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class OrderMapPlaceServiceController {
    OrderMapPlaceService orderMapPlaceService;
    UserService userService;
    OrderService orderService;
    @Autowired
    public void OrderMapPlaceServiceImpl(OrderMapPlaceService orderMapPlaceService,UserService userService,OrderService orderService){
        this.orderMapPlaceService = orderMapPlaceService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/addLBSOrder")
    private Response<?> addLBSOrder(@RequestBody JSONObject addOrderJson){
        Double latitude = Double.valueOf(addOrderJson.get("latitude").toString());
        Double longitude = Double.valueOf(addOrderJson.get("longitude").toString());
        String userId = (String) addOrderJson.get("userId");
        Double money = Double.valueOf(addOrderJson.get("money").toString());
        Integer orderId = Integer.valueOf(addOrderJson.get("orderId").toString());
        return orderMapPlaceService.addLBSOrder(new OrderMapPlace(latitude,longitude,userId,money,orderId));
    }

    @GetMapping("/findTopNPersonalOrderMapPlace/{userId}/{n}")
    private Response<?> findTopNPersonalOrderMapPlace(@PathVariable String userId,@PathVariable String n){
        return orderMapPlaceService.findTopNPersonalOrderMapPlace(userId,Integer.valueOf(n));
    }

    @GetMapping("/findTopNFamilyOrderMapPlace/{familyId}/{n}")
    private Response<?> findTopNFamilyOrderMapPlace(@PathVariable String familyId,@PathVariable String n){
        //找到家庭成员,根据userId
        List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
        List<OrderMapPlace> res = null;
        for (User user:familyUsers){
            Response<List<OrderMapPlace>> response = orderMapPlaceService.findTopNPersonalOrderMapPlace(user.getPhoneNum(),Integer.parseInt(n));
            if (response.isSuccess()){
                res.addAll(response.getData());
            }
        }
        //将OrderMapPlace按照id倒序排列
        Collections.sort(res, new Comparator<OrderMapPlace>() {
            @Override
            public int compare(OrderMapPlace o1, OrderMapPlace o2) {
                return o2.getId() - o1.getId();
            }
        });
        res.subList(0,Math.min(Integer.parseInt(n),res.size()));
        return new Response<>(true,"查询成功",res);
    }
}
