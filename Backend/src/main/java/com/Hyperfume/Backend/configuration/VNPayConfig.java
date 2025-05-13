package com.Hyperfume.Backend.configuration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.vnpay")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayConfig {
    String version;
    String command;
    String tmnCode;
    String hashSecret;
    String returnUrl;
    String paymentUrl;
    String apiUrl;
    String refundUrl;
    int expireTime;
}
