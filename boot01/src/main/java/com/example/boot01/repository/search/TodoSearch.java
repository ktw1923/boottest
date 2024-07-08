package com.example.boot01.repository.search;

import com.example.boot01.dto.PageDTO;
import com.example.boot01.entity.Todo;
import org.springframework.data.domain.Page;


public interface TodoSearch {

    Page<Todo> search(PageDTO pageDTO);
}
