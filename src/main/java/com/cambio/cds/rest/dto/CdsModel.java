package com.cambio.cds.rest.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CdsModel {
    private String id;
    private String url;

    private Map<String, List<String>> keywordDetails = new HashMap<>();


    @JsonAnyGetter
    public Map<String, List<String>> getKeywordDetails() {
        return keywordDetails;
    }

}
