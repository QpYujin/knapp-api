package com.questioner.knapp.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.questioner.knapp.core.models.QElement;
import com.questioner.knapp.core.models.QType;
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

    @Test
    public void createQElement() throws Exception {
        String data = "{\"elementtype\": \"MutlipleChoice\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.elementtype").value("MutlipleChoice"));
    }

    @Test
    public void getQElement() throws Exception {
        this.mockMvc.perform(get("/knapp/QElement/{id}", "500"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void updateQElement() throws Exception {
        String data = "{\"elementtype\": \"MutlipleChoice\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.elementtype").value("MutlipleChoice"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        QElement QElement = new ObjectMapper().readValue(content, QElement.class);

        QElement.setElementtype("FillInTheBlanks");
        QElement.setUiview("EmptyLine");
        QElement.setComments("updated element type");

        this.mockMvc.perform(put("/knapp/QElement/{id}", QElement.getId().toString())
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(QElement)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.elementtype").value("FillInTheBlanks"));
    }

    @Test
    public void deleteQElement() throws Exception {
        String data = "{\"elementtype\": \"MutlipleChoice\",\"uiview\": \"RadioButton\",\"comments\": \"creating element\"}";
        MvcResult result = this.mockMvc.perform(post("/knapp/QElement")
                .contentType(MediaType.APPLICATION_JSON).content(data))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.elementtype").value("MutlipleChoice"))
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
        MvcResult result = this.mockMvc.perform(get("/knapp/QElement"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<QElement> qTypes = new ObjectMapper().readValue(content, new TypeReference<List<QElement>>() {
        });

        assertTrue(qTypes.size() > 0);
    }

}
