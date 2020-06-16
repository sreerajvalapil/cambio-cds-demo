package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsSearchService;
import com.cambio.cds.rest.dto.CdsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CdsSearchController.class)
class CdsSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper ;

    @MockBean
    private CdsSearchService cdsSearchService;

    @BeforeEach
    void setup() {

    }

   @Test
    public void testSearchWithLanguageAndSearchField() throws Exception {
       List<CdsModel> cdsList = Arrays.asList(CdsModel.builder().id("id1")
               .keywordDetails(Collections.singletonMap("en", Arrays.asList("Vaccination","HF")))
               .url("http://localhost:8080/api/models/Chronic_pain_pregabalin.v1")
               .build()) ;
        given(this.cdsSearchService.searchForCdsModel("en","Vaccination"))
                .willReturn(cdsList);
       MvcResult mvcResult = mockMvc.perform(get("/api/models?search=en&searchFields=Vaccination"))
                .andExpect(status().isOk())
                .andReturn();
       String actualResponseBody = mvcResult.getResponse().getContentAsString();

       Assertions.assertTrue(objectMapper.writeValueAsString(cdsList)
               .equals(actualResponseBody));
    }

    @Test
    public void testSearchWithLanguageOnly() throws Exception {
        List<CdsModel> cdsList = Arrays.asList(CdsModel.builder().id("id1")
                .keywordDetails(Collections.singletonMap("en", Arrays.asList("UF","HF")))
                .url("http://localhost:8080/api/models/Chronic_pain_pregabalin.v1")
                .build()) ;

        given(this.cdsSearchService.searchForCdsModel("en",null))
                .willReturn(cdsList);

        MvcResult mvcResult = mockMvc.perform(get("/api/models?search=en"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(objectMapper.writeValueAsString(cdsList)
                .equals(actualResponseBody));
    }

}