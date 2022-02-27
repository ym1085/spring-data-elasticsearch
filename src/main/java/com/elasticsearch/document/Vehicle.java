package com.elasticsearch.document;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // FIXME: https://github.com/FasterXML/jackson-databind/issues/2715
@JsonPropertyOrder(value = {"id", "vehicle_number", "vehicle_name", "created_at", "updated_at"})
public class Vehicle {
//    @JsonIgnore
    @JsonProperty("id")
    private String id;

    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    @JsonProperty("vehicle_name")
    private String vehicleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("updated_at")
    private Date updatedAt;
}
