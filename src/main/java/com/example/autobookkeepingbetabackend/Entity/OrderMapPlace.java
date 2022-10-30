package com.example.autobookkeepingbetabackend.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class OrderMapPlace implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String userId;

    @Column
    private Double money;

    @Column
    private Integer orderId;
    public OrderMapPlace(Double latitude, Double longitude, String userId, Double money,Integer orderId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
        this.money = money;
        this.orderId = orderId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public OrderMapPlace() {
    }
}
