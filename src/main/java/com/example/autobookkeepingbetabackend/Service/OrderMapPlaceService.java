package com.example.autobookkeepingbetabackend.Service;


import com.example.autobookkeepingbetabackend.Entity.OrderMapPlace;
import com.example.autobookkeepingbetabackend.Util.Response;

import java.util.List;

public interface OrderMapPlaceService {
    public Response<?> addLBSOrder(OrderMapPlace orderMapPlace);

    public Response<List<OrderMapPlace>> findTopNPersonalOrderMapPlace(String userId,int n);

    public void deleteOrderMapPlaceByOrderId(int orderId);
}
