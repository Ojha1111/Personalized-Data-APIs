package com.Tredence.PersonalizedDataAPI.service;

import com.Tredence.PersonalizedDataAPI.dto.ShopperDTO;
import com.Tredence.PersonalizedDataAPI.exception.ShopperNotFoundException;

import java.util.List;

public interface ShopperService {
    void storeShopperData(ShopperDTO shopperDTO);

    List<String> getProductsByShopperWithFilters(String shopperId, String category, String brand, int limit);

    List<String> getProductsByShopper(String shopperId, String category, String brand, int limit);

    ShopperDTO getShopperById(String shopperId);
}
