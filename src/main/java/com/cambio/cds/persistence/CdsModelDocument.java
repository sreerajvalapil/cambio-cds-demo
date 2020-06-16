package com.cambio.cds.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "cds-model")
public class CdsModelDocument {

    @Id
    @Field(store = true, type = FieldType.Text)
    private String modelId;

    @Field(type = Nested, includeInParent = true)
    @Builder.Default
    private List<CdsModelKeyword> keywords = new ArrayList<>();

}
