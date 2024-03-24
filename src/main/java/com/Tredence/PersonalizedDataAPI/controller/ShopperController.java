package com.Tredence.PersonalizedDataAPI.controller;

import com.Tredence.PersonalizedDataAPI.dto.ProductMetadataDTO;
import com.Tredence.PersonalizedDataAPI.dto.ShelfItemDTO;
import com.Tredence.PersonalizedDataAPI.dto.ShopperDTO;
import com.Tredence.PersonalizedDataAPI.exception.ProductNotFoundException;
import com.Tredence.PersonalizedDataAPI.exception.ShopperNotFoundException;
import com.Tredence.PersonalizedDataAPI.service.ProductMetadataService;
import com.Tredence.PersonalizedDataAPI.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ShopperController {

    private final ShopperService shopperService;

    @Autowired
    public ShopperController(ShopperService shopperService) {
        this.shopperService = shopperService;
    }

    @PostMapping("/internal/shoppers")
    public ResponseEntity<String> storeShopperData(@RequestBody ShopperDTO shopperDTO) {
        if (shopperDTO == null || shopperDTO.getShopperId() == null || shopperDTO.getShopperId().isEmpty() || shopperDTO.getShelf() == null || shopperDTO.getShelf().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request payload");
        }

        try {

            for (ShelfItemDTO shelfItem : shopperDTO.getShelf()) {
                ProductMetadataDTO productMetadata = shelfItem.getProductMetadata();
                if (productMetadata != null) {
                    productMetadataService.storeProductMetadata(productMetadata);
                }
            }

            shopperService.storeShopperData(shopperDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Shopper data stored successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store shopper data");
        }
    }

    @GetMapping("/internal/shoppers/{shopperId}")
    public ResponseEntity<ShopperDTO> getShopperData(@PathVariable String shopperId) {
        try {
            // Fetch shopper data based on the provided shopperId
            ShopperDTO shopperDTO = shopperService.getShopperById(shopperId);
            if (shopperDTO == null) {
                throw new ShopperNotFoundException("Shopper not found with ID: " + shopperId);
            }
            return ResponseEntity.ok(shopperDTO);
        } catch (ShopperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Autowired
    private ProductMetadataService productMetadataService;

    @PostMapping("/product-metadata")
    public ResponseEntity<String> createProductMetadata(@RequestBody ProductMetadataDTO productMetadataDTO) {
        try {
            productMetadataService.saveProductMetadata(productMetadataDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product metadata created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product metadata");
        }
    }

    @GetMapping("/product-metadata/{productId}")
    public ResponseEntity<ProductMetadataDTO> getProductMetadataById(@PathVariable String productId) {
        try {
            ProductMetadataDTO productMetadataDTO = productMetadataService.getProductMetadataById(productId);
            if (productMetadataDTO == null) {
                throw new ProductNotFoundException("Product metadata not found with ID: " + productId);
            }
            return ResponseEntity.ok(productMetadataDTO);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/external/shoppers/{shopperId}/products")
    public ResponseEntity<List<String>> getProductsByShopperWithFilters(
            @PathVariable String shopperId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<String> products = shopperService.getProductsByShopperWithFilters(shopperId, category, brand, limit);
            if (products.isEmpty()) {
                throw new ShopperNotFoundException("Products not found for shopper ID: " + shopperId);
            }
            return ResponseEntity.ok(products);
        } catch (ShopperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
