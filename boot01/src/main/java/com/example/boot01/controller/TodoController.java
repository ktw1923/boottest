package com.example.boot01.controller;

import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.dto.TodoDTO;
import com.example.boot01.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bit/todo")
@RequiredArgsConstructor
public class TodoController {

    public final TodoService todoService;

    @GetMapping("/{todonum}")
    public TodoDTO get(@PathVariable(name="todonum") Long todonum){
        return todoService.get(todonum);
    }

    @GetMapping("/list")
    public PageDTO2<TodoDTO>  list(PageDTO pageDTO){
        return todoService.getList(pageDTO);
    }

    @PostMapping("/")
    public Map<String , Long> register(@RequestBody TodoDTO todoDTO){
        Long todonum=todoService.register(todoDTO);
        return Map.of("Todonum",todonum);
    }

    @PutMapping("/{todonum}")
    public Map<String , String> modify(@PathVariable(name="todonum") Long todonum,@RequestBody TodoDTO todoDTO){

        todoDTO.setTodonum(todonum);
        todoService.modify(todoDTO);
        return Map.of("final","success");

    }
    @DeleteMapping("/{todonum}")
    public Map<String , String> delete(@PathVariable(name="todonum") Long todonum){
        todoService.delete(todonum);
        return Map.of("final","success");
    }

}
