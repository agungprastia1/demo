package com.oneIndonesia.demo.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.oneIndonesia.demo.dto.*;
import com.oneIndonesia.demo.model.*;
import com.oneIndonesia.demo.repository.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FeatureService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostinganRepository postinganRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicantsRepository applicantsRepository;

    public BaseResponse<Long> saveOrUpdateRole(SaveOrUpdateRoleRequest request) {
        if (request.getId() == null) {
            Role role = modelMapper.map(request, Role.class);
            role.setCreateAt(new Date());
            Long id = roleRepository.save(role).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        } else {
            Optional<Role> optionalRole = roleRepository.findById(request.getId());
            if (optionalRole.isEmpty()) {
                return BaseResponse.<Long>builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                        .build();
            }
            Role role = modelMapper.map(request, Role.class);
            Long id = roleRepository.save(role).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        }
    }

    public BaseResponse<Long> saveOrUpdateCategory(@RequestBody SaveOrUpdateCategoryRequest request) {
        if (request.getId() == null) {
            Category category = modelMapper.map(request, Category.class);
            category.setCreateAt(new Date());
            Long id = categoryRepository.save(category).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        } else {
            Optional<Category> category = categoryRepository.findById(request.getId());
            if (category.isEmpty()) {
                return BaseResponse.<Long>builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                        .build();
            }
            Category categori = modelMapper.map(request, Category.class);
            Long id = categoryRepository.save(categori).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        }
    }

    public BaseResponse<Long> saveOrUpdatePostingan(SaveOrUpdatePostinganRequest request,User user) {
        if (request.getId() == null) {
            if (user.getRoleId()!=2){
                return BaseResponse.<Long>builder()
                        .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value())
                        .message("Id "+user.getId()+" "+HttpStatus.NON_AUTHORITATIVE_INFORMATION.getReasonPhrase()).build();
            }
            Postingan postingan = modelMapper.map(request, Postingan.class);
            postingan.setRecruiter(user.getId());
            postingan.setCreateAt(new Date());
            Long id = postinganRepository.save(postingan).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        } else {
            Optional<Postingan> postingan = postinganRepository.findById(request.getId());
            if (postingan.isEmpty()) {
                return BaseResponse.<Long>builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                        .build();
            }
            if (user.getRoleId()!=2){
                return BaseResponse.<Long>builder()
                        .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value())
                        .message("Id "+user.getId()+" "+HttpStatus.NON_AUTHORITATIVE_INFORMATION.getReasonPhrase()).build();
            }

            Postingan postinganMap = Postingan.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .categoryId(request.getCategoryId())
                    .recruiter(user.getId())
                    .description(request.getDescription())
                    .dueDate(request.getDueDate())
                    .updateAt(new Date())
                    .salary(request.getSalary()).build();
            Long id = postinganRepository.save(postinganMap).getId();
            return BaseResponse.<Long>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(id).build();
        }
    }

    public BaseResponse<Long> applyJob(ApplicantsJob request,User user) throws IOException {

        File file = new File(resourceLoader.getResource("").getFile() +"/"+ request.getFile().getOriginalFilename());
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(request.getFile().getBytes());
        stream.close();

        Applicants applicantsJob = Applicants.builder()
                .postinganId(request.getPostinganId())
                .jobseeker(user.getId())
                .recruiter(request.getRecruiter())
                .description(request.getDescription())
                .fileName(request.getFile().getOriginalFilename())
                .path(file.getAbsolutePath())
                .createAt(new Date()).build();
        Long id = applicantsRepository.save(applicantsJob).getId();
        return BaseResponse.<Long>builder()
                .message(HttpStatus.OK.getReasonPhrase())
                .status(HttpStatus.OK.value())
                .data(id).build();
    }

    public BaseResponse<DetailApplicantResponse> getDetailApplicant(Long id) {
        Optional<Applicants> applicants = applicantsRepository.findById(id);
        try {
            if (applicants.isEmpty()) {
                return BaseResponse.<DetailApplicantResponse>builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message("Data id " + id + " is not found!").build();
            }
            Optional<User> user = userRepository.findById(applicants.get().getJobseeker());
            Optional<User> approver = userRepository.findById(applicants.get().getJobseeker());
            Optional<User> rejection = userRepository.findById(applicants.get().getJobseeker());
            Optional<Postingan> postingan = postinganRepository.findById(applicants.get().getPostinganId());
            Optional<Category> category = categoryRepository.findById(postingan.get().getCategoryId());

            DetailApplicantResponse detailApplicantResponse = DetailApplicantResponse.builder()
                    .id(applicants.get().getId())
                    .name(user.get().getUsername())
                    .email(user.get().getEmail())
                    .noHp(user.get().getNoHp())
                    .jobTittle(postingan.get().getTitle())
                    .category(category.isEmpty() ? null : category.get().getCategory())
                    .cv(applicants.get().getFileName())
                    .cvPath(applicants.get().getPath())
                    .applyDate(applicants.get().getCreateAt())
                    .dueDate(postingan.get().getDueDate())
                    .status(applicants.get().getStatus())
                    .remark(applicants.get().getRemark())
                    .approveAt(applicants.get().getApprovedAt())
                    .rejectAt(applicants.get().getRejectedAt())
                    .approveBy(approver.isEmpty()?null:approver.get().getUser())
                    .rejectBy(rejection.isEmpty()?null:rejection.get().getUser())
                    .build();
            return BaseResponse.<DetailApplicantResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(detailApplicantResponse)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<DetailApplicantResponse>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase() + " " + e.getMessage()).build();
        }
    }

    public Resource downloadCv(Long id) {
        Optional<Applicants> applicants = applicantsRepository.findById(id);
        if (applicants.isEmpty()) {
            return null;
        }
        return resourceLoader.getResource("/" + applicants.get().getFileName());
    }

    public BaseResponse<GetDetailPostinganResponse> getDetailPostingan(Long id) {
        Optional<Postingan> postingan = postinganRepository.findById(id);
        if (postingan.isEmpty()) {
            return BaseResponse.<GetDetailPostinganResponse>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Data with id " + id + " not found!").build();
        }
        Optional<User> user = null;
        Optional<Category> category = null;
        if (postingan.get().getRecruiter() != null) {
            user = userRepository.findById(postingan.get().getRecruiter());
        }
        if (postingan.get().getCategoryId() != null) {
            category = categoryRepository.findById(postingan.get().getCategoryId());
        }
        GetDetailPostinganResponse getDetailPostinganResponse = modelMapper.map(postingan.get(), GetDetailPostinganResponse.class);
        getDetailPostinganResponse.setCategory(category.isEmpty() ? null : category.get().getCategory());
        getDetailPostinganResponse.setRecruiter(user.isEmpty() ? null : user.get().getUser());
        return BaseResponse.<GetDetailPostinganResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(getDetailPostinganResponse).build();
    }

    public BaseResponse<GetListPostinganResponse> getPostinganList(GetPostinganListRequest request) {
        request.setType(request.getSortBy() == null ? "id" : request.getSortBy());
        request.setSortType(request.getSortType() == null ? "DESC" : request.getSortType());
        JpaSort sortProgrest;
        if (request.getSortType().equalsIgnoreCase("ASC")) {
            sortProgrest = JpaSort.unsafe(Sort.Direction.ASC, "(" + request.getSortBy() + ")");
        } else {
            sortProgrest = JpaSort.unsafe(Sort.Direction.DESC, "(" + request.getSortBy() + ")");
        }
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getDataPerPage(), sortProgrest);
        List<GetDetailPostinganResponse> detailPostinganResponses = new ArrayList<>();
        Page<GetDetailPostinganInf> postinganList = postinganRepository.getPostinganList(
                request.getId()==null?"All":request.getId(),
                request.getTitle()==null?"All":request.getTitle(),
                request.getCategory()==null?"All": request.getCategory(),
                request.getRecruiter() ==null?"All": request.getRecruiter(),
                request.getDescription()==null?"All":request.getDescription(),
                request.getDueDate() ==null?"All":request.getDueDate(),
                request.getSalary()==null? "All" :request.getSalary(),
                request.getCreateAt()==null?"All":request.getCreateAt(),
                request.getUpdateAt()==null?"All":request.getUpdateAt(),
                pageable
                );
        if (postinganList.hasContent()){
            postinganList.forEach(a -> detailPostinganResponses.add(modelMapper.map(a, GetDetailPostinganResponse.class)));
            GetListPostinganResponse response = GetListPostinganResponse.builder()
                    .pageNumber(postinganList.getNumber())
                    .totalPage(postinganList.getTotalPages())
                    .numberOfElement(postinganList.getNumberOfElements())
                    .totalElement(postinganList.getTotalElements())
                    .content(detailPostinganResponses)
                    .build();
            return BaseResponse.<GetListPostinganResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(response).build();
        }else {
            return BaseResponse.<GetListPostinganResponse>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase()).build();
        }
    }

    public BaseResponse<GetListApplicantListResponse> getListApplicantList( GetListApplicantRequest request){
        request.setType(request.getSortBy() == null ? "id" : request.getSortBy());
        request.setSortType(request.getSortType() == null ? "DESC" : request.getSortType());
        JpaSort sortProgrest;
        if (request.getSortType().equalsIgnoreCase("ASC")) {
            sortProgrest = JpaSort.unsafe(Sort.Direction.ASC, "(" + request.getSortBy() + ")");
        } else {
            sortProgrest = JpaSort.unsafe(Sort.Direction.DESC, "(" + request.getSortBy() + ")");
        }
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getDataPerPage(), sortProgrest);
        List<GetAplicantListDto> getAplicantListDtos = new ArrayList<>();
        Page<GetApplicantListInf> applicantListInf = applicantsRepository.getListApplicant(
                request.getId() ==null?"All":request.getId(),
                request.getTitle()==null?"All":request.getTitle(),
                request.getCvName()==null?"All":request.getCvName(),
                request.getStatus()==null?"All":request.getStatus(),
                request.getRemark()==null?"All":request.getRemark(),
                request.getCategory()==null?"All":request.getCategory(),
                request.getUser()==null?"All":request.getUser(),
                request.getCreateDate()==null?"All":request.getCreateDate(),
                pageable
        );
        if (applicantListInf.isEmpty()){
            return BaseResponse.<GetListApplicantListResponse>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                    .build();
        }
        applicantListInf.forEach(a -> getAplicantListDtos.add(modelMapper.map(a,GetAplicantListDto.class)));
        GetListApplicantListResponse response = GetListApplicantListResponse.builder()
                .totalPage(applicantListInf.getTotalPages())
                .pageNumber(applicantListInf.getNumber())
                .numberOfElement(applicantListInf.getNumberOfElements())
                .totalElement(applicantListInf.getTotalElements())
                .content(getAplicantListDtos)
                .build();
        return BaseResponse.<GetListApplicantListResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }


    public BaseResponse<List<Long>> approveJobApply(JobRequest request,User user) {
        List<Applicants> applicants = applicantsRepository.getApplicant(request.getId());
        List<Applicants> applicantsList = new ArrayList<>();
        if (applicants.isEmpty()){
            return BaseResponse.<List<Long>>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                    .build();
        }
        List<Long> id = new ArrayList<>();
        for (Applicants app :applicants){
            id.add(app.getId());
            app.setStatus("Approved");
            app.setApprovedAt(new Date());
            app.setApprovedBy(user.getId());
            applicantsList.add(app);
        }
        applicantsRepository.saveAll(applicantsList);
        return BaseResponse.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(id)
                .build();
    }

    public BaseResponse<List<Long>> rejectApplication(JobRequest request,User user) {
        List<Applicants> applicants = applicantsRepository.getApplicant(request.getId());
        List<Applicants> applicantsList = new ArrayList<>();
        if (applicants.isEmpty()){
            return BaseResponse.<List<Long>>builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                    .build();
        }
        List<Long> id = new ArrayList<>();
        for (Applicants app :applicants){
            id.add(app.getId());
            app.setStatus("Rejected");
            app.setRemark(request.getRemark());
            app.setRejectedAt(new Date());
            app.setRejectedBy(user.getId());
            applicantsList.add(app);
        }
        applicantsRepository.saveAll(applicantsList);
        return BaseResponse.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(id)
                .build();
    }
}
