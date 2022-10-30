package com.example.autobookkeepingbetabackend.Service;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Util.Response;

import java.util.List;


public interface OrderService {
    Response<?> saveOrder(Orders order);

    public Orders getOrderById(int id);

    public Integer deleteOrderById(int id);

    public List<Orders> findOrdersByUserId(String userId);

    public List<Orders> findOrdersByUserIdAndMonthAndYear(String userId,int month,int year);

    public List<Orders> findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(String userId,int year,int month,int day,String orderRemark,String[] costType);

    public List<Orders> findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(String userId,int year,int month,String orderRemark,String[] costType);

    public List<Orders> findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(String userId,int year,String orderRemark,String[] costType);

    public List<Orders> findOrdersByUserIdAndOrderRemarkContainsAndCostType(String userId,String orderRemark,String[] costType);

    public Orders findOrdersById(int id);
}
