package com.example.autobookkeepingbetabackend.Service;


import com.example.autobookkeepingbetabackend.Entity.NotificationEntry;
import com.example.autobookkeepingbetabackend.Util.Response;

public interface NotificationEntryService {
    public Response<?> findNotificationEntriesByReceiverIdAndIsSent(String receiverId);
    public Response<?> saveNotificationEntry(NotificationEntry notificationEntry);
}
