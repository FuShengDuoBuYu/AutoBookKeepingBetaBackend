package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            orderRepository.save(order);
            return new Response<>(true,"操作成功",null);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }
}
