package com.example.autobookkeepingbetabackend.Service;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import com.example.autobookkeepingbetabackend.Util.Response;


public interface OrderService {
    Response<?> saveOrder(Orders order);
}
