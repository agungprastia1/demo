package com.oneIndonesia.demo.service;

import com.oneIndonesia.demo.dto.AddUserRequest;
import com.oneIndonesia.demo.dto.BaseResponse;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@Log4j2
public class UserManagementService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public BaseResponse<User> register (User request){
        boolean userExist = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExist){
            return BaseResponse.<User>builder()
                    .status(HttpStatus.IM_USED.value())
                    .message("Email "+request.getEmail()+" already used!").build();
        }
        User user = modelMapper.map(request, User.class);
        String passwordEncode = bCryptPasswordEncoder.encode(request.getPassword());
        user.setPassword(passwordEncode);
        user.setCreateAt(new Date());
         userRepository.save(user);
        return BaseResponse.<User>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(user)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                String.format("User with email '%s' not found",email)));
    }
}
