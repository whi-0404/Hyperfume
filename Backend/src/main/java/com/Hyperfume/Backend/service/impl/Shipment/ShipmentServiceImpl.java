package com.Hyperfume.Backend.service.impl.Shipment;

import com.Hyperfume.Backend.dto.request.ShipmentRequest;
import com.Hyperfume.Backend.dto.request.ShippingAddressRequest;
import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.util.ParseAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentServiceImpl implements ShipmentService {

    @NonFinal
    @Value("${shipment.GHN.token}")
    protected String token;

    @NonFinal
    @Value("${shipment.GHN.shop-id}")
    protected int shopId;

    @NonFinal
    @Value("${shipment.GHN.client-id}")
    protected String clientId;

    @NonFinal
    @Value("${shipment.GHN.api.fee}")
    protected String getFeeApi;

    @NonFinal
    @Value("${shipment.GHN.api.lead-time}")
    protected String getExpectedDeliveryDateApi;

    @NonFinal
    @Value("${shipment.GHN.api.service}")
    protected String getServiceApi;

    @NonFinal
    @Value("${shipment.GHN.from-address}")
    protected String fromAddressKey;


    ShipmentAddressService shipmentAddressService;
    RestTemplate restTemplate = new RestTemplate();

    public ShipmentResponse getShipmentOrderInfos(ShipmentRequest request) {
        Map<String, String> toAddress = ParseAddress.parseAddress(request.getShippingAddressRequest().getShipAddress());
        Map<String, String> fromAddress = ParseAddress.parseAddress(fromAddressKey);

        int toProvinceId = shipmentAddressService.getProvinceId(toAddress.get("province"));
        int toDistrictId = shipmentAddressService.getDistrictId(toProvinceId, toAddress.get("district"));
        String toWardCode = shipmentAddressService.getWardCode(toDistrictId, toAddress.get("ward"));

        int fromProvinceId = shipmentAddressService.getProvinceId(fromAddress.get("province"));
        int fromDistrictId = shipmentAddressService.getDistrictId(fromProvinceId, fromAddress.get("district"));
        String fromWardCode = shipmentAddressService.getWardCode(fromDistrictId, fromAddress.get("ward"));

        int weight = request.getQuantity() * 350;

        List<Map<String, Object>> serviceList = getServiceList(fromDistrictId, toDistrictId);
        log.info("Service list: {}", serviceList);

        int serviceId;
        String serviceName;

//        if (weight <= 49000) {
            // (≤ 49kg)
            serviceName = "Hàng nhẹ";
            Map<String, Object> lightService = findServiceByName(serviceList, serviceName);
            if (lightService != null) {
                serviceId = getIntValue(lightService.get("service_id"));
            } else {
                throw new IllegalStateException("Không tìm thấy dịch vụ hàng nhẹ");
            }
//        } else {
//            // (> 49kg)
//            serviceName = "Hàng nặng";
//            Map<String, Object> heavyService = findServiceByName(serviceList, serviceName);
//            if (heavyService != null) {
//                serviceId = getIntValue(heavyService.get("service_id"));
//            } else {
//                throw new IllegalStateException("Không tìm thấy dịch vụ hàng nặng");
//            }
//        }

        LocalDate expectedDeliveryDate = getExpectedDeliveryDate(serviceId, fromDistrictId, fromWardCode,
                toDistrictId, toWardCode, weight);

        int fee = getFee(serviceId, fromDistrictId, fromWardCode, toDistrictId, toWardCode, weight);

        return ShipmentResponse.builder()
                .serviceId(serviceId)
                .serviceName(serviceName)
                .fee(fee)
                .expectedDeliveryDate(expectedDeliveryDate)
                .build();
    }

    public int getFee(Integer serviceId, int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int weight) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("token", token);
            headers.add("ShopId", String.valueOf(shopId));

            Map<String, Object> body = new HashMap<>();
            body.put("service_id", serviceId);
            body.put("from_district_id", fromDistrictId);
            body.put("from_ward_code", fromWardCode);
            body.put("to_district_id", toDistrictId);
            body.put("to_ward_code", toWardCode);
            body.put("weight", weight);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(getFeeApi, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseMap = response.getBody();

                if (responseMap != null && responseMap.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
                    return getIntValue(data.get("total"));
                } else {
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
                }
            } else {
                log.warn("Error response getting fee: {}", response.getStatusCode());
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAILED_GET_SHIPPING_FEE);
        }
    }

    public LocalDate getExpectedDeliveryDate(Integer serviceId, int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int weight) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("token", token);
            headers.add("ShopId", String.valueOf(shopId));

            Map<String, Object> body = new HashMap<>();
            body.put("service_id", serviceId);
            body.put("from_district_id", fromDistrictId);
            body.put("from_ward_code", fromWardCode);
            body.put("to_district_id", toDistrictId);
            body.put("to_ward_code", toWardCode);
            body.put("weight", weight);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(getExpectedDeliveryDateApi, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseMap = response.getBody();
                if (responseMap != null && responseMap.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
                    int timestamp = getIntValue(data.get("leadtime"));

                    LocalDate expectedDate = Instant.ofEpochSecond(timestamp)
                            .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                            .toLocalDate();

                    return expectedDate;
                } else {
                    log.warn("No data in response for expected delivery date.");
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
                }
            } else {
                log.warn("Error response getting expected delivery date: {}", response.getStatusCode());
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAILED_GET_EXPECTED_DELIVERY_DATE);
        }
    }


    public List<Map<String, Object>> getServiceList(int fromDistrictId, int toDistrictId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("token", token);

            Map<String, Object> body = new HashMap<>();
            body.put("shop_id", shopId);
            body.put("from_district", fromDistrictId);
            body.put("to_district", toDistrictId);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(getServiceApi, requestEntity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("data")) {
                    return (List<Map<String, Object>>) responseBody.get("data");

                } else {
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
                }
            } else {
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAILED_GET_SERVICE_LIST);
        }
    }

    private Map<String, Object> findServiceByName(List<Map<String, Object>> serviceList, String serviceName) {
        return serviceList.stream()
                .filter(service -> serviceName.equals(service.get("short_name")))
                .findFirst()
                .orElse(null);
    }

    private int getIntValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Double) {
            return ((Double) value).intValue();
        }
        throw new IllegalArgumentException("Cannot convert value to int: " + value);
    }
}
