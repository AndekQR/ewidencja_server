package com.example.ewidencja.controllerTest;

import com.example.ewidencja.model.User;
import com.example.ewidencja.security.AuthenticationRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testUtilities.TestUtilities.toJson;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Test
    public void loginTest() throws Exception {
        String username="daniellegawiec20@gmail.com";
        String password="admin123";
        AuthenticationRequest authenticationRequest=new AuthenticationRequest(username, password);
        String authReqJson=toJson(authenticationRequest);

        ResultActions result
                =mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authReqJson)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));


        String resultString=result.andReturn().getResponse().getContentAsString();
        String email = new JSONObject(resultString).getJSONObject("user").getString("email");
        Assertions.assertEquals(email, username);

    }
}


