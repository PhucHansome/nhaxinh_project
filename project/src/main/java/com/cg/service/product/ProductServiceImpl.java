package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.Tag;
import com.cg.model.dto.*;
import com.cg.model.enums.FileType;
import com.cg.repository.CartItemRepository;
import com.cg.repository.ProductMediaRepository;
import com.cg.repository.ProductRepository;
import com.cg.repository.TagRepository;
import com.cg.service.upload.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMediaRepository productMediaRepository;

   @Autowired
   private TagRepository tagRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private CartItemRepository cartItemRepository ;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Iterable<IProductDTO> findAllIProductDTO() {
        return productRepository.findAllIProductDTO();
    }

    @Override
    public IProductDTO findIProductDTOById(String id) {
        return productRepository.findIProductDTOById(id);
    }

    @Override
    public Product create(String tagValue,ProductDTO productDTO) {
        List<MultipartFile> fileList = productDTO.getFiles();
        Product product = productRepository.save(productDTO.toProduct());
        for (MultipartFile file : fileList) {

            String fileType = file.getContentType();
            assert fileType != null;

            fileType = fileType.substring(0, 5);

            productDTO.setFileType(fileType);
            productDTO.setFile(file);

            ProductMedia   productMedia = productMediaRepository.save(productDTO.toProductmedia());


            if (fileType.equals(FileType.IMAGE.getValue())) {
                uploadAndSaveProductImage(productDTO, product, productMedia);
            }

            if (fileType.equals(FileType.VIDEO.getValue())) {
                uploadAndSaveProductVideo(productDTO, product, productMedia);
            }
        }
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setProduct(product);
        tag.setName(tagValue);
        tagRepository.save(tag);
        return product;
    }

    @Override
    public void delete(Product product) throws IOException {
        Optional<ProductMedia> productImageVideo = productMediaRepository.findByProduct(product);

        if(productImageVideo.isPresent()){
            String publicId = productImageVideo.get().getCloundId();
            if(productImageVideo.get().getFileType().equals(FileType.IMAGE.getValue())){
                uploadService.destroyImage(publicId,uploadUtils.buildImageDestroyParams(product,publicId));
            }

            if(productImageVideo.get().getFileType().equals(FileType.VIDEO.getValue())){
                uploadService.destroyVideo(publicId, uploadUtils.buildVideoDestroyParams(product, publicId));
            }

            productMediaRepository.delete(productImageVideo.get());
        }
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> findAllProductDTONoImage() {
        return productRepository.findAllProductDTONoImage();
    }

    @Override
    public List<ProductDTO> findAllProductDTOByStatus(String status) {
        return productRepository.findAllProductDTOByStatus(status);
    }

    @Override
    public Optional<ProductDTO> findProductDTOBySlug(String slug) {
        return productRepository.findProductDTOBySlug(slug);
    }

    @Override
    public Product save(Product Product) {
        return productRepository.save(Product);
    }

    @Override
    public Optional<ProductDTO> findProductDTOById(String id) {
        return productRepository.findProductDTOById(id);
    }

    @Override
    public Optional<ProductDTO> findProductDTOByCode(String code) {
        return productRepository.findProductDTOByCode(code);
    }

    private void uploadAndSaveProductImage(ProductDTO productDTO, Product product, ProductMedia productMedia) {
        try {
            Map uploadResult = uploadService.uploadImage(productDTO.getFile(), uploadUtils.buildImageUploadParams(productMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productMedia.setFileName(productMedia.getId() + "." + fileFormat);
            productMedia.setFileUrl(fileUrl);
            productMedia.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            productMedia.setCloundId(productMedia.getFileFolder() + "/" + productMedia.getId());
            productMedia.setProduct(product);
            productMediaRepository.save(productMedia);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload h??nh ???nh th???t b???i");
        }
    }

    private void uploadAndSaveProductVideo(ProductDTO productDTO, Product product, ProductMedia productMedia) {
        try {
            Map uploadResult = uploadService.uploadVideo(productDTO.getFile(), uploadUtils.buildVideoUploadParams(productMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productMedia.setFileName(productMedia.getId() + "." + fileFormat);
            productMedia.setFileUrl(fileUrl);
            productMedia.setFileFolder(UploadUtils.VIDEO_UPLOAD_FOLDER);
            productMedia.setCloundId(productMedia.getFileFolder() + "/" + productMedia.getId());
            productMedia.setProduct(product);
            productMediaRepository.save(productMedia);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload video th???t b???i");
        }
    }

    @Override
    public List<ProductDTO> searchProductDTOByTitle(String title) {
        return productRepository.searchProductDTOByTitle(title);
    }

    @Override
    public Product updateProduct(String tagValue,ProductDTO productDTO) {
        List<ProductMedia> productMediaDTOList = productMediaRepository.findProductMediaByProduct(productDTO.toProduct());
        for(ProductMedia productMedia1 : productMediaDTOList){
            productMediaRepository.deleteById(productMedia1.getId());
        }
        List<MultipartFile> fileList = productDTO.getFiles();
        Product product = productRepository.save(productDTO.toProduct());
        for (MultipartFile file : fileList) {

            String fileType = file.getContentType();
            assert fileType != null;

            fileType = fileType.substring(0, 5);

            productDTO.setFileType(fileType);
            productDTO.setFile(file);


            ProductMedia productMedia = productMediaRepository.save(productDTO.toProductmedia());

            if (fileType.equals(FileType.IMAGE.getValue())) {
                uploadAndSaveProductImage(productDTO, product, productMedia);
            }

            if (fileType.equals(FileType.VIDEO.getValue())) {
                uploadAndSaveProductVideo(productDTO, product, productMedia);
            }
        }
        Optional<TagDTO> tagDTO = tagRepository.findTagDTOByProductId(product.getId());

        tagDTO.get().setProduct(product.toProductDTO());
        tagDTO.get().setName(tagValue);
        tagRepository.save(tagDTO.get().toTag());

        return product;
    }

    @Override
    public Product deleteSoft(Product product) {
        Optional<TagDTO> tagDTO = tagRepository.findTagDTOByProductId(product.getId());
        if(tagDTO.isPresent()){
            tagDTO.get().setDeleted(true);
            tagRepository.save(tagDTO.get().toTag());
            product.setDeleted(true);
            return productRepository.save(product);
        }
        product.setDeleted(true);
        return productRepository.save(product);
    }
}
