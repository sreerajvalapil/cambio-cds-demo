package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsSearchService;
import com.cambio.cds.rest.dto.CdsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/models")
public class CdsSearchController {

    @Autowired
    private CdsSearchService cdsSearchService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CdsModel> searchModel(
            @RequestParam(value = "search", required = true) String search,
            @RequestParam(value = "searchFields", required = true) String searchFields
    ) {
        //cds-search-service/api/models?search=en&searchFields=keywords.
        //api/models?search=en&searchFields=abc
        System.out.println("I am form the controller " + search + " " +searchFields);
        return cdsSearchService.searchForCdsModel(search,searchFields);
    }

}
