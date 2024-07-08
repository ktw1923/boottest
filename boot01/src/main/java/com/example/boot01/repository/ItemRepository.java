package com.example.boot01.repository;

import com.example.boot01.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @EntityGraph(attributePaths = "imgList")
    @Query("select it from Item it where it.pnum = :pnum")
    Optional<Item> selectOne(@Param("pnum") Long pnum);

    //item 과 관련된 imgList를 같이 로드하겠다
    //pnum기준으로 Item테이블 조회하면서 imgList 함께 가져오겠다

    //left join
    @Modifying
    @Query("update Item it set it.pflag = :pflag where it.pnum = :pnum")
    void updateToDelete(@Param("pnum") Long pnum, @Param("pflag") boolean pflag);

    @Query("select it, pi from Item it left join it.imgList pi where pi.itemnum = 0 and it.pflag = false")
    Page<Object []> selectList(Pageable pageable);




}
