package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DetailApplicantResponse
{
    private Long id;
    private String name;
    private String email;
    private String noHp;
    private String jobTittle;
    private String category;
    private String cv;
    private String cvPath;
    private Date applyDate;
    private Date dueDate;
}
