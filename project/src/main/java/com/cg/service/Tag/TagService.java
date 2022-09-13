package com.cg.service.Tag;


import com.cg.model.Tag;
import com.cg.model.dto.TagDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface TagService extends IGeneralService<Tag> {

    Optional<TagDTO> findTagDTOByProductId(String id);

    List<TagDTO> searchProductDTOByTitle(String title);
}
