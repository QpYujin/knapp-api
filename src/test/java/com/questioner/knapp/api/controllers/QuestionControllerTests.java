package com.questioner.knapp.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questioner.knapp.api.services.QElementService;
import com.questioner.knapp.api.services.QTypeService;
import com.questioner.knapp.api.services.QuestionService;
import com.questioner.knapp.core.models.QElement;
import com.questioner.knapp.core.models.QType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/application-test.properties")
public class QuestionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QElementService qElementService;

    @Autowired
    private QTypeService qTypeService;

    private QType mulChoi;
    private QType filBlnk;

    private static String QTYPE_DATA = "{\"name\": \"%s\",\"description\": \"%s\",\"comments\": \"%s\"}";

    private static String QDATA = "{\"qTypeId\": \"%s\",\"text\": \"%s\",\"description\": \"%s\"}";

    @Before
    public void init() throws Exception {

        String data = String.format(QTYPE_DATA, "MultipleChoice", "multiple-choice", "multiple-choice");

        MvcResult result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        mulChoi = new ObjectMapper().readValue(content, QType.class);

        data = String.format(QTYPE_DATA, "FillInTheBlanks", "fill in the blanks", "fill in the blanks");
        result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FillInTheBlanks"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        content = result.getResponse().getContentAsString();
        filBlnk = new ObjectMapper().readValue(content, QType.class);
    }

    @Before
    public void cleanup() {
        List<QElement> qElements = qElementService.findAll();
        for (QElement qElement : qElements) qElementService.delete(qElement.getId());

        List<QType> qTypes = qTypeService.findAll();
        for (QType qType : qTypes) qTypeService.delete(qType.getId());
    }

    @Test
    public void createQElement() throws Exception {
        String data = String.format(QDATA, mulChoi.getId(), "What is your name", "Provide your name");
        this.mockMvc.perform(post("/knapp/question")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("What is your name"))
                .andExpect(jsonPath("$.description").value("Provide your name"))
                .andExpect(jsonPath("$.qtype.name").value("MultipleChoice"))
                .andExpect(jsonPath("$.qtype.id").value(mulChoi.getId()));
    }

    @Test
    public void getQElementSuccess() throws Exception {

        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qelement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement qElement = new ObjectMapper().readValue(content, QElement.class);

        this.mockMvc.perform(get("/knapp/qelement/{id}", qElement.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andExpect(jsonPath("$.qtype.id").value(mulChoi.getId()));
    }

    
    @Test
    public void getQElementFail() throws Exception {
        this.mockMvc.perform(get("/knapp/qelement/{id}", "500"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    
    @Test
    public void updateQElement() throws Exception {
        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qelement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement qElement = new ObjectMapper().readValue(content, QElement.class);

        qElement.setqTypeId(filBlnk.getId());
        qElement.setUiview("Box");
        qElement.setComments("updated element type");
        String updatedContent = new ObjectMapper().writeValueAsString(qElement);

        result = this.mockMvc.perform(put("/knapp/qelement/{id}", qElement.getId().toString())
                .contentType(MediaType.APPLICATION_JSON).content(updatedContent))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("Box"))
                .andReturn();

        content = result.getResponse().getContentAsString();
        qElement = new ObjectMapper().readValue(content, QElement.class);

        assertEquals(filBlnk.getId(), qElement.getqTypeId());
    }

    
    @Test
    public void deleteQElement() throws Exception {
        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qelement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement QElement = new ObjectMapper().readValue(content, QElement.class);

        Long deletedId = QElement.getId();

        this.mockMvc.perform(delete("/knapp/qelement/{id}", QElement.getId()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/knapp/qelement/{id}", deletedId))
                .andDo(print()).andExpect(status().isNotFound());
    }

    
    @Test
    public void findAllQElement() throws Exception {

        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        this.mockMvc.perform(post("/knapp/qelement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"));

        data = "{\"qTypeId\": \"" + filBlnk.getId() + "\",\"uiview\": \"Box\",\"comments\": \"updating element\"}";
        this.mockMvc.perform(post("/knapp/qelement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("Box"));

        MvcResult result = this.mockMvc.perform(get("/knapp/qelement"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<QElement> qTypes = new ObjectMapper().readValue(content, new TypeReference<List<QElement>>() {
        });

        assertTrue(qTypes.size() > 0);
        assertEquals(qTypes.size(), 2);
    }

}
