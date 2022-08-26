package com.cg.model.dto;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductMediaDTO {

    private String id;

    private String fileName;

    private String fileFolder;

    private String fileUrl;

    private String fileType;

    private String cloundId;

    private Long ts ;

    private ProductDTO product;

    public ProductMediaDTO(String id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }

    public ProductMedia toProductMedia() {
       return new ProductMedia()
               .setId(id)
               .setCloundId(cloundId)
               .setFileType(fileType)
               .setFileUrl(fileUrl)
               .setProduct(product.toProduct())
               .setFileFolder(fileFolder)
               .setFileName(fileName)
               .setTs(ts)
               ;
   }
}
