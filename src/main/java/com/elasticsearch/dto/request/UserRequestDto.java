package com.elasticsearch.dto.request;

import com.elasticsearch.document.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * 회원 정보 조회 시 사용되는 DTO, @NotNull로 좀 유연하게 처리
 *
 * @author ymkim
 * @since 2022.03.05 Sun 17:15
 */
@Getter
@ToString
@NoArgsConstructor
public class UserRequestDto {

    @NotNull
    private String id;

    @NotNull
    private String userName;

    @NotNull
    private String userId;

    @NotNull
    private String userPhoneNum;

    @NotNull
    private String userAddress;

    @NotNull
    @PastOrPresent
    private String createdAt;

    @NotNull
    @PastOrPresent
    private Date updatedAt;

    @Builder
    public UserRequestDto(String id,
                          String userName,
                          String userId,
                          String userPhoneNum,
                          String userAddress,
                          String createdAt,
                          Date updatedAt) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userPhoneNum = userPhoneNum;
        this.userAddress = userAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /* Document Entity 자체를 layer 계층에서 사용하고 싶지 않아서 builder 사용 */
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
