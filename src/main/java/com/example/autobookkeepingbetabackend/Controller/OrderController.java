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
}
