package com.cambio.cds.persistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdsModelRepository extends ElasticsearchRepository<CdsModelDocument,String> {

    List<CdsModelDocument> findByKeywordsLanguage(String languageName);

    List<CdsModelDocument> findByKeywordsLanguageOrKeywordsKeyword(String languageName,String keyWord);

}
