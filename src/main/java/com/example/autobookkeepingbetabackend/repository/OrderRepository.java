package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findOrdersByUserIdIs(String userId);
    List<Orders> findOrdersByUserIdAndMonth(String userId,int month);
    Orders findOrdersById(int id);
    Integer deleteOrdersById(int id);
}
