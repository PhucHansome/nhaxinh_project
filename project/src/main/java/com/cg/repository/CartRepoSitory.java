package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepoSitory extends JpaRepository<Cart,Long> {

}
