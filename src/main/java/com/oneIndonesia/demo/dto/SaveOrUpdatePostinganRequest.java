package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrUpdatePostinganRequest {
    private Long id;
    private String title;
    private Long categoryId;
    private Long recruiter;
    private String description;
    private Date dueDate;
    private Long salary;
}
