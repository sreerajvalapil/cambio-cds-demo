package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsModelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CdsModelController.class)
public class CdsModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CdsModelService cdsModelService;

    @BeforeEach
    void setup() {
    }


    @Test
    public void testUpload() throws Exception {
        MockMultipartFile cdsModelFile = new
                MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        doNothing()
                .when(this.cdsModelService).uploadFile(cdsModelFile);
        mockMvc.perform(multipart("/api/models/upload")
                .file(cdsModelFile)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

   @Test
    public void testDownload() throws Exception {

        String modelId = "test1" ;
        byte[] results = "sample values".getBytes();
        given(this.cdsModelService.downloadCdsModel(modelId))
                .willReturn(results);
        MvcResult mvcResult = mockMvc.perform(get("/api/models/"+modelId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();
       Assertions.assertTrue("sample values".equals(mvcResult.getResponse().getContentAsString()));

    }

    @Test
    public void testDeleteModel() throws Exception {
        String modelId = "test1" ;
        doNothing().when(this.cdsModelService).deleteCdsModel(modelId);

        final String expectedResponse = "[File with model id " + modelId + "] deleted successfully.";

        MvcResult mvcResult = mockMvc.perform(delete("/api/models/delete/"+modelId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertTrue(expectedResponse.equals(actualResponseBody));

    }

}
