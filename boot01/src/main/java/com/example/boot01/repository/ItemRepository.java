package com.example.boot01.repository;


import java.util.Optional;

import com.example.boot01.dto.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @EntityGraph(attributePaths = "imgList")
    @Query("select p from Item p where p.pnum = :pnum")
    Optional<Item> selectOne(@Param("pnum") Long pnum);

    @Modifying
    @Query("update Item p set p.deFlag = :flag where p.pnum = :pnum")
    void updateToDelete(@Param("pnum") Long pnum , @Param("flag") boolean flag);


    @Query("select p, pi  from Item p left join p.imgList pi  where pi.itemnum = 0 and p.deFlag = false ")
    Page<Object[]> selectList(Pageable pageable);
}
