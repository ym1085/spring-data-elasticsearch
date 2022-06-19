package com.elasticsearch.core.document;

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
@JsonPropertyOrder(
        value = {
                "id",
                "vehicle_number",
                "vehicle_name",
                "vehicle_remain_amount",
                "vehicle_price",
                "created_at",
                "updated_at"
        }
)
public class Vehicle {

    @JsonProperty("id")
    private String id;

    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonProperty("vehicle_color")
    private String vehicleColor;

    @JsonProperty("vehicle_remain_amount")
    private String vehicleRemainAmount;

    @JsonProperty("vehicle_price")
    private String vehiclePrice;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Builder
    public Vehicle(String id, String vehicleNumber, String vehicleName, String vehicleColor,
                   String vehicleRemainAmount, String vehiclePrice, Date createdAt, Date updatedAt) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.vehicleName = vehicleName;
        this.vehicleColor = vehicleColor;
        this.vehicleRemainAmount = vehicleRemainAmount;
        this.vehiclePrice = vehiclePrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
