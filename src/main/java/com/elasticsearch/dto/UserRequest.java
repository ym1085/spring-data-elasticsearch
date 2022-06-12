package com.elasticsearch.dto;

import com.elasticsearch.document.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class UserRequest {

    @Data
    public static class request {
        @NotNull
        private String id;

        @NotNull
        private String userName;

        @NotNull
        @Max(30)
        private String userId;

        @NotNull
        @Max(11)
        private String userPhoneNum;

        @NotNull
        private String userAddress;

        @NotNull
        @PastOrPresent
        private Date createdAt;

        @NotNull
        @PastOrPresent
        private Date updatedAt;

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .userName(userName)
                    .userId(userId)
                    .userPhoneNum(userPhoneNum)
                    .userAddress(userAddress)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        }
    }
}