package com.example.autobookkeepingbetabackend.Service.Impl;


import com.example.autobookkeepingbetabackend.Entity.NotificationEntry;
import com.example.autobookkeepingbetabackend.Service.NotificationEntryService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.NotificationEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationEntryServiceImpl implements NotificationEntryService {

    NotificationEntryRepository notificationEntryRepository;

    public NotificationEntryServiceImpl(NotificationEntryRepository notificationEntryRepository){
        this.notificationEntryRepository = notificationEntryRepository;
    }

    @Override
    public Response<?> findNotificationEntriesByReceiverIdAndIsSent(String receiverId) {
        return new Response<>(true,"查询成功",notificationEntryRepository.findNotificationEntriesByReceiverIdAndIsSentIsFalse(receiverId));
    }

    @Override
    public Response<?> saveNotificationEntry(NotificationEntry notificationEntry) {
        return new Response<>(true,"保存成功",notificationEntryRepository.save(notificationEntry));
    }
}
