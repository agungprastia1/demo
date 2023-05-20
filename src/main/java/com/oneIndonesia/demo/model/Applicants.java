package com.oneIndonesia.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "applicant")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Applicants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "postingan_id")
    private Long postinganId;

    @Column(name = "jobseeker")
    private Long jobseeker;

    @Column(name = "recruiter")
    private Long recruiter;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "description")
    private String description;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "path")
    private String path;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "rejected_By")
    private Long rejectedBy;

    @Column(name = "approved_at")
    private Date approvedAt;

    @Column(name = "rejected_at")
    private Date rejectedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;
}
