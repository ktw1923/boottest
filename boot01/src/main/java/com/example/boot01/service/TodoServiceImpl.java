package com.example.boot01.service;


import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.dto.TodoDTO;
import com.example.boot01.entity.Todo;
import com.example.boot01.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository; //객체불변성


    @Override
    public TodoDTO get(Long todonum) {
        Optional<Todo> opt =todoRepository.findById(todonum);

        Todo todo=opt.orElseThrow();

        return entityToDTO(todo);
    }

    @Override
    public PageDTO2<TodoDTO> getList(PageDTO pageDTO) {
        Page<Todo> pageResult=todoRepository.search(pageDTO) ;

//김씨를 검색한 후 검색결과 -> 김씨가 쓴 타이틀, 내용, 날짜..

        List<TodoDTO> dtoList= pageResult.get()
                .map(todo->entityToDTO(todo))
                .collect(Collectors.toList());

        PageDTO2<TodoDTO> pageDTO2=PageDTO2.<TodoDTO> withAll()
                .dtoList(dtoList)
                .pageDTO(pageDTO)
                .total(pageResult.getTotalElements())
                .build();

        return pageDTO2;

    }

    @Override
    public Long register(TodoDTO todoDTO) {
        Todo todo=dtoToEntity(todoDTO);
        Todo todoResult=todoRepository.save(todo);
        return todoResult.getTodonum();
    }

    @Override
    public void modify(TodoDTO todoDTO) {

        Optional<Todo> optional=todoRepository.findById(todoDTO.getTodonum());

        Todo todo=optional.orElseThrow();

        todo.setTitle(todoDTO.getTitle());
        todo.setContent(todoDTO.getTitle());
        todo.setFinale(todoDTO.isFinale());
        todo.setDueDate(todoDTO.getDueDate());

        todoRepository.save(todo);
    }
    @Override
    public void delete(Long todonum) {

        todoRepository.deleteById(todonum);

    }
}
