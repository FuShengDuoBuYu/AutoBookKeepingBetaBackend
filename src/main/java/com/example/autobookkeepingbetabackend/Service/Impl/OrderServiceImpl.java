package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Service.OrderService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
