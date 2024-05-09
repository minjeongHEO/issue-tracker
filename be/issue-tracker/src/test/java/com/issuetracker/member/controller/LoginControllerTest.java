package com.issuetracker.member.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.issuetracker.member.dto.LoginMemberDto;
import com.issuetracker.member.dto.LoginTryDto;
import com.issuetracker.member.exception.LoginFailException;
import com.issuetracker.member.model.Member;
import com.issuetracker.member.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginService loginService;

    private LoginTryDto loginTryDto;
    private Member member;
    private LoginMemberDto loginMemberDto;

    @BeforeEach
    void setUp() {
        loginTryDto = new LoginTryDto("john.doe", "password123");
        member = new Member("john.doe", "password123", "johnny", "john.doe@example.com");
        loginMemberDto = new LoginMemberDto("john.doe", "johnny", "john.doe@example.com");
    }

    @Test
    @DisplayName("로그인에 성공하면 아이디, 닉네임, 비밀번호를 반환한다.")
    void login_success() throws Exception {
        // Mocking LoginService
        when(loginService.login(any(LoginTryDto.class))).thenReturn(member);

        String requestJson = objectMapper.writeValueAsString(loginTryDto);
        String expectedResponseJson = objectMapper.writeValueAsString(loginMemberDto);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    @DisplayName("아이디나 비밀번호가 비어있으면 400 응답을 반환한다.")
    void login_validation_fail() throws Exception {
        // Invalid DTO with empty password
        LoginTryDto invalidLoginTryDto = new LoginTryDto("john.doe", "");
        String requestJson = objectMapper.writeValueAsString(invalidLoginTryDto);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인에 실패하면 401 응답을 반환한다.")
    void login_fail() throws Exception {
        // Mocking LoginService to throw an exception
        when(loginService.login(any(LoginTryDto.class))).thenThrow(new LoginFailException());

        String requestJson = objectMapper.writeValueAsString(loginTryDto);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }
}
