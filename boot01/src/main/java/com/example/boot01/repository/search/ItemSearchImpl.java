package com.example.boot01.repository.search;

import com.example.boot01.dto.ItemDTO;
import com.example.boot01.dto.PageDTO;
import com.example.boot01.dto.PageDTO2;
import com.example.boot01.entity.Item;
import com.example.boot01.entity.QItem;
import com.example.boot01.entity.QItemImage;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ItemSearchImpl extends QuerydslRepositorySupport implements ItemSearch {

    public ItemSearchImpl() {
        super(Item.class);
    }

    @Override
    public PageDTO2<ItemDTO> search(PageDTO pageDTO) {

        Pageable pageable = PageRequest.of(
                pageDTO.getPage() - 1,
                pageDTO.getNum(),
                Sort.by("pnum").descending());

        QItem qItem = QItem.item;
        QItemImage qItemImage = QItemImage.itemImage;

        JPQLQuery<Item> query = from(qItem);
        query.leftJoin(qItem.imgList, qItemImage); // qItem.imgList를 qItemImage로 간주
        //상품인데 이미지가 없을 수 있기 때문에 left join 사용

        query.where(qItemImage.itemnum.eq(0));
        //이미지 0번째(첫번째)것만 가져옴

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        List<Item> itemList = query.fetch();

        long count = query.fetchCount();

        log.info(itemList);
        return null;
    }
}
