package com.Tredence.PersonalizedDataAPI.service;

import com.Tredence.PersonalizedDataAPI.dto.ProductMetadataDTO;
import com.Tredence.PersonalizedDataAPI.dto.ShelfItemDTO;
import com.Tredence.PersonalizedDataAPI.dto.ShopperDTO;
import com.Tredence.PersonalizedDataAPI.entity.ProductMetadataEntity;
import com.Tredence.PersonalizedDataAPI.entity.ShelfItemEntity;
import com.Tredence.PersonalizedDataAPI.entity.ShopperEntity;
import com.Tredence.PersonalizedDataAPI.exception.ShopperNotFoundException;
import com.Tredence.PersonalizedDataAPI.repository.ProductMetadataRepository;
import com.Tredence.PersonalizedDataAPI.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopperServiceImpl implements ShopperService {
    private final ShopperRepository shopperRepository;
    private final ProductMetadataRepository productMetadataRepository;
    @Autowired
    public ShopperServiceImpl(ShopperRepository shopperRepository,ProductMetadataRepository productMetadataRepository) {
        this.shopperRepository = shopperRepository;
        this.productMetadataRepository = productMetadataRepository;
    }

    @Override
    public void storeShopperData(ShopperDTO shopperDTO) {
        ShopperEntity shopperEntity = new ShopperEntity();
        shopperEntity.setShopperId(shopperDTO.getShopperId());

        List<ShelfItemEntity> shelfItems = shopperDTO.getShelf().stream()
                .map(this::convertToShelfItemEntity)
                .collect(Collectors.toList());
        shopperEntity.setShelf(shelfItems);

        shopperRepository.save(shopperEntity);
    }
       @Override
    public List<String> getProductsByShopper(String shopperId, String category, String brand, int limit) {
        Optional<ShopperEntity> optionalShopperEntity = shopperRepository.findById(shopperId);
        if (optionalShopperEntity.isPresent()) {
            List<ShelfItemEntity> shelfItems = optionalShopperEntity.get().getShelf();
            return shelfItems.stream()
                    .map(ShelfItemEntity::getProductId)
                    .limit(limit)
                    .collect(Collectors.toList());
        } else {
            throw new ShopperNotFoundException("Shopper not found with ID: " + shopperId);
        }
    }


    @Override
    public ShopperDTO getShopperById(String shopperId) {
        Optional<ShopperEntity> shopperEntityOptional = shopperRepository.findById(shopperId);
        if (shopperEntityOptional.isPresent()) {
            ShopperEntity shopperEntity = shopperEntityOptional.get();
            ShopperDTO shopperDTO = new ShopperDTO();
            shopperDTO.setShopperId(shopperEntity.getShopperId());
            shopperDTO.setShelf(convertToShelfItemDTOs(shopperEntity.getShelf()));
            List<ShelfItemDTO> shelfItemDTOs = shopperEntity.getShelf().stream()
                    .map(this::convertToShelfItemDTO)
                    .collect(Collectors.toList());
            shopperDTO.setShelf(shelfItemDTOs);
            return shopperDTO;
        } else {
            throw new ShopperNotFoundException("Shopper not found with ID: " + shopperId);
        }
    }


    private ShelfItemEntity convertToShelfItemEntity(ShelfItemDTO shelfItemDTO) {
        ShelfItemEntity shelfItemEntity = new ShelfItemEntity();
        shelfItemEntity.setProductId(shelfItemDTO.getProductId());
        shelfItemEntity.setRelevancyScore(shelfItemDTO.getRelevancyScore());

        // Set product metadata if available
        if (shelfItemDTO.getProductMetadata() != null) {
            ProductMetadataDTO productMetadataDTO = shelfItemDTO.getProductMetadata();
            ProductMetadataEntity productMetadataEntity = new ProductMetadataEntity();
            productMetadataEntity.setProductId(productMetadataDTO.getProductId());
            productMetadataEntity.setCategory(productMetadataDTO.getCategory());
            productMetadataEntity.setBrand(productMetadataDTO.getBrand());
            shelfItemEntity.setProductMetadata(productMetadataEntity);
        }

        return shelfItemEntity;
    }




    @Override
    public List<String> getProductsByShopperWithFilters(String shopperId, String category, String brand, int limit) {
        Optional<ShopperEntity> optionalShopperEntity = shopperRepository.findById(shopperId);
        if (optionalShopperEntity.isPresent()) {
            List<ShelfItemEntity> shelfItems = optionalShopperEntity.get().getShelf();
            return shelfItems.stream()
                    .map(item -> {
                        ProductMetadataEntity productMetadataEntity = item.getProductMetadata();
                        if (productMetadataEntity != null) {
                            return new ShelfItemDTO(item.getProductId(), item.getRelevancyScore(), convertToProductMetadataDTO(productMetadataEntity));
                        } else {
                            // If productMetadataEntity is null, initialize an empty ProductMetadataDTO
                            return new ShelfItemDTO(item.getProductId(), item.getRelevancyScore(), new ProductMetadataDTO());
                        }
                    })
                    .limit(limit <= 100 ? limit : 100)
                    .map(ShelfItemDTO::toString) // Or any other transformation you need
                    .collect(Collectors.toList());
        } else {
            throw new ShopperNotFoundException("Shopper not found with ID: " + shopperId);
        }
    }





    private ProductMetadataDTO convertToProductMetadataDTO(ProductMetadataEntity productMetadataEntity) {
        ProductMetadataDTO productMetadataDTO = new ProductMetadataDTO();
        if (productMetadataEntity != null) {
            productMetadataDTO.setProductId(productMetadataEntity.getProductId());
            productMetadataDTO.setCategory(productMetadataEntity.getCategory());
            productMetadataDTO.setBrand(productMetadataEntity.getBrand());
        }
        return productMetadataDTO;
    }
    private List<ShelfItemDTO> convertToShelfItemDTOs(List<ShelfItemEntity> shelfItems) {
        return shelfItems.stream()
                .map(this::convertToShelfItemDTO)
                .collect(Collectors.toList());
    }

    private ShelfItemDTO convertToShelfItemDTO(ShelfItemEntity shelfItemEntity) {
        ShelfItemDTO shelfItemDTO = new ShelfItemDTO();
        shelfItemDTO.setProductId(shelfItemEntity.getProductId());
        shelfItemDTO.setRelevancyScore(shelfItemEntity.getRelevancyScore());
        if (shelfItemEntity.getProductMetadata() != null) {
            ProductMetadataEntity productMetadataEntity = shelfItemEntity.getProductMetadata();
            ProductMetadataDTO productMetadataDTO = new ProductMetadataDTO();
            productMetadataDTO.setProductId(productMetadataEntity.getProductId());
            productMetadataDTO.setCategory(productMetadataEntity.getCategory());
            productMetadataDTO.setBrand(productMetadataEntity.getBrand());
            shelfItemDTO.setProductMetadata(productMetadataDTO);
        }
        return shelfItemDTO;
    }



}
