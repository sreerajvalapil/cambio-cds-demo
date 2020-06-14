package com.cambio.cds.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CdsModelKeyword {

    @Field(type = Text)
    private String language;

    @Field(type = Text)
    private String keyword;
}
