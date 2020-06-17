package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsSearchService;
import com.cambio.cds.rest.dto.CdsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/models")
public class CdsSearchController {

    private final CdsSearchService cdsSearchService;

    public CdsSearchController(CdsSearchService cdsSearchService) {
        this.cdsSearchService = cdsSearchService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CdsModel> searchModel(
            @RequestParam(value = "search", required = true) String search,
            @RequestParam(value = "searchFields", required = false) String searchFields
    ) {
        return cdsSearchService.searchForCdsModel(search, searchFields);
    }

}
