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
public class GetPostinganListRequest extends BaseRequestDto {
    private Long id;
    private String title;
    private String category;
    private String recruiter;
    private String description;
    private String dueDate;
    private String salary;
    private String createAt;
    private String updateAt;
}
