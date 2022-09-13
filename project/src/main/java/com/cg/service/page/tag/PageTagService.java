package com.cg.service.page.tag;

import com.cg.model.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageTagService {
    Page<TagDTO> findAllProductDTONoImage( Pageable pageable);

    Page<TagDTO> findALl(int choice, int option , String title, Pageable pageable);
}
