package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.NotificationEntry;
import com.example.autobookkeepingbetabackend.Entity.TodoItem;
import com.example.autobookkeepingbetabackend.Service.NotificationEntryService;
import com.example.autobookkeepingbetabackend.Service.TodoItemService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoItemController {
    TodoItemService todoItemService;
    NotificationEntryService notificationEntryService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService, NotificationEntryService notificationEntryService){
        this.todoItemService = todoItemService;
        this.notificationEntryService = notificationEntryService;
    }

    //添加待办事项
    @PostMapping("/todoItem/addTodoItem")
    public Response<?> addTodoItem(@RequestBody JSONObject addTodoItemJson){
        String itemTitle = (String) addTodoItemJson.get("itemTitle");
        String familyId = (String) addTodoItemJson.get("familyId");
        String posterId = (String) addTodoItemJson.get("posterId");
        //获取系统时间
        String postTime = Utils.getCurrentTime();
        String handlerId = (String) addTodoItemJson.get("handlerId");
        Boolean isFinished = false;
        String handleTime = "";
        //设置待办事项
        TodoItem addTodoItem = new TodoItem(itemTitle,postTime,posterId,handlerId,isFinished,handleTime,familyId);
        //设置待办通知
        String notificationTitle = "新增待办";
        String notificationContent = "您有一条新的待办事项";
        String notificationType = "待办";
        NotificationEntry addNotificationEntry = new NotificationEntry(notificationTitle,notificationContent,notificationType,handlerId,familyId,false,"");
        notificationEntryService.saveNotificationEntry(addNotificationEntry);
        return todoItemService.saveTodoItem(addTodoItem);
    }

    //获取所有的待办事项
    @GetMapping("/todoItem/getFamilyTodoItem/{familyId}")
    public Response<?> getFamilyTodoItem(@PathVariable String familyId){
        System.out.println("111");
        Response<?> response = todoItemService.findFamilyTodoItem(familyId);
        System.out.println("222");
        return response;
    }

    //完成一个待办事项
    @GetMapping("/todoItem/finishTodoItem/{itemId}")
    public Response<?> finishTodoItem(@PathVariable Integer itemId){
        String handleTime = Utils.getCurrentTime();
        TodoItem pendingModifyTodoItem = todoItemService.getTodoItemById(itemId);
        pendingModifyTodoItem.setHandleTime(handleTime);
        pendingModifyTodoItem.setFinished(true);
        //设置待办通知
        String notificationTitle = "完成待办";
        String notificationContent = "您有一条待办事项已完成";
        String notificationType = "待办";
        NotificationEntry addNotificationEntry = new NotificationEntry(notificationTitle,notificationContent,notificationType,pendingModifyTodoItem.getPosterId(),pendingModifyTodoItem.getFamilyId(),false,"");
        notificationEntryService.saveNotificationEntry(addNotificationEntry);
        return todoItemService.saveTodoItem(pendingModifyTodoItem);
    }

    //查看待办通知
    @GetMapping("todoItem/getNotificationEntry/{userId}")
    public Response<?> getNotificationEntry(@PathVariable String userId){
        List<NotificationEntry> notificationEntries = (List<NotificationEntry>) notificationEntryService.findNotificationEntriesByReceiverIdAndIsSent(userId).getData();
        for(NotificationEntry notificationEntry:notificationEntries){
            notificationEntry.setIsSent(true);
            notificationEntry.setSendTime(Utils.getCurrentTime());
            notificationEntryService.saveNotificationEntry(notificationEntry);
        }
        return new Response<>(true,"查询成功",notificationEntries);
    }
}
