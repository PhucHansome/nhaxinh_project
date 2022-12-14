package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.*;
import com.cg.service.Tag.TagService;
import com.cg.service.category.CategoryService;
import com.cg.service.productColor.ProductColorService;
import com.cg.service.productmedia.ProductMediaService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private ProductColorService productColorService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMediaService productMediaService;

    @Autowired
    private TagService tagService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping
    private ResponseEntity<?> findAll() {
        try {
            List<ProductDTO> productDTOS = productService.findAllProductDTONoImage();

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product-status-active")
    private ResponseEntity<?> findAllProductActive() {
        try {
            List<ProductDTO> productDTOS = productService.findAllProductDTOByStatus("Còn Hàng");

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        Optional<ProductDTO> productDTOOptional = productService.findProductDTOById(id);
        if (!productDTOOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/product/slug/{slug}")
    public ResponseEntity<?> findProductBySlug(@PathVariable String slug) {
        Optional<ProductDTO> productDTOOptional = productService.findProductDTOBySlug(slug);
        if (!productDTOOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/product/search/{title}")
    public ResponseEntity<?> searchByTitleInline(@PathVariable String title) {
        try {
            title = "%" + title + "%";
            List<TagDTO> productList = tagService.searchProductDTOByTitle(title);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm", HttpStatus.OK);
        }
    }

    @GetMapping("/product/{code}")
    public ResponseEntity<?> findProductByCode(@PathVariable String code) {
        Optional<ProductDTO> productDTOOptional = productService.findProductDTOByCode(code);
        if (!productDTOOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/product/tag/{idProduct}")
    public ResponseEntity<?> findTagByProductId(@PathVariable String idProduct) {
        Optional<TagDTO> productDTOOptional = tagService.findTagDTOByProductId(idProduct);
        if (!productDTOOptional.isPresent()) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/productmedia/{id}")
    public ResponseEntity<?> findProductMediaByIdProduct(@PathVariable String id) {
        List<ProductMediaDTO> productMediaDTOList = productMediaService.findAllByProductIdOrderByTsDesc(id);
        if (productMediaDTOList.isEmpty()) {
            throw new DataInputException("Product is not found");
        }
        return new ResponseEntity<>(productMediaDTOList, HttpStatus.OK);
    }

    @GetMapping("/product-color")
    private ResponseEntity<?> findAllProductColor() {
        try {
            List<ProductColorDTO> productColorDTOS = productColorService.findAllProductColorDTO();

            return new ResponseEntity<>(productColorDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product-media/{id}")
    private ResponseEntity<?> findAllProductMedia(@PathVariable String id) {
        List<ProductMediaDTO> productMediaDTO = productMediaService.findAllByProductIdOrderByTsDesc(id);
        return new ResponseEntity<>(productMediaDTO, HttpStatus.OK);
    }

    @GetMapping("/product-image/{product_mediaID}")
    private ResponseEntity<?> getProductImage(@PathVariable String product_mediaID) {
        List<ProductMediaDTO> productMedia = productMediaService.findAllByProductId(product_mediaID);
        return new ResponseEntity<>(productMedia, HttpStatus.OK);
    }

    @PostMapping("/{idCategory}/{idProductColor}/{TagValue}")
    public ResponseEntity<?> create(ProductDTO productDTO, @PathVariable Long idCategory, @PathVariable Long idProductColor, @PathVariable String TagValue, BindingResult bindingResult) {
        Optional<CategoryDTO> optionalCategoryDTO = categoryService.findCategoryDTOById(idCategory);
        productDTO.setCategory(optionalCategoryDTO.get());

        Optional<ProductColorDTO> productColorDTO = productColorService.findProductColorDTOById(idProductColor);
        productDTO.setProductColor(productColorDTO.get());

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<ProductDTO> productDTO1 = productService.findProductDTOByCode(productDTO.getCode());
        if (productDTO1.isPresent()) {
            throw new DataInputException("Mã code sản phẩm này đã tồn tại vui lòng Nhập lại thông tin");
        }

        Optional<ProductDTO> productDTO2 = productService.findProductDTOBySlug(productDTO.getSlug());
        if (productDTO2.isPresent()) {
            throw new DataInputException("Tên sản phẩm này đã tồn tại vui lòng Nhập lại thông tin");
        }

        List<String> errors = new ArrayList<>();
        if (!productDTO.getImage().equals("null")) {
            errors.add("dữ liệu lỗi");
        }

        if (productDTO.getMaterial().isEmpty()) {
            errors.add("Vật liệu không được để trống");
        }

        if (productDTO.getCode().isEmpty()) {
            errors.add("Mã sản phẩm không được để trống");
        }

        if (!productDTO.getCode().matches("([A-Z]){2}[-]{1}[3]{1}[1]{1}[*]{1}\\d{8}")) {
            errors.add("Mã code sản phẩm Không đúng định dạng! Vd:AB-31*034567987(AB là bắt buộc chữ cái in hoa -31* à quy ước bắt buộc và 8 số bất kỳ)");
        }

        if (productDTO.getDescription().isEmpty()) {
            errors.add("Mô tả sản phẩm không được để trống!");
        }

        if (productDTO.getTitle().isEmpty()) {
            errors.add("Tên sản phẩm không được để trống");
        }

        if (errors.size() > 0) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }



        if (errors.size() == 0) {
            try {
                Product createdProduct = productService.create(TagValue, productDTO);

                Optional<ProductMedia> productMediaOptional = productMediaService.findTopByProductOrderByTsAsc(createdProduct);

                if (!productMediaOptional.isPresent()) {
                    throw new DataInputException("Product creation information is not valid, please check the information again");
                }

                createdProduct.setImage(productMediaOptional.get().getFileUrl());
                productService.save(createdProduct);

                return new ResponseEntity<>(createdProduct.toProductDTO(), HttpStatus.CREATED);

            } catch (DataIntegrityViolationException e) {
                throw new DataInputException("Product creation information is not valid, please check the information again");
            }
        }
        return null;
    }

    @PutMapping("/put/{idCategory}/{idProductColor}/{TagValue}")
    public ResponseEntity<?> update(ProductDTO productDTO, @PathVariable Long idCategory, @PathVariable Long idProductColor, @PathVariable String TagValue, BindingResult bindingResult) {
        Optional<CategoryDTO> optionalCategoryDTO = categoryService.findCategoryDTOById(idCategory);
        productDTO.setCategory(optionalCategoryDTO.get());

        Optional<ProductColorDTO> productColorDTO = productColorService.findProductColorDTOById(idProductColor);
        productDTO.setProductColor(productColorDTO.get());
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);
        List<String> errors = new ArrayList<>();
        if (!productDTO.getImage().equals("null")) {
            errors.add("dữ liệu lỗi");
        }

        if (productDTO.getMaterial().isEmpty()) {
            errors.add("Vật liệu không được để trống");
        }

        if (productDTO.getCode().isEmpty()) {
            errors.add("Mã sản phẩm không được để trống");
        }

        if (!productDTO.getCode().matches("([A-Z]){2}[-]{1}[3]{1}[1]{1}[*]{1}\\d{8}")) {
            errors.add("Mã code sản phẩm Không đúng định dạng! Vd:AB-31*034567987(AB là bắt buộc chữ cái in hoa -31* à quy ước bắt buộc và 8 số bất kỳ)");
        }

        if (productDTO.getDescription().isEmpty()) {
            errors.add("Mô tả sản phẩm không được để trống!");
        }

        if (productDTO.getTitle().isEmpty()) {
            errors.add("Tên sản phẩm không được để trống");
        }

        if (errors.size() > 0) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (errors.size() == 0) {
            try {
                Product updateProduct = productService.updateProduct(TagValue, productDTO);

                Optional<ProductMedia> productMediaImage = productMediaService.findTopByProductOrderByTsAsc(updateProduct);

                if (!productMediaImage.isPresent()) {
                    throw new DataInputException("Product creation information is not valid, please check the information again");
                }

                updateProduct.setImage(productMediaImage.get().getFileUrl());
                productService.save(updateProduct);

                return new ResponseEntity<>(updateProduct.toProductDTO(), HttpStatus.ACCEPTED);

            } catch (DataIntegrityViolationException e) {
                throw new DataInputException("Product creation information is not valid, please check the information again");
            }
        }
        return null;
    }


    @DeleteMapping("/delete-soft-product/{id}")
    public ResponseEntity<?> deleteSoft(@PathVariable String id) {
        Optional<ProductDTO> productDTO = productService.findProductDTOById(id);
        if (!productDTO.isPresent()) {
            throw new DataInputException("Sản phẩm này không tồn tại");
        }
        productService.deleteSoft(productDTO.get().toProduct());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }
}
