package com.Hyperfume.Backend.service.redis;

import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShipmentRedisService {
    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;

    public ShipmentRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static final String SHIPMENT_INFO_PREFIX = "shipment_info:";
    private static final int CACHE_TTL_MINUTES = 30;

    public String cacheShipmentInfo(ShipmentResponse shipmentInfo){
        String token = generateUniqueToken();

        String key = SHIPMENT_INFO_PREFIX + token;

        redisTemplate.opsForValue().set(key, shipmentInfo);
        redisTemplate.expire(key, CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        return token;
    }

    public ShipmentResponse getShipmentInfo(String token){
        String key = SHIPMENT_INFO_PREFIX + token;

        Object obj = redisTemplate.opsForValue().get(key);

        if (obj == null) {
            log.warn("No shipment info found for token: {}", token);
            return null;
        }

        return objectMapper.convertValue(obj, ShipmentResponse.class);
    }


    public void deleteShipmentInfo(String token){
        String key = SHIPMENT_INFO_PREFIX + token;
        redisTemplate.delete(key);
        log.info("Removed shipment info with token: {}", token);
    }

    private String generateUniqueToken(){
        return UUID.randomUUID().toString();
    }

}
