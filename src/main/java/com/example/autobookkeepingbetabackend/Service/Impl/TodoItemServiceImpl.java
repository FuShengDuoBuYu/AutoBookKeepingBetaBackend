package com.example.autobookkeepingbetabackend.Service.Impl;

import com.example.autobookkeepingbetabackend.Entity.TodoItem;
import com.example.autobookkeepingbetabackend.Service.TodoItemService;
import com.example.autobookkeepingbetabackend.Util.Response;
import com.example.autobookkeepingbetabackend.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoItemServiceImpl implements TodoItemService {
    TodoItemRepository todoItemRepository;

    @Autowired
    public TodoItemServiceImpl(TodoItemRepository todoItemRepository){
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public Response<?> saveTodoItem(TodoItem todoItem) {
        try {
            TodoItem t = todoItemRepository.save(todoItem);
            return new Response<>(true,"添加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null);
        }
    }

    @Override
    public Response<?> findFamilyTodoItem(String familyId) {
        try {
            return new Response<>(true,"查询成功",todoItemRepository.findTodoItemsByFamilyId(familyId));
        }
        catch (Exception e){
            e.printStackTrace();
            return new Response<>(false,e.toString(),null );
        }
    }

    @Override
    public TodoItem getTodoItemById(Integer todoItemId) {
        return todoItemRepository.findTodoItemByIdIs(todoItemId);
    }

}
