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
public class GetDetailPostinganResponse {
    private Long id;
    private String title;
    private String category;
    private String recruiter;
    private String description;
    private Date dueDate;
    private Long salary;
    private Date createAt;
    private Date updateAt;
}
