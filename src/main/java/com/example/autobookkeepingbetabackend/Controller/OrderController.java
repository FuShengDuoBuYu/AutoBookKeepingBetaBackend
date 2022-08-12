package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Entity.User;
import com.example.autobookkeepingbetabackend.Service.Impl.OrderServiceImpl;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Service.UserService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {

    OrderService orderService;
    UserService userService;

//    @GetMapping("/{phoneNum}/findall")
//    public Response<?> findall(@PathVariable String phoneNum){
//        List<Orders> orders = orderRepository.findOrdersByUserIdIs(phoneNum);
//        return new Response<>(true,"操作成功",orders);
//    }
    @Autowired
    public OrderController(OrderService orderService,UserService userService){
        this.userService = userService;
        this.orderService = orderService;
    }

    //添加账单
    @PostMapping("/addOrder")
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

    //修改账单
    @PutMapping("/modifyOrder/{id}")
    public Response<?> modifyOrder(@RequestBody JSONObject addOrderJson,@PathVariable int id){

        Integer month = (Integer) addOrderJson.get("month");
        Integer day = (Integer) addOrderJson.get("day");
        String clock = (String) addOrderJson.get("clock");
        Double money = Double.valueOf(addOrderJson.get("money").toString());
        String bankName = (String) addOrderJson.get("bankName");
        String orderRemark = (String) addOrderJson.get("orderRemark");
        String costType = (String) addOrderJson.get("costType");

        //根据主键id找到对应的账单
        Orders pendingModifyOrder = orderService.getOrderById(id);
        //修改
        pendingModifyOrder.setMonth(month);
        pendingModifyOrder.setDay(day);
        pendingModifyOrder.setClock(clock);
        pendingModifyOrder.setMoney(money);
        pendingModifyOrder.setBankName(bankName);
        pendingModifyOrder.setOrderRemark(orderRemark);
        pendingModifyOrder.setCostType(costType);

        //保存
        return orderService.saveOrder(pendingModifyOrder);
    }

    //删除账单
    @DeleteMapping("/deleteOrder/{id}")
    public Response<?> deleteOrder(@PathVariable int id){
        Integer delete=orderService.deleteOrderById(id);
        return new Response<>(delete==1,delete==1?"删除成功":"删除失败,请检查是否有该账单");
    }

    //查询家庭账单
    @GetMapping("/findMonthFamilyOrders/{familyId}/{month}")
    public Response<?> findMonthFamilyOrders(@PathVariable String familyId,@PathVariable String month){
        //先找到家庭的用户
        List<User> familyUsers = (List<User>) userService.getUsersByFamilyId(familyId).getData();
        //在根据用户的id查找到所有的账单
        List<Orders> familyOrders = new ArrayList<>();
        for(int i = 0;i < familyUsers.size();i++){
            List<Orders> userOrders = (List<Orders>) orderService.findOrdersByUserIdAndMonth(familyUsers.get(i).getPhoneNum(),Integer.valueOf(month));
            familyOrders.addAll(userOrders);
        }
        //排序一下,按照年月日排序
        Collections.sort(familyOrders,(a,b)->{
            if(a.getYear()!=b.getYear()){
                return b.getYear()-a.getYear();
            }
            else if(a.getMonth()!=b.getMonth()){
                return b.getMonth()-a.getMonth();
            }
            else {
                return b.getDay() - a.getDay();
            }
        });
//        System.out.println(familyOrders.size());
        return new Response<>(true,"查询成功",familyOrders);
    }

}
