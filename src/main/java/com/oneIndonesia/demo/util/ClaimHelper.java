package com.oneIndonesia.demo.util;

import com.oneIndonesia.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ClaimHelper {
    public ClaimHelper() {
    }

    public static User getUser(Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
       return (User) authentication.getPrincipal();
    }
}
