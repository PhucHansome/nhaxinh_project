package com.cg.model.dto;

import java.math.BigDecimal;

public interface IProductDTO {
    String getId();
    String getCode();
    String getTitle();
    BigDecimal getPrice();
    BigDecimal getQuantity();
    String getStatus();
    String getSlug();
    String getColor();
    String getCategory();
    String getCreatedAt();
    String getSize();
    String getMaterial();
    String getImage();
    String getDescription();
    String getFileId();
    String getFileName();
    String getFileFolder();
    String getFileUrl();
    String getFileType();
}
