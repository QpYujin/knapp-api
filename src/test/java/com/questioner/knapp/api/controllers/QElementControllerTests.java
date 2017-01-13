package com.questioner.knapp.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questioner.knapp.api.services.QElementService;
import com.questioner.knapp.api.services.QTypeService;
import com.questioner.knapp.core.models.QElement;
import com.questioner.knapp.core.models.QType;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class QElementControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QElementService qElementService;

    @Autowired
    private QTypeService qTypeService;

    private QType mulChoi;
    private QType filBlnk;

    @Before
    public void init() throws Exception {
        String data = "{\"name\": \"MultipleChoice\",\"description\": \"multiple-choice\",\"comments\": \"multiple-choice\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MultipleChoice"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        mulChoi = new ObjectMapper().readValue(content, QType.class);

        data = "{\"name\": \"FillInTheBlanks\",\"description\": \"fill in the blanks\",\"comments\": \"fill in the blanks\"}";
        result = this.mockMvc.perform(post("/knapp/qtype")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FillInTheBlanks"))
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
        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"));
    }

    @Test
    public void getQElementSuccess() throws Exception {

        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement qElement = new ObjectMapper().readValue(content, QElement.class);

        this.mockMvc.perform(get("/knapp/QElement/{id}", qElement.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andExpect(jsonPath("$.qtype.id").value(mulChoi.getId()));
    }

    @Test
    public void getQElementFail() throws Exception {
        this.mockMvc.perform(get("/knapp/QElement/{id}", "500"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void updateQElement() throws Exception {
        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/QElement")
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

        result = this.mockMvc.perform(put("/knapp/QElement/{id}", qElement.getId().toString())
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
        MvcResult result = this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement QElement = new ObjectMapper().readValue(content, QElement.class);

        Long deletedId = QElement.getId();

        this.mockMvc.perform(delete("/knapp/QElement/{id}", QElement.getId()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/knapp/QElement/{id}", deletedId))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void findAllQElement() throws Exception {

        String data = "{\"qTypeId\": \"" + mulChoi.getId() + "\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("RadioButton"));

        data = "{\"qTypeId\": \"" + filBlnk.getId() + "\",\"uiview\": \"Box\",\"comments\": \"updating element\"}";
        this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.uiview").value("Box"));

        MvcResult result = this.mockMvc.perform(get("/knapp/QElement"))
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
