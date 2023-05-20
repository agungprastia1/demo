package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListPostinganResponse {
    private int totalPage;
    private long totalElement;
    private int numberOfElement;
    private int pageNumber;
    private List<GetDetailPostinganResponse> content;
}
