package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.OrderMapPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderMapPlaceReposity extends JpaRepository<OrderMapPlace,Long> {
    List<OrderMapPlace> findOrderMapPlaceByUserIdIs(String userId);

    @Transactional
    void deleteOrderMapPlaceByOrderId(int orderId);
}
