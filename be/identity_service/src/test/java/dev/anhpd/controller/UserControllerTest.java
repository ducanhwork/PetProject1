package dev.anhpd.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.anhpd.entity.dto.request.UserCreateRequest;
import dev.anhpd.entity.dto.response.UserResponse;
import dev.anhpd.service.UserService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        userCreateRequest = UserCreateRequest.builder()
                .username("anhpd")
                .password("12345678")
                .fullname("Pham Dinh Anh")
                .email("duc@gmail.com")
                .dob(LocalDate.of(2004, 12, 9))
                .enabled(false)
                .build();
        userResponse = UserResponse.builder()
                .user_id("cf0600f538b3")
                .username("anhpd")
                .fullname("Pham Dinh Anh")
                .dob(LocalDate.of(2004, 12, 9))
                .email("duc@gmail.com")
                .build();
    }

    @Test
    void createUser() {
        log.info("Test create user");
        // GIVEN

        // WHEN
        // THEN
    }
}
