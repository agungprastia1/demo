package com.oneIndonesia.demo.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneIndonesia.demo.controller.FeatureController;
import com.oneIndonesia.demo.dto.BaseResponse;
import com.oneIndonesia.demo.dto.SaveOrUpdateCategoryRequest;
import com.oneIndonesia.demo.dto.SaveOrUpdatePostinganRequest;
import com.oneIndonesia.demo.dto.SaveOrUpdateRoleRequest;
import com.oneIndonesia.demo.model.User;
import com.oneIndonesia.demo.service.FeatureService;
import com.oneIndonesia.demo.util.ClaimHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Feature Controller")
@ContextConfiguration(classes = {FeatureController.class})
@ExtendWith(SpringExtension.class)
 class FeatureControllerTest {

    @Autowired
    private FeatureController featureController;

    @MockBean
    private FeatureService featureService;

    @Test
    void saveOrUpdateRole() throws Exception {
        when(this.featureService.saveOrUpdateRole((SaveOrUpdateRoleRequest) any())).thenReturn(new BaseResponse<>());

        SaveOrUpdateRoleRequest saveOrUpdateRoleRequest = new SaveOrUpdateRoleRequest();
        saveOrUpdateRoleRequest.setRole("user");
        saveOrUpdateRoleRequest.setId(1L);


        String request = (new ObjectMapper()).writeValueAsString(saveOrUpdateRoleRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/features/saveOrUpdateRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(this.featureController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void saveOrUpdateCategory() throws JsonProcessingException {
        BaseResponse<Long> res = new BaseResponse<>();
        SaveOrUpdateCategoryRequest saveOrUpdateCategoryRequest = SaveOrUpdateCategoryRequest.builder()
                .category("aaa")
                .id(1L).build();
        when(featureService.saveOrUpdateCategory(saveOrUpdateCategoryRequest)).thenReturn(res);


        BaseResponse<Long> requestBuilder = featureController.saveOrUpdateCategory(saveOrUpdateCategoryRequest);

        verify(featureService, times(1)).saveOrUpdateCategory(saveOrUpdateCategoryRequest);

        assertEquals(res,requestBuilder);
    }

    @Test
    void saveOrUpdatePostingan(){
        BaseResponse<Long> response = new BaseResponse<>();
        SaveOrUpdatePostinganRequest saveOrUpdatePostinganRequest = SaveOrUpdatePostinganRequest.builder()
                .id(null)
                .title("aaa")
                .categoryId(1L)
                .description("asdad")
                .dueDate(null)
                .salary(23L)
                .build();

        User user = User.builder()
                .id(2L)
                .roleId(1L)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(featureService.saveOrUpdatePostingan(saveOrUpdatePostinganRequest,user)).thenReturn(response);
        BaseResponse<Long> actu = featureController.saveOrUpdatePostingan(saveOrUpdatePostinganRequest,authentication);
        verify(featureService,times(1)).saveOrUpdatePostingan(saveOrUpdatePostinganRequest,user);
        assertEquals(actu,response);
    }


}
