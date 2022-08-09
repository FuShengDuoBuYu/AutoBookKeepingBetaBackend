package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Service.Impl.OrderServiceImpl;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    OrderService orderService;

//    @GetMapping("/{phoneNum}/findall")
//    public Response<?> findall(@PathVariable String phoneNum){
//        List<Orders> orders = orderRepository.findOrdersByUserIdIs(phoneNum);
//        return new Response<>(true,"操作成功",orders);
//    }
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/{phoneNum}/addOrder")
    public Response<?> addOrder(@RequestBody JSONObject addOrderJson){
        Integer year = (Integer) addOrderJson.get("year");
        Integer month = (Integer) addOrderJson.get("month");
        Integer day = (Integer) addOrderJson.get("day");
        String clock = (String) addOrderJson.get("clock");
        Double money = Double.valueOf(addOrderJson.get("money").toString());
        String bankName = (String) addOrderJson.get("bankName");
        String orderRemark = (String) addOrderJson.get("orderRemark");
        String costType = (String) addOrderJson.get("costType");
        String userId = (String) addOrderJson.get("userId");

        Orders addOrder = new Orders(year,month,day,clock,money,bankName,orderRemark,costType,userId);

        return orderService.saveOrder(addOrder);
    }
}
