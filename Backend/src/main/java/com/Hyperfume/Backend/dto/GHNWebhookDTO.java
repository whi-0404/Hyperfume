package com.Hyperfume.Backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GHNWebhookDTO {
    @JsonProperty("OrderCode")
    String orderCode;
    @JsonProperty("Status")
    String status;
    @JsonProperty("Warehouse")
    String wareHouse;
    @JsonProperty("Time")
    @JsonFormat(
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX",
            timezone = "Asia/Ho_Chi_Minh"
    )
    LocalDateTime time;
    @JsonProperty("Description")
    String description;
}
