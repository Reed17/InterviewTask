package com.interview.task.controller;


import com.interview.task.ApplicationRunner;
import com.interview.task.utils.JsonParserUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
public class AuthenticationControllerIntegrationTest {

    /*
     * Json request && response file paths
     * */
    private static final String PATH_TO_NEW_USER_MODEL = "json/req/register-new-user.json";
    private static final String LOGIN_REQUEST_PATH = "json/req/user-login.json";
    private static final String INVALID_USER_CREDENTIALS_PATH = "json/req/invalid-user-credentials.json";
    private static final String PATH_TO_LOGIN_ERROR = "json/resp/login-error-user-not-found-response.json";
    private static final String USER_ALREADY_EXISTS_PATH = "json/resp/login-error-user-already-exists-response.json";

    /*
     * Api paths
     * */
    private static final String API_AUTH_SIGNUP = "/api/auth/signup";
    private static final String API_AUTH_SIGNIN = "/api/auth/signin";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

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
        String jsonSource = getJson(PATH_TO_NEW_USER_MODEL);
        mockMvc.perform(post(API_AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSource))
                .andExpect(status().isOk());
    }

    @Test
    void whenUserTryToRegisterTwiceThenRegistrationFails() throws Exception {
        String jsonSource = getJson(PATH_TO_NEW_USER_MODEL);
        mockMvc.perform(post(API_AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSource));

        String response = mockMvc.perform(post(API_AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSource))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = getJson(USER_ALREADY_EXISTS_PATH);
        JSONAssert.assertEquals(expected, response, JSONCompareMode.LENIENT);
    }

    @Test
    void whenUserLoginWithValidCredentialsThenSuccessfullyLogin() throws Exception {
        String jsonSource = getJson(LOGIN_REQUEST_PATH);
        mockMvc.perform(post(API_AUTH_SIGNIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSource))
                .andExpect(status().isOk());
    }

    @Test
    void whenUserLoginWithInvalidCredentialsThenSuccessfullyLogin() throws Exception {
        String jsonSource = getJson(INVALID_USER_CREDENTIALS_PATH);
        String response = mockMvc.perform(post(API_AUTH_SIGNIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSource))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expected = getJson(PATH_TO_LOGIN_ERROR);
        JSONAssert.assertEquals(expected, response, JSONCompareMode.LENIENT);
    }

    private String getJson(String pathToJsonFile) throws IOException, ParseException {
        JSONObject jsonObj = JsonParserUtil.parseJsonToObject(pathToJsonFile);
        return jsonObj.toString();
    }

}