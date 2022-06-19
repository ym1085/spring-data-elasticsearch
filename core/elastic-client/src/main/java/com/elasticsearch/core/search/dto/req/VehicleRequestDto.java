package com.elasticsearch.core.search.dto.req;

import com.elasticsearch.core.document.Vehicle;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class VehicleRequestDto {

    @Data
    public static class request {
        @NotNull
        private String id;

        @NotNull
        @Min(1) @Max(6)
        private String vehicleNumber;

        @NotNull
        @Min(1) @Max(10)
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
}
