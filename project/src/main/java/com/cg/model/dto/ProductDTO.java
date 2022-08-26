package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.ProductColor;
import com.cg.model.ProductMedia;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Data
public class ProductDTO {
    private String id;

    private String code;

    private String title;

    private BigDecimal price;

    private BigDecimal quantity;

    private String status;

    private String description;

    private String size;

    private String material;

    private String slug;

    private String image;

    private CategoryDTO category;

    private ProductColorDTO productColor;

    private String fileName;
    
    private String fileFolder;

    private String fileUrl;

    private String cloudId;

    private String fileProductId;

    private List<MultipartFile> files;

    private MultipartFile file;

    private String fileType;

    private Date createdAt;

    public ProductDTO(String id, String code, String title, BigDecimal price, BigDecimal quantity, String status, String description, String size, String material, String slug, String image, Category category, ProductColor productColor , Date createdAt) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.description = description;
        this.size = size;
        this.material = material;
        this.slug = slug;
        this.image = image;
        this.category = category.categoryDTO();
        this.productColor = productColor.toProductColorDTO();
        this.createdAt = createdAt;
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setCode(code)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setStatus(status)
                .setDescription(description)
                .setSize(size)
                .setMaterial(material)
                .setSlug(slug)
                .setImage(image)
                .setProductColor(productColor.toProductColor())
                .setCategory(category.toCategory())
                ;
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
