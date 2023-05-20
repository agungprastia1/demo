package com.oneIndonesia.demo.controller;

import com.oneIndonesia.demo.dto.*;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.service.FeatureService;
import com.oneIndonesia.demo.util.ClaimHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public BaseResponse<Long> saveOrUpdatePostingan(@RequestBody SaveOrUpdatePostinganRequest request,Authentication authentication){
        User user = ClaimHelper.getUser(authentication);
        return featureService.saveOrUpdatePostingan(request,user);
    }

    @PostMapping(path = "/applyJob")
    public BaseResponse<Long> applyJob(@ModelAttribute ApplicantsJob applicantsJob, Authentication authentication) throws IOException {
        User user =  ClaimHelper.getUser(authentication);
        return featureService.applyJob(applicantsJob,user);
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

    @PostMapping(path = "/approveApplicant")
    private BaseResponse<List<Long>> aproveJobApply(@RequestBody JobRequest request, Authentication authentication){
        User user = ClaimHelper.getUser(authentication);
        return featureService.approveJobApply(request,user);
    }

    @PostMapping(path = "/rejectApplicant")
    private BaseResponse<List<Long>> rejectApplicant(@RequestBody JobRequest request, Authentication authentication){
        User user = ClaimHelper.getUser(authentication);
        return featureService.rejectApplication(request,user);
    }



}
