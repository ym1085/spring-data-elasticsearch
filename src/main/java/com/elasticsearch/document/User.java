package com.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

//@Document(indexName = IndicesHelper.USER_INDEX_NAME)
//@Setting(settingPath = "static/indices/es-settings.json")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder(value = {"id", "userName", "userId", "userPhoneNum", "userAddress", "created_at", "updatedAt"})
public class User {

    @JsonProperty("id")
    private String id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_phone_num")
    private String userPhoneNum;

    @JsonProperty("user_address")
    private String userAddress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("created_at")
    private String created_at;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("updated_at")
    private Date updatedAt;

}
