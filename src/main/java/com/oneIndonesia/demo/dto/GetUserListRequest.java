package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserListRequest extends BaseRequestDto{
    private Long id;
    private String user;
    private String email;
    private String noHp;
    private String role;
    private String createAt;
    private String updatedAt;
}
