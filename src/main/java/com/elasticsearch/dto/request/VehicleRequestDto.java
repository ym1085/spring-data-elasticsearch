package com.elasticsearch.dto.request;

import com.elasticsearch.document.User;
import com.elasticsearch.document.Vehicle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

/**
 * 차량 정보 조회 시 사용되는 DTO
 *
 * @author ymkim
 * @since 2022.03.05 Sun 19:03
 */
@Getter
@ToString
@NoArgsConstructor
public class VehicleRequestDto {

    @NotNull
    private String id;

    @NotNull
    private String vehicleNumber;

    @NotNull
    private String vehicleName;

    @NotNull
    private String vehicleColor;

    @NotNull
    private String vehicleRemainAmount;

    @NotNull
    private String vehiclePrice;

    @NotNull
    @PastOrPresent
    private Date createdAt;

    @NotNull
    @PastOrPresent
    private Date updatedAt;

    @Builder
    public Vehicle toEntity() {
        return Vehicle.builder()
                .id(id)
                .vehicleNumber(vehicleNumber)
                .vehicleName(vehicleName)
                .vehicleColor(vehicleColor)
                .vehicleRemainAmount(vehicleRemainAmount)
                .vehiclePrice(vehiclePrice)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
