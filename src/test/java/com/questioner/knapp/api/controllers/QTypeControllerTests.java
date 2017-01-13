package com.questioner.knapp.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questioner.knapp.api.services.QTypeService;
import com.questioner.knapp.core.models.QType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/application-test.properties")
public class QTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QTypeService qTypeService;

    @Before
    public void init() {
        List<QType> qTypes = qTypeService.findAll();
        for (QType qType : qTypes) qTypeService.delete(qType.getId());
    }

    @Test
    public void createQType() throws Exception {
        String data = "{\"name\": \"MultipleChoice\",\"description\": \"multiple-choice\",\"comments\": \"multiple-choice\"}";
        this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"));
    }

    @Test
    public void getQType() throws Exception {
        this.mockMvc.perform(get("/knapp/qtype/{id}", "500"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void updateQType() throws Exception {
        String data = "{\"name\": \"FillInTheBlanks\",\"description\": \"fill in the blanks\",\"comments\": \"fill in the blanks\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FillInTheBlanks"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QType responseQType = new ObjectMapper().readValue(content, QType.class);

        responseQType.setName("MultipleChoice");
        responseQType.setDescription("multiple-choice");
        responseQType.setComments("multiple-choice");

        this.mockMvc.perform(put("/knapp/qtype/{id}", responseQType.getId().toString())
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(responseQType)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"));
    }

    @Test
    public void deleteQType() throws Exception {
        String data = "{\"name\": \"MultipleChoice\",\"description\": \"multiple-choice\",\"comments\": \"multiple-choice\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QType responseQType = new ObjectMapper().readValue(content, QType.class);

        Long deletedId = responseQType.getId();

        this.mockMvc.perform(delete("/knapp/qtype/{id}", responseQType.getId()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/knapp/qtype/{id}", deletedId))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void findAllQTypes() throws Exception {

        String data = "{\"name\": \"MultipleChoice\",\"description\": \"multiple-choice\",\"comments\": \"multiple-choice\"}";
        this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"));


        MvcResult result = this.mockMvc.perform(get("/knapp/qtype"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<QType> qTypes = new ObjectMapper().readValue(content, new TypeReference<List<QType>>() {
        });

        assertTrue(qTypes.size() > 0);
    }

}
