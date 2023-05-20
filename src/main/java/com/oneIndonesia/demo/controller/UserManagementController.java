package com.oneIndonesia.demo.controller;

import com.oneIndonesia.demo.dto.AddUserRequest;
import com.oneIndonesia.demo.dto.BaseResponse;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.service.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserManagementController {
    @Autowired
    private  UserManagementService userManagementService;
//
//    @PostMapping(path = "/saveOrUpdateUser")
//    public BaseResponse<Long> addUser(@RequestBody AddUserRequest request){
//        return userManagementService.addUser(request);
//    }

    @PostMapping("/register")
    public BaseResponse<User> register (@RequestBody User request){
        return userManagementService.register(request);
    }
}
