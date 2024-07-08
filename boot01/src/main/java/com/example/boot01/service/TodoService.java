package com.example.boot01.service;

import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.dto.TodoDTO;
import com.example.boot01.entity.Todo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {
    TodoDTO get(Long todonum);

    PageDTO2<TodoDTO> getList(PageDTO pageDTO);

    Long register(TodoDTO todoDTO);

    void modify(TodoDTO todoDTO);

    void delete(Long todonum);



    //dto객체를 entity로 변경
    default Todo dtoToEntity(TodoDTO todoDTO){
        return
                Todo.builder()  //객체를 생성해서 리턴하는 함수
                        .todonum(todoDTO.getTodonum())
                        .title(todoDTO.getTitle())
                        .content(todoDTO.getContent())
                        .finale(todoDTO.isFinale())
                        .dueDate(todoDTO.getDueDate())
                        .build();
    }
    //entity를 dto객체로 변경
    default TodoDTO entityToDTO(Todo todo){
        return
                TodoDTO.builder()  //객체를 생성해서 리턴하는 함수
                        .todonum(todo.getTodonum())
                        .title(todo.getTitle())
                        .content(todo.getContent())
                        .finale(todo.isFinale())
                        .dueDate(todo.getDueDate())
                        .build();
    }
}
