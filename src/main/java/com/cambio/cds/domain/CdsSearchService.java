package com.cambio.cds.domain;

import com.cambio.cds.persistence.CdsModelDocument;
import com.cambio.cds.persistence.CdsModelKeyword;
import com.cambio.cds.persistence.CdsModelRepository;
import com.cambio.cds.rest.dto.CdsModel;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class CdsSearchService {

    @Autowired
    private CdsModelRepository cdsModelRepository ;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    public List<CdsModel> searchForCdsModel(String language, String searchField) {
        List<CdsModel> searchResponselist = new ArrayList<>() ;
        saveDocument();


/*        BoolQueryBuilder outer = QueryBuilders.boolQuery();

        outer.must(QueryBuilders.matchQuery("modelId","test1"));*/

        String field = "keywords";
        final Query searchQuery = new NativeSearchQueryBuilder().
                withQuery(matchQuery(field,
                        Arrays.asList(CdsModelKeyword.builder().language(language).keyword(searchField).build())))
                .build();
        System.out.println("the query is .... : " +searchQuery.toString());
       /* final SearchHits<CdsModelDocument> articles =
                elasticsearchRestTemplate.search(searchQuery, CdsModelDocument.class,
                        IndexCoordinates.of("cds-model"));*/

        Stream<CdsModelDocument> responseList = cdsModelRepository.findByKeywordsIn(
                Arrays.asList(CdsModelKeyword.builder().language(language).keyword(searchField).build()));

        //Stream<CdsModelDocument> responseList =  cdsModelRepository.findByUrl("https://www.google.com") ;


        return searchResponselist;

    }

    private void saveDocument() {
        // for testing
        Map<String, List<String>> map = new HashMap<>();
        map.put("en", Arrays.asList("keyword1","keyword2"));
        map.put("sv", Arrays.asList("keywordö","keywordä"));

        List<CdsModelKeyword> cdsModelKeywordList =
                Arrays.asList(CdsModelKeyword.builder().language("en").keyword("keyword1").build(),
                CdsModelKeyword.builder().language("sv").keyword("keyword2").build());

        CdsModelDocument cdsModelDocument = CdsModelDocument.builder()
                .modelId("test1")
                .keywords(cdsModelKeywordList)
                .url("https://www.google.com")
                .build();
        cdsModelRepository.save(cdsModelDocument);
    }
}
