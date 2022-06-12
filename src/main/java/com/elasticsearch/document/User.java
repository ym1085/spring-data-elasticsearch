package com.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Builder
    public User(String id, String userName, String userId, String userPhoneNum, String userAddress, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userPhoneNum = userPhoneNum;
        this.userAddress = userAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
