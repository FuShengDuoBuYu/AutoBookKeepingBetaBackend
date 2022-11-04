package com.example.autobookkeepingbetabackend.repository;

import com.example.autobookkeepingbetabackend.Entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem,String> {
    List<TodoItem> findTodoItemsByFamilyId(String familyId);
    TodoItem findTodoItemByIdIs(Integer todoItemId);
}
