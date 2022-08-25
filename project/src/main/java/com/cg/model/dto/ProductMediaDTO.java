package com.cg.model.dto;

import com.cg.model.Product;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

public class ProductMediaDTO {

    private String id;
    private String fileUrl;

    public ProductMediaDTO(String id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}
