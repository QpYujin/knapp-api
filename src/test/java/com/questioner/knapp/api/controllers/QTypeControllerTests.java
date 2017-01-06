/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.questioner.knapp.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.questioner.knapp.core.model.QType;
import com.questioner.knapp.core.models.QType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createQType() throws Exception {
        String data = "{\"description\": \"frisco\",\"comments\": \"TX\"}";
        this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("frisco"));
    }

    @Test
    public void getQType() throws Exception {
        this.mockMvc.perform(get("/knapp/qtype/{id}", "500"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void updateQType() throws Exception {
        String data = "{\"description\": \"test-description\",\"comments\": \"test-comments\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test-description"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QType responseQType = new ObjectMapper().readValue(content, QType.class);

        responseQType.setDescription("test-description-updated");
        responseQType.setComments("test-comments-updated");

        this.mockMvc.perform(put("/knapp/qtype/{id}", responseQType.getId().toString())
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(responseQType)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("test-description-updated"));
    }

}
