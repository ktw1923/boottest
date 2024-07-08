package com.example.boot01.repository;

import com.example.boot01.entity.Todo;
import com.example.boot01.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> , TodoSearch {
}
