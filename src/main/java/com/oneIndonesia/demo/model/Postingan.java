package com.oneIndonesia.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "postingan")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Postingan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "recruiter")
    private Long recruiter;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "salary")
    private Long salary;
    
    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;
}
