package com.Tredence.PersonalizedDataAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShelfItemDTO {
    private String productId;
    private double relevancyScore;
    private ProductMetadataDTO productMetadata;

}
