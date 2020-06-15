package com.cambio.cds.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CdsModelRepository extends ElasticsearchRepository<CdsModelDocument,String> {

    Stream<CdsModelDocument> findByKeywordsIn(List<CdsModelKeyword> keywords);

    Stream<CdsModelDocument> findByModelId(String modelId);

    Stream<CdsModelDocument> findByKeywordsLanguage(String languageName);

}
