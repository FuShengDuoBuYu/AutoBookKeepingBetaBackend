package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.NotificationEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationEntryRepository extends JpaRepository<NotificationEntry,Long> {
    List<NotificationEntry> findNotificationEntriesByReceiverIdAndIsSentIsFalse(String receiverId);
}
