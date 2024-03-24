package com.Tredence.PersonalizedDataAPI.service;

import com.Tredence.PersonalizedDataAPI.dto.ProductMetadataDTO;
import com.Tredence.PersonalizedDataAPI.entity.ProductMetadataEntity;
import com.Tredence.PersonalizedDataAPI.exception.ProductNotFoundException;
import com.Tredence.PersonalizedDataAPI.repository.ProductMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductMetadataServiceImpl implements ProductMetadataService {

    @Autowired
    private ProductMetadataRepository productMetadataRepository;

    @Override
    public void saveProductMetadata(ProductMetadataDTO productMetadataDTO) {
        ProductMetadataEntity productMetadataEntity = new ProductMetadataEntity();
        productMetadataEntity.setProductId(productMetadataDTO.getProductId());
        productMetadataEntity.setCategory(productMetadataDTO.getCategory());
        productMetadataEntity.setBrand(productMetadataDTO.getBrand());
        productMetadataRepository.save(productMetadataEntity);
    }

    @Override
    public ProductMetadataDTO getProductMetadataById(String productId) {
        ProductMetadataEntity productMetadataEntity = productMetadataRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product metadata not found with ID: " + productId));

        return convertToDTO(productMetadataEntity);
    }

    private ProductMetadataDTO convertToDTO(ProductMetadataEntity productMetadataEntity) {
        ProductMetadataDTO productMetadataDTO = new ProductMetadataDTO();
        productMetadataDTO.setProductId(productMetadataEntity.getProductId());
        productMetadataDTO.setCategory(productMetadataEntity.getCategory());
        productMetadataDTO.setBrand(productMetadataEntity.getBrand());
        return productMetadataDTO;
    }


    @Override
    public ProductMetadataDTO getProductMetadata(String productId) {
        Optional<ProductMetadataEntity> optionalProductMetadataEntity = productMetadataRepository.findById(productId);
        if (optionalProductMetadataEntity.isPresent()) {
            ProductMetadataEntity productMetadataEntity = optionalProductMetadataEntity.get();
            ProductMetadataDTO productMetadataDTO = new ProductMetadataDTO();
            productMetadataDTO.setProductId(productMetadataEntity.getProductId());
            productMetadataDTO.setCategory(productMetadataEntity.getCategory());
            productMetadataDTO.setBrand(productMetadataEntity.getBrand());

            return productMetadataDTO;
        }
        return null;
    }

    @Override
    public void updateProductMetadata(String productId, ProductMetadataDTO productMetadataDTO) {
        Optional<ProductMetadataEntity> optionalProductMetadataEntity = productMetadataRepository.findById(productId);
        if (optionalProductMetadataEntity.isPresent()) {
            ProductMetadataEntity productMetadataEntity = optionalProductMetadataEntity.get();
            productMetadataEntity.setCategory(productMetadataDTO.getCategory());
            productMetadataEntity.setBrand(productMetadataDTO.getBrand());
            productMetadataRepository.save(productMetadataEntity);
        }
    }



    @Override
    public void storeProductMetadata(ProductMetadataDTO productMetadata) {

    }
}

