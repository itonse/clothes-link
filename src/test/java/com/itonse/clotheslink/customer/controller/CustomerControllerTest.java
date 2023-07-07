package com.itonse.clotheslink.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itonse.clotheslink.customer.dto.SignInForm;
import com.itonse.clotheslink.customer.dto.SignUpForm;
import com.itonse.clotheslink.customer.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void resetDataBase() {
        customerRepository.deleteAll();
    }

    @Test
    void signUpSuccess() throws Exception {
        // given
        SignUpForm signUpForm = SignUpForm.builder()
                .email("bbb@naver.com")
                .name("강이번")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("bbb@naver.com"));
    }

    @Test
    void signUpFailCustomException() throws Exception {
        // given
        SignUpForm firstForm = SignUpForm.builder()
                .email("bbb@naver.com")
                .name("강이번")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        SignUpForm secondForm = SignUpForm.builder()
                .email("bbb@naver.com")
                .name("박삼번")
                .password("55667788")
                .phone("010-5555-6666")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("bbb@naver.com"));

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                        .value("ALREADY_REGISTERED_CUSTOMER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("이미 가입된 이메일 입니다."));
    }

    @Test
    void signUpFailValidException() throws Exception {
        // given
        SignUpForm signUpForm = SignUpForm.builder()
                .email("bbb@naver.com")
                .name("강이번")
                .password("11")
                .phone("010-1111-2222")
                .build();

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .value("비밀번호는 최소 8자 ~ 최대 20자 이내로 입력해주세요."));
    }

    @Test
    void signInSuccess() throws Exception {
        // given
        SignUpForm signUpForm = SignUpForm.builder()
                .email("bbb@naver.com")
                .name("박삼번")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        SignInForm signInForm = SignInForm.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpForm)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signin/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.TOKEN")
                        .value(Matchers.notNullValue()));
    }

    @Test
    void signInFailCustomException() throws Exception {
        // given
        SignInForm signInForm = SignInForm.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .build();

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/signin/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode")
                        .value("LOGIN_FAIL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("일치하는 회원정보가 없습니다."));

    }
}