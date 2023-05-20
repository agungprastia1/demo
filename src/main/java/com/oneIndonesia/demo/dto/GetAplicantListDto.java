package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAplicantListDto {
    private Long id;
    private String category;
    private String title;
    private String user;
    private String cvName;
    private String cvPath;
    private String status;
    private String remark;
    private Date createDate;
}
