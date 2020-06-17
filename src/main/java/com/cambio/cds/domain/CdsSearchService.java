package com.cambio.cds.domain;

import com.cambio.cds.persistence.CdsModelDocument;
import com.cambio.cds.persistence.CdsModelKeyword;
import com.cambio.cds.persistence.CdsModelRepository;
import com.cambio.cds.rest.dto.CdsModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CdsSearchService {

    public static final List<String> searchFieldList =
            Collections.unmodifiableList(Arrays.asList("keywords"));

    private final CdsModelRepository cdsModelRepository;

    public CdsSearchService(CdsModelRepository cdsModelRepository) {
        this.cdsModelRepository = cdsModelRepository;
    }

    public List<CdsModel> searchForCdsModel(String languageOrTerm, String searchField) {

        List<CdsModel> results;
        List<CdsModelDocument> cdsModelDocumentList;
        if (StringUtils.isEmpty(searchField)) {
            /* Global search with search term for all language  ,
             if '*' is searched then fetch all results , otherwise search by keyword exact match  */
            cdsModelDocumentList = languageOrTerm.equals("*") ? cdsModelRepository.findAll()
                    : cdsModelRepository.findByKeywordsKeyword(languageOrTerm);
            results = cdsModelDocumentList.stream().map(cdsModelDocument -> toCdsModel(cdsModelDocument, null))
                    .collect(Collectors.toList());

        } else {
            // Assuming that , this is a scoped search with languageOrTerm and SearchField
            if (!searchFieldList.contains(searchField)) {
                throw new IllegalArgumentException("Invalid searchField passed : " + searchField);
            }
            cdsModelDocumentList = cdsModelRepository.findByKeywordsLanguage(languageOrTerm);
            results = cdsModelDocumentList.stream().map(cdsModelDocument -> toCdsModel(cdsModelDocument, languageOrTerm))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private CdsModel toCdsModel(CdsModelDocument cdsModelDocument, String language) {
        String url = generateUrl(cdsModelDocument);
        List<CdsModelKeyword> keywordList = new ArrayList<>();
        if (language != null) {
            keywordList = cdsModelDocument.getKeywords().stream()
                    .filter(cdsModelKeyword -> cdsModelKeyword.getLanguage().equals(language))
                    .collect(Collectors.toList());
        } else {
            keywordList = cdsModelDocument.getKeywords();
        }

        Map<String, List<String>> keywordDetailsMap = new HashMap<>();
        for (CdsModelKeyword cdsKeyword : keywordList) {
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

    private String generateUrl(CdsModelDocument cdsModelDocument) {
        return ServletUriComponentsBuilder.
                fromCurrentContextPath().path("/api/models/" + cdsModelDocument.getModelId()).toUriString();
    }

}
