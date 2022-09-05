package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findOrdersByUserIdIs(String userId);
    List<Orders> findOrdersByUserIdAndMonthAndYear(String userId,int month,int year);
    Orders findOrdersById(int id);
    Integer deleteOrdersById(int id);
}
