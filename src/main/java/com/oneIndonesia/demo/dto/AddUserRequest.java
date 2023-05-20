package com.oneIndonesia.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AddUserRequest {
    private Long id;
    private String userName;
    private String password;
    private Long roleId;
    private String email;
    private String noHp;
}
