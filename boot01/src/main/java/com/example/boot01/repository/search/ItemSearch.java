package com.example.boot01.repository.search;

import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.entity.Todo;
import org.springframework.data.domain.Page;

public interface ItemSearch {

    PageDTO2<ItemDTO> search(PageDTO pageDTO);

}
