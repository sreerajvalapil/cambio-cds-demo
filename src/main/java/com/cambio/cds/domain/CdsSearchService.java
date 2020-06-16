package com.cambio.cds.domain;

import com.cambio.cds.persistence.CdsModelDocument;
import com.cambio.cds.persistence.CdsModelKeyword;
import com.cambio.cds.persistence.CdsModelRepository;
import com.cambio.cds.rest.dto.CdsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CdsSearchService {

    private final CdsModelRepository cdsModelRepository;

    public CdsSearchService(CdsModelRepository cdsModelRepository) {
        this.cdsModelRepository = cdsModelRepository;
    }

    public List<CdsModel> searchForCdsModel(String language, String searchField) {


        List<CdsModelDocument> cdsModelDocumentList = new ArrayList<>();
        if (searchField.isBlank()) {
            cdsModelDocumentList = cdsModelRepository.findByKeywordsLanguage(language);
        } else {
            // Exact match , Can use query to improve the search
            cdsModelDocumentList = cdsModelRepository.findByKeywordsLanguageOrKeywordsKeyword(language, searchField);

        }
        return cdsModelDocumentList.stream().map(this::toCdsModel)
                .collect(Collectors.toList());

    }

    private CdsModel toCdsModel(CdsModelDocument cdsModelDocument) {
        String url = ServletUriComponentsBuilder.
                fromCurrentContextPath().path("/api/model/" + cdsModelDocument.getModelId()).toUriString();

        Map<String, List<String>> keywordDetailsMap = new HashMap<>();
        for (CdsModelKeyword cdsKeyword : cdsModelDocument.getKeywords()) {
            String mapKey = "keywords_" + cdsKeyword.getLanguage();
            List<String> mapValue = keywordDetailsMap.get(mapKey);
            if (mapValue == null) {
                keywordDetailsMap.put(mapKey, new ArrayList<>(Arrays.asList(cdsKeyword.getKeyword())));
            } else {
                mapValue.add(cdsKeyword.getKeyword());
            }
        }
        return CdsModel.builder().id(cdsModelDocument.getModelId())
                .keywordDetails(keywordDetailsMap)
                .url(url)
                .build();
    }

}
