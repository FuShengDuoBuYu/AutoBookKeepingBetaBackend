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
}
