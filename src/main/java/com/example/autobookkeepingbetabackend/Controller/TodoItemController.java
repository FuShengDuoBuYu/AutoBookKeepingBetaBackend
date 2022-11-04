package com.example.autobookkeepingbetabackend.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.autobookkeepingbetabackend.Entity.TodoItem;
import com.example.autobookkeepingbetabackend.Service.TodoItemService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoItemController {
    TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService){
        this.todoItemService = todoItemService;
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
        String posterPortrait = (String) addTodoItemJson.get("posterPortrait");
        String handlerPortrait = (String) addTodoItemJson.get("handlerPortrait");
        Boolean isFinished = false;
        String handleTime = "";

        TodoItem addTodoItem = new TodoItem(itemTitle,postTime,posterId,handlerId,posterPortrait,handlerPortrait,isFinished,handleTime,familyId);
        return todoItemService.saveTodoItem(addTodoItem);
    }

    //获取所有的待办事项
    @GetMapping("/todoItem/getFamilyTodoItem/{familyId}")
    public Response<?> getFamilyTodoItem(@PathVariable String familyId){

        return todoItemService.findFamilyTodoItem(familyId);
    }

    //完成一个待办事项
    @GetMapping("/todoItem/finishTodoItem/{itemId}")
    public Response<?> finishTodoItem(@PathVariable Integer itemId){
        String handleTime = Utils.getCurrentTime();

        TodoItem pendingModifyTodoItem = todoItemService.getTodoItemById(itemId);
        pendingModifyTodoItem.setHandleTime(handleTime);
        pendingModifyTodoItem.setFinished(true);
        return todoItemService.saveTodoItem(pendingModifyTodoItem);
    }
}
