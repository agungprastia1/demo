package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrUpdateCategoryRequest {
    private Long id;
    private String category;
}
