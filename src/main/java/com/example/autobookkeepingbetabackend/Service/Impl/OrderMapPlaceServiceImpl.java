package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.OrderMapPlace;
import com.example.autobookkeepingbetabackend.Service.OrderMapPlaceService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.OrderMapPlaceReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapPlaceServiceImpl implements OrderMapPlaceService {

    OrderMapPlaceReposity orderMapPlaceReposity;

    @Autowired
    public OrderMapPlaceServiceImpl(OrderMapPlaceReposity orderMapPlaceReposity){
        this.orderMapPlaceReposity = orderMapPlaceReposity;
    }

    @Override
    public Response<?> addLBSOrder(OrderMapPlace orderMapPlace) {
        try {
            orderMapPlaceReposity.save(orderMapPlace);
            return new Response<>(true,"添加成功",orderMapPlace.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }

    @Override
    public Response<List<OrderMapPlace>> findTopNPersonalOrderMapPlace(String userId, int n) {
        try {
            List<OrderMapPlace> res = orderMapPlaceReposity.findOrderMapPlaceByUserIdIs(userId);
            res = res.subList(0,Math.min(n,res.size()));
            return new Response<>(true,"查询成功",res);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }

    @Override
    public void deleteOrderMapPlaceByOrderId(int orderId) {
        orderMapPlaceReposity.deleteOrderMapPlaceByOrderId(orderId);
    }

}
