package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApplicantsJob {
    private Long id;
    private Long postinganId;
    private Long jobseeker;
    private Long recruiter;
    private String description;
    private MultipartFile file;
}
