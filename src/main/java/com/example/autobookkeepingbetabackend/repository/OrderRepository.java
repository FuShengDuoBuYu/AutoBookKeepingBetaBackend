package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findOrdersByUserIdIs(String userId);
    List<Orders> findOrdersByUserIdAndMonthAndYear(String userId,int month,int year);
    List<Orders> findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContainsAndCostType(String userId,int year,int month,int day,String orderRemark,String costType);
    List<Orders> findOrdersByUserIdAndYearAndMonthAndOrderRemarkContainsAndCostType(String userId,int year,int month,String orderRemark,String costType);
    List<Orders> findOrdersByUserIdAndYearAndOrderRemarkContainsAndCostType(String userId,int year,String orderRemark,String costType);
    List<Orders> findOrdersByUserIdAndOrderRemarkContainsAndCostType(String userId,String orderRemark,String costType);
    List<Orders> findOrdersByUserIdAndYearAndMonthAndDayAndOrderRemarkContains(String userId,int year,int month,int day,String orderRemark);
    List<Orders> findOrdersByUserIdAndYearAndMonthAndOrderRemarkContains(String userId,int year,int month,String orderRemark);
    List<Orders> findOrdersByUserIdAndYearAndOrderRemarkContains(String userId,int year,String orderRemark);
    List<Orders> findOrdersByUserIdAndOrderRemarkContains(String userId,String orderRemark);
    Orders findOrdersById(int id);
    Integer deleteOrdersById(int id);
}
