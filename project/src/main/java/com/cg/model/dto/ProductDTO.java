package com.cg.model.dto;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String id;

    private String title;

    private BigDecimal price;

    private BigDecimal quantity;

    private String status;

    private String description;

    private String fileName;
    
    private String fileFolder;

    private String fileUrl;

    private String cloudId;

    private String fileProductId;

    private MultipartFile file;

    private String fileType;
    
    public Product toProduct(){
        return new Product()
                .setId(id);
    }

    public ProductMedia toProductmedia(){
        return new ProductMedia()
                .setId(fileProductId)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloundId(cloudId)
                .setFileType(fileType);
    }
}
