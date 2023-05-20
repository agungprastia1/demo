package com.oneIndonesia.demo.controller;

import com.fasterxml.jackson.databind.Module;
import com.oneIndonesia.demo.dto.*;
import com.oneIndonesia.demo.service.FeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/features")
@Slf4j
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping(path = "/saveOrUpdateRole")
    public BaseResponse<Long> saveOrUpdateRole(@RequestBody SaveOrUpdateRoleRequest request){
        return featureService.saveOrUpdateRole(request);
    }

    @PostMapping(path = "/saveOrUpdateCategory")
    public BaseResponse<Long> saveOrUpdateCategory(@RequestBody SaveOrUpdateCategoryRequest request){
        return featureService.saveOrUpdateCategory(request);
    }

    @PostMapping(path = "/saveOrUpdatePostingan")
    public BaseResponse<Long> saveOrUpdatePostingan(@RequestBody SaveOrUpdatePostinganRequest request){
        return featureService.saveOrUpdatePostingan(request);
    }

    @PostMapping(path = "/applyJob")
    public BaseResponse<Long> applyJob(@ModelAttribute ApplicantsJob applicantsJob, Authentication authentication) throws IOException {
        return featureService.applyJob(applicantsJob);
    }

    @GetMapping("/getDetailApplicant")
    public BaseResponse<DetailApplicantResponse> getDetailApplicant(@RequestParam (value = "id", defaultValue = "id")Long id){
        return featureService.getDetailApplicant(id);
    }

    @GetMapping("/downloadCv")
    public Resource downloadCv(@RequestParam (value = "id", defaultValue = "")Long id){
        return featureService.downloadCv(id);
    }

    @GetMapping(path = "/getDetailPostingan")
    public BaseResponse<GetDetailPostinganResponse> getDetailPostinganResponseBaseResponse(@RequestParam (value = "", defaultValue = "")Long id){
        return featureService.getDetailPostingan(id);
    }

    @PostMapping(path = "/getPostinganLlist")
    public BaseResponse<GetListPostinganResponse> getPostinganList(@RequestBody GetPostinganListRequest request){
        return featureService.getPostinganList(request);
    }

    @PostMapping(path = "/getListApplicant")
    public BaseResponse<GetListApplicantListResponse> getListApplicantList(@RequestBody  GetListApplicantRequest request){
        return featureService.getListApplicantList(request);
    }

//    @PostMapping(path = "/getListUser")

}
