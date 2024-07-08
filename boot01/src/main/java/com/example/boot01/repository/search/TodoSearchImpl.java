package com.example.boot01.repository.search;

import com.example.boot01.dto.PageDTO;
import com.example.boot01.entity.QTodo;
import com.example.boot01.entity.Todo;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl(){
        super(Todo.class);
    }

    @Override
    public Page<Todo> search(PageDTO pageDTO) {

        QTodo qTodo=QTodo.todo; //쿼리를 이용하기 위한 객체 생성

        JPQLQuery<Todo> jpqlQuery = from(qTodo);

        Pageable pageable = PageRequest.of(pageDTO.getPage()-1,
                pageDTO.getNum(),
                Sort.by("todonum").descending());

        this.getQuerydsl().applyPagination(pageable,jpqlQuery);

        List<Todo> list =jpqlQuery.fetch();

        long total= jpqlQuery.fetchCount();

        return new PageImpl<>(list, pageable, total);

    }
}
