package com.elasticsearch.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("테스트 컨트롤러")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TestController.class)
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName(value = "테스트 컨트롤러 문자열 출력 확인")
    void test() throws Exception {
        MultiValueMap<String, String> content = new LinkedMultiValueMap<>();
        content.set("title", "Spring Data Elasticsearch");

        mockMvc.perform(
                get("/api/test")
                        .params(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Spring Data Elasticsearch intro."))
                .andDo(print());
    }
}