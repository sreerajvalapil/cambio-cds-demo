package com.cambio.cds.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "sweden-truck")
public class VolvoTruck {
    @EqualsAndHashCode.Include
    @Id
    @Field(store = true, type = FieldType.Text)
    private String modelId;

    @Field(type = FieldType.Integer)
    private Integer kmDriven;

    @Field(store = true, type  = FieldType.Keyword)
    private String regionName;

    @Field(type  = FieldType.Integer)
    private Integer regionCode;


    @Field(type = Date, format = DateFormat.basic_date_time)
    private LocalDateTime startTime ;

    @Field(type = Date, format = DateFormat.basic_date_time)
    private LocalDateTime firstServiceTime;

    @Field(type = Date, format = DateFormat.basic_date_time)
    private LocalDateTime nextServiceDue;

}
