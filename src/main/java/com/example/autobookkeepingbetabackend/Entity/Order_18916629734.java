package com.example.autobookkeepingbetabackend.Entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class Order_18916629734 implements Serializable {
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
    private BigDecimal money;

    @Column
    private String bankName;

    @Column
    private String orderRemark;

    @Column
    private String costType;
}
