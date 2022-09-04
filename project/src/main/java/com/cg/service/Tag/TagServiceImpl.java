package com.cg.service.Tag;

import com.cg.model.Product;
import com.cg.model.Tag;
import com.cg.model.dto.TagDTO;
import com.cg.repository.ProductRepository;
import com.cg.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id) ;
    }

    @Override
    public Tag getById(Long id) {
        return null;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(Tag tag) {

    }

    @Override
    public Optional<TagDTO> findTagDTOByProductId(String id) {
        return tagRepository.findTagDTOByProductId(id);
    }
}
