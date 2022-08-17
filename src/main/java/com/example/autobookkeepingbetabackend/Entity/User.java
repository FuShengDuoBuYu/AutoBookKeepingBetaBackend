package com.example.autobookkeepingbetabackend.Entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {

    @Id
    private String phoneNum;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String familyId;

    @Column
    private String familyIdentity;

    @Column
    private String portrait;

    @Column
    private String bankNumber;

    public User(){}

    public User(String phoneNum, String password, String nickname, String familyId, String familyIdentity, String portrait,String bankNumber) {
        this.phoneNum = phoneNum;
        this.password = password;
        this.nickname = nickname;
        this.familyId = familyId;
        this.familyIdentity = familyIdentity;
        this.portrait = portrait;
        this.bankNumber = bankNumber;
    }
}
