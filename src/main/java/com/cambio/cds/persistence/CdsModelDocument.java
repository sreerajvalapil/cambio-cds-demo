package com.cambio.cds.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "cds-model")
public class CdsModelDocument {

    @Id
    private String modelId;

    @Field(type = Nested, includeInParent = true)
    private List<CdsModelKeyword> keywords;


/*    @Field(type = FieldType.Object)
    @Builder.Default
    private Map<String, List<String>> countryKeyWordMap = new HashMap<>();*/

    @NonNull
    @Field(store = true, type = FieldType.Text)
    private String url;



}
