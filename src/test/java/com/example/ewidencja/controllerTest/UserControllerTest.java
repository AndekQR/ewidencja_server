package com.example.ewidencja.controllerTest;

import com.example.ewidencja.security.AuthenticationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testUtilities.TestUtilities.toJson;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String obtainAccessToken(String username, String password) throws Exception {

        AuthenticationRequest authenticationRequest=new AuthenticationRequest(username, password);
        String authReqJson = toJson(authenticationRequest);

        ResultActions result
                =mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authReqJson)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString=result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser=new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }

    @Test
    public void shouldReturnUsernameAndAuthorities() throws Exception {
        String accessToken=obtainAccessToken("daniellegawiec20@gmail.com", "admin123");
        mockMvc
                .perform(get("/me")
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"roles\":[{\"authority\":\"ROLE_USER\"}],\"username\":\"daniellegawiec20@gmail.com\"}"));
    }
}
