package com.oneIndonesia.demo.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.oneIndonesia.demo.controller.FeatureController;
import com.oneIndonesia.demo.dto.ApplicantsJob;
import com.oneIndonesia.demo.dto.BaseResponse;
import com.oneIndonesia.demo.dto.SaveOrUpdatePostinganRequest;
import com.oneIndonesia.demo.dto.SaveOrUpdateRoleRequest;
import com.oneIndonesia.demo.model.Postingan;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.repository.PostinganRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {FeatureService.class})
@ExtendWith(SpringExtension.class)
class FeatureServiceTest {
    @MockBean
    private FeatureService featureService;

    @Mock
    private PostinganRepository postinganRepository;

    @InjectMocks
    private FeatureController featureController;

    @Test
    void saveOrUpdateRole() {
        BaseResponse<Long> response = new BaseResponse<>();
        SaveOrUpdateRoleRequest saveOrUpdateRoleRequest = SaveOrUpdateRoleRequest.builder()
                .role("rewe")
                .id(null).build();

        when(featureService.saveOrUpdateRole(saveOrUpdateRoleRequest)).thenReturn(response);

        BaseResponse<Long> actual = featureService.saveOrUpdateRole(saveOrUpdateRoleRequest);
        assertNull(actual.getData());
        assertEquals(actual,response);
    }

    @Test
    void saveOrUpdatePostinganNew(){
        SaveOrUpdatePostinganRequest saveOrUpdatePostinganRequest = SaveOrUpdatePostinganRequest.builder()
                .salary(1234L)
                .dueDate(new Date())
                .description("ini desc")
                .categoryId(1L)
                .title("ini title")
                .id(null)
        .build();


         Postingan postingan = Postingan.builder()
                .id(null)
                .salary(22L)
                .dueDate(new Date())
                .description("description")
                .recruiter(1L)
                .categoryId(1L)
                .title("title")
                .createAt(new Date())
                .build();

        User user = User.builder()
                .id(1L)
                .roleId(1L).build();

        when(featureService.saveOrUpdatePostingan(saveOrUpdatePostinganRequest,user)).thenReturn(new BaseResponse<Long>(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),1L));
        when(postinganRepository.save(postingan)).thenReturn(new Postingan(1L,"title",1L,2L,"desrc",new Date(),22L,new Date(),new Date()));
        BaseResponse<Long> actual = featureService.saveOrUpdatePostingan(saveOrUpdatePostinganRequest,user);

        assertEquals(HttpStatus.OK.value(), actual.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(),actual.getMessage());
        assertNotNull(actual.getData());
    }

    @Test
    void saveOrUpdatePostinganExist(){
        SaveOrUpdatePostinganRequest request = new SaveOrUpdatePostinganRequest();
        request.setId(1L);
        request.setTitle("Updated Title");

        User user = new User();
        user.setId(1L);
        user.setRoleId(2L);

        when(postinganRepository.findById(1L))
                .thenReturn(Optional.of(new Postingan(1L, "Old Title", 2L, 1L, "Old Description", new Date(), 4000L,new Date(),new Date())));
        when(postinganRepository.save(any(Postingan.class)))
                .thenReturn(new Postingan(1L, "Old Title", 2L, 1L, "Old Description", new Date(), 4000L,new Date(),new Date()));

        when(featureService.saveOrUpdatePostingan(request,user)).thenReturn(new BaseResponse<Long>(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),1L));
        BaseResponse<Long> actual = featureService.saveOrUpdatePostingan(request, user);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK.getReasonPhrase(),actual.getMessage());
        assertEquals(HttpStatus.OK.value(),actual.getStatus());
        assertNotNull(actual.getData());
    }

    @Test
    void applyJob() throws IOException {
        ApplicantsJob applicantsJob = new ApplicantsJob();
        User user = new User();
        when(featureService.applyJob(any(ApplicantsJob.class),any(User.class))).thenReturn(new BaseResponse<Long>(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),1L));
        BaseResponse<Long> action = featureService.applyJob(applicantsJob,user);

        assertNotNull(action);
        assertEquals(HttpStatus.OK.value(),action.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(),action.getMessage());
    }
}