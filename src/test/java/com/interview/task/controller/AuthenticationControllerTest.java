package com.interview.task.controller;


import com.interview.task.ApplicationRunner;
import com.interview.task.security.JwtProvider;
import com.interview.task.security.UserDetailsServiceImpl;
import com.interview.task.utils.JsonParserUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(
        classes = {ApplicationRunner.class}
)
public class AuthenticationControllerTest {

    private static final String PATH_TO_NEW_USER_MODEL = "json/register-new-user.json";


    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .addFilters(this.springSecurityFilterChain)
                .build();
    }

    @Test
    void whenUserHaveValidCredentialsThenSignUp() throws Exception {
        JSONObject jsonObj = JsonParserUtil.parseJsonToObject(PATH_TO_NEW_USER_MODEL);
        String jsonSource = jsonObj.toString();
        mockMvc.perform(post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(jsonSource))
                .andExpect(status().isOk());
    }
}
