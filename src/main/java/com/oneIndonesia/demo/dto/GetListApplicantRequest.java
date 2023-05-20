package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GetListApplicantRequest extends BaseRequestDto{
        private Long id;
    private String title;
    private String user;
    private String cvName;
    private String cvPath;
    private String createDate;
    private String status;
    private String remark;
    private String category;
}
