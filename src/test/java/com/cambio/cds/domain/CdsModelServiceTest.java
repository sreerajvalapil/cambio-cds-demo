package com.cambio.cds.domain;

import com.amazonaws.services.s3.AmazonS3;
import com.cambio.cds.persistence.entity.CdsModelDocument;
import com.cambio.cds.persistence.entity.CdsModelKeyword;
import com.cambio.cds.persistence.repository.CdsModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class CdsModelServiceTest {

    @Mock
    private CdsModelRepository cdsModelRepository;

    @Mock
    private  AmazonS3 s3client;

    private CdsModelService cdsModelService ;

    @BeforeEach
    void setUp() {
        cdsModelService = new CdsModelService(s3client,cdsModelRepository);
    }

    @Test
    void testUploadFile() {
        String file = "{   \"id\": \"chf_vaccination_recommendation.v1.test\",   \"gdl_version\": \"2.0\",   \"concept\": \"gt0001\",   \"language\": {     \"original_language\": \"ISO_639-1::en\"   },   \"description\": {     \"details\": {       \"en\": {         \"id\": \"en\",         \"keywords\": [           \"HF\",           \"Vaccination\"         ]       }     } }";
        MockMultipartFile cdsModelFile = new
                MockMultipartFile("file", "filename.txt", "text/plain", file.getBytes());
        CdsModelDocument doc = CdsModelDocument.builder()
                .modelId("chf_vaccination_recommendation.v1.test")
                .keywords(Arrays.asList(CdsModelKeyword.builder().language("en")
                        .keyword("HF").build(),CdsModelKeyword.builder().language("en")
                        .keyword("Vaccination").build())).build() ;
        given(this.cdsModelRepository.save(any(CdsModelDocument.class)))
                .willReturn(doc);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cdsModelService.uploadFile(cdsModelFile);
        })
    }

    @Test
    void testUploadInvalidFile() {
        String file = "test";
        MockMultipartFile cdsModelFile = new
                MockMultipartFile("file", "filename.txt", "text/plain", file.getBytes());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cdsModelService.uploadFile(cdsModelFile);
        });
    }

    @Test
    void testDeleteFile() {
        String cdsModelId = "chf_vaccination_recommendation.v1.test";
        doNothing().when(this.cdsModelRepository).delete(any(CdsModelDocument.class));
        doNothing().when(this.s3client).deleteObject(eq("cds-model-docs"), eq(cdsModelId));
        cdsModelService.deleteCdsModel(cdsModelId);
    }

    @Test
    void testDownloadFile() {
        String cdsModelId = "chf_vaccination_recommendation.v1.test";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cdsModelService.downloadCdsModel(cdsModelId);
        });
    }








}