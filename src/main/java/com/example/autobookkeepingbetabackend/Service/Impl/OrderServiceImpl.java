package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Response<?> saveOrder(Orders order) {
        try {
            Orders o = orderRepository.save(order);
            return new Response<>(true,"添加成功",o.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }

    @Override
    public Orders getOrderById(int id) {
        return orderRepository.findOrdersById(id);
    }

    @Transactional
    @Override
    public Integer deleteOrderById(int id) {
        return orderRepository.deleteOrdersById(id);
    }

    @Override
    public List<Orders> findOrdersByUserId(String userId) {

        return orderRepository.findOrdersByUserIdIs(userId);
    }

    @Override
    public List<Orders> findOrdersByUserIdAndMonthAndYear(String userId, int month,int year) {
        return orderRepository.findOrdersByUserIdAndMonthAndYear(userId,month,year);
    }

    @Override
    public List<Orders> findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(String userId, int year, int month, int day, String orderRemark, String[] costType) {
        List<Orders> res = new ArrayList<>();
        if(costType.length == 0 || costType[0].equals("")||ifContain(costType,"不限")){
            res.addAll(orderRepository.findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContains(userId,year,month,day,orderRemark));
        }
        else {
            for (String s:costType){
                System.out.println(s);
                res.addAll(orderRepository.findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(userId,year,month,day,orderRemark,s));
            }
        }
        return res;
    }

    @Override
    public List<Orders> findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(String userId, int year, int month, String orderRemark, String[] costType) {
        List<Orders> res = new ArrayList<>();
        if(costType.length == 0 || costType[0].equals("")||ifContain(costType,"不限")){
            res.addAll(orderRepository.findOrdersByUserIdAndYearAndMonthAndOrderRemarkContains(userId,year,month,orderRemark));
        }
        else {
            for (String s:costType){
                res.addAll(orderRepository.findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(userId,year,month,orderRemark,s));
            }
        }
        return res;
    }

    @Override
    public List<Orders> findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(String userId, int year, String orderRemark, String[] costType) {
        List<Orders> res = new ArrayList<>();
        if(costType.length == 0 || costType[0].equals("")||ifContain(costType,"不限")){
            res.addAll(orderRepository.findOrdersByUserIdAndYearAndOrderRemarkContains(userId,year,orderRemark));
        }
        else {
            for (String s:costType){
                res.addAll(orderRepository.findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(userId,year,orderRemark,s));
            }
        }
        return res;
    }

    @Override
    public List<Orders> findOrdersByUserIdAndOrderRemarkContainsAndCostType(String userId, String orderRemark, String[] costType) {
        List<Orders> res = new ArrayList<>();
        if(costType.length == 0 || costType[0].equals("")||ifContain(costType,"不限")){
            res.addAll(orderRepository.findOrdersByUserIdAndOrderRemarkContains(userId,orderRemark));
        }
        else {
            for (String s:costType){
                res.addAll(orderRepository.findOrdersByUserIdAndOrderRemarkContainsAndCostType(userId,orderRemark,s));
            }
        }
        return res;
    }

    private boolean ifContain(String[] arr,String s){
        for (String a:arr){
            if (a.equals(s)){
                return true;
            }
        }
        return false;
    }
}
