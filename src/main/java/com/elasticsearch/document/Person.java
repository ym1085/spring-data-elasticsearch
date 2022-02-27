package com.elasticsearch.document;

import com.elasticsearch.helper.IndicesHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = IndicesHelper.PERSON_IDX)
@Setting(settingPath = "static/indices/es-settings.json")
@Getter
@Setter
public class Person {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

}
