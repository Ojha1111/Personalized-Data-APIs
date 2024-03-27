package com.Tredence.PersonalizedDataAPI.service;

import com.Tredence.PersonalizedDataAPI.dto.ProductMetadataDTO;

public interface ProductMetadataService {
    void saveProductMetadata(ProductMetadataDTO productMetadataDTO);
    ProductMetadataDTO getProductMetadataById(String productId);

    ProductMetadataDTO getProductMetadata(String productId);

    void updateProductMetadata(String productId, ProductMetadataDTO productMetadataDTO);

    void storeProductMetadata(ProductMetadataDTO productMetadata);


    
}
