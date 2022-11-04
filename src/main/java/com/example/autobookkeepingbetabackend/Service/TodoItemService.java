package com.example.autobookkeepingbetabackend.Service;


import com.example.autobookkeepingbetabackend.Entity.TodoItem;
import com.example.autobookkeepingbetabackend.Util.Response;

public interface TodoItemService {
    Response<?> saveTodoItem(TodoItem todoItem);
    Response<?> findFamilyTodoItem(String familyId);
    TodoItem getTodoItemById(Integer todoItemId);
}
