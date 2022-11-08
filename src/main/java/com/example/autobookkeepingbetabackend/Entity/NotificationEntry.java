package com.example.autobookkeepingbetabackend.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class NotificationEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String notificationTitle;

    @Column
    private String notificationContent;

    @Column
    private String notificationType;

    @Column
    private String receiverId;

    @Column
    private String receiverFamilyId;

    @Column
    private Boolean isSent;

    @Column
    private String sendTime;

    public NotificationEntry(String notificationTitle, String notificationContent, String notificationType, String receiverId, String receiverFamilyId, Boolean isSent, String sendTime) {
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.notificationType = notificationType;
        this.receiverId = receiverId;
        this.receiverFamilyId = receiverFamilyId;
        this.isSent = isSent;
        this.sendTime = sendTime;
    }

    public NotificationEntry() {
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverFamilyId() {
        return receiverFamilyId;
    }

    public void setReceiverFamilyId(String receiverFamilyId) {
        this.receiverFamilyId = receiverFamilyId;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
