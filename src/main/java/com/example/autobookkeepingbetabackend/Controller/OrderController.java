package com.example.autobookkeepingbetabackend.Controller;

import com.example.autobookkeepingbetabackend.Entity.Order_18916629734;
import com.example.autobookkeepingbetabackend.repository.Repository_18916629734;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private Repository_18916629734 repository_18916629734;

    @GetMapping("/[{phoneNum}/findall")
    public List<Order_18916629734> findall(@PathVariable Long phoneNum){
        List<Order_18916629734> users = repository_18916629734.findAll();
        return users;
    }

}
