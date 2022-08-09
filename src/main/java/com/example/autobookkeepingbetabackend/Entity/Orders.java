package com.example.autobookkeepingbetabackend.Entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private int day;

    @Column
    private String clock;

    @Column
    private Double money;

    @Column
    private String bankName;

    @Column
    private String orderRemark;

    @Column
    private String costType;

    @Column
    private String userId;

    public Orders(int year, int month, int day, String clock, Double money, String bankName, String orderRemark, String costType, String userId) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.clock = clock;
        this.money = money;
        this.bankName = bankName;
        this.orderRemark = orderRemark;
        this.costType = costType;
        this.userId = userId;
    }
}