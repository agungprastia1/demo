package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequestDto {
    private String type;
    private String sortType;
    private String sortBy;
    private int pageNumber;
    private int dataPerPage;
}
