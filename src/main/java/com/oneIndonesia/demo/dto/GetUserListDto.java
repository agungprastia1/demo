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
public class GetUserListDto {
    private Long id;
    private String user;
    private String email;
    private String noHp;
    private String role;
    private Date createAt;
    private Date updatedAt;
}
