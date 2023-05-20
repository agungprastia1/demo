package com.oneIndonesia.demo.service;

import com.oneIndonesia.demo.dto.*;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public BaseResponse<GetUserListResponse> getListUser(GetUserListRequest request) {
        request.setType(request.getSortBy() == null ? "id" : request.getSortBy());
        request.setSortType(request.getSortType() == null ? "DESC" : request.getSortType());
        JpaSort sortProgrest;
        if (request.getSortType().equalsIgnoreCase("ASC")) {
            sortProgrest = JpaSort.unsafe(Sort.Direction.ASC, "(" + request.getSortBy() + ")");
        } else {
            sortProgrest = JpaSort.unsafe(Sort.Direction.DESC, "(" + request.getSortBy() + ")");
        }
        List<GetUserListDto> getUserListDtos = new ArrayList<>();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getDataPerPage(), sortProgrest);
        Page<GetUserLilstInf> getUserLilstInfs = userRepository.getListUser(
                request.getId()==null?"All":request.getId(),
                request.getUser()==null?"All":request.getUser(),
                request.getEmail()==null?"All":request.getEmail(),
                request.getNoHp()==null?"All":request.getNoHp(),
                request.getCreateAt()==null?"All":request.getCreateAt(),
                request.getUpdatedAt()==null?"All":request.getUpdatedAt(),
                request.getRole()==null?"All":request.getRole(),
                pageable
        );
        if (getUserLilstInfs.isEmpty()){
            return BaseResponse.<GetUserListResponse>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                    .build();
        }
        getUserLilstInfs.forEach(a -> getUserListDtos.add(modelMapper.map(a,GetUserListDto.class)));
        GetUserListResponse response = GetUserListResponse.builder()
                .pageNumber(getUserLilstInfs.getNumber())
                .totalPage(getUserLilstInfs.getTotalPages())
                .numberOfElement(getUserLilstInfs.getNumberOfElements())
                .totalElement(getUserLilstInfs.getTotalElements())
                .content(getUserListDtos)
                .build();
        return BaseResponse.<GetUserListResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response).build();
    }
}
