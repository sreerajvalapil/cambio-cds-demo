package com.cambio.cds.domain;

import com.cambio.cds.persistence.CdsModelDocument;
import com.cambio.cds.persistence.CdsModelKeyword;
import com.cambio.cds.persistence.CdsModelRepository;
import com.cambio.cds.rest.dto.CdsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CdsSearchServiceTest {

    @Mock
    private CdsModelRepository cdsModelRepository;

    private CdsSearchService cdsSearchService ;

    @BeforeEach
    void setUp() {
        cdsSearchService = new CdsSearchService(cdsModelRepository);
    }

    @DisplayName("Test the global search ")
    @Test
    void testGlobalSearch() {
        given(this.cdsModelRepository.findAll())
                .willReturn(Arrays.asList(
                        CdsModelDocument.builder().modelId("test1")
                        .keywords(Arrays.asList(CdsModelKeyword.builder().language("en")
                                .keyword("HF").build())).build(),
                        CdsModelDocument.builder().modelId("test2")
                                .keywords(Arrays.asList(CdsModelKeyword.builder().language("sv")
                                        .keyword("BD").build())).build()
                ));
        List<CdsModel> results = cdsSearchService.searchForCdsModel("*",null);
        assertEquals(2, results.size());
        assertEquals("test1", results.get(0).getId());
    }

    @DisplayName("Test the search term")
    @Test
    void testWithSearchTerm() {
        given(this.cdsModelRepository.findByKeywordsKeyword("Vaccination"))
                .willReturn(Arrays.asList(CdsModelDocument.builder().modelId("test5")
                        .keywords(Arrays.asList(CdsModelKeyword.builder().language("en")
                                .keyword("Vaccination").build())).build()));
        List<CdsModel> results = cdsSearchService.searchForCdsModel("Vaccination",null);
        assertEquals("test5", results.get(0).getId());
    }


    @DisplayName("Scopped search with language and SearchField")
    @Test
    void testWithLanguageAndSearchField() {
        given(this.cdsModelRepository.findByKeywordsLanguage("en"))
                .willReturn(Arrays.asList(CdsModelDocument.builder().modelId("vaccination_model1")
                        .keywords(Arrays.asList(CdsModelKeyword.builder().language("en")
                                .keyword("HF").build())).build()));
        List<CdsModel> results = cdsSearchService.searchForCdsModel("en","keywords");
        assertEquals("vaccination_model1", results.get(0).getId());
    }




}