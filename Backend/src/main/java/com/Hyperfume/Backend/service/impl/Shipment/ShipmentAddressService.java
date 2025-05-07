package com.Hyperfume.Backend.service.impl.Shipment;

import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import jakarta.annotation.ManagedBean;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentAddressService {

    @NonFinal
    @Value("${shipment.GHN.token}")
    protected String token;

    @NonFinal
    @Value("${shipment.GHN.api.province}")
    protected String getProvinceListApi;

    @NonFinal
    @Value("${shipment.GHN.api.district}")
    protected String getDistrictListApi;

    @NonFinal
    @Value("${shipment.GHN.api.ward}")
    protected String getWardListApi;


    RestTemplate restTemplate = new RestTemplate();

    public int getProvinceId(String provinceName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(getProvinceListApi,
                    HttpMethod.GET,
                    requestEntity,
                    Map.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseMap = response.getBody();

                if (responseMap != null && responseMap.containsKey("data")) {

                    List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");

                    for (Map<String, Object> map : data) {

                        Object provinceNameObj = map.get("ProvinceName");

                        if (provinceNameObj != null && provinceNameObj.equals(provinceName)) {
                            String provinceId = map.get("ProvinceID").toString();
                            return Integer.parseInt(provinceId);
                        }

                        Object extensionNamesObj = map.get("NameExtension");
                        if (extensionNamesObj instanceof List) {
                            List<String> extensionNames = (List<String>) extensionNamesObj;
                            if (extensionNames.contains(provinceName)) {
                                return convertToInt(map.get("ProvinceID"));
                            }
                        }
                    }

                    throw new AppException(ErrorCode.GHN_PROVINCE_NOT_FOUND);
                } else
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);

            } else {
                log.warn("Error response getting province ID: {}", response.getStatusCode());
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.GHN_FAILED_GET_PROVINCE_ID);
        }
    }

    public int getDistrictId(int provinceId, String districtName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Integer> body = new HashMap<>();
            body.put("province_id", provinceId);

            HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    getDistrictListApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseMap = response.getBody();
                if (responseMap != null && responseMap.containsKey("data")) {
                    List<Map<String, Object>> districts = (List<Map<String, Object>>) responseMap.get("data");

                    for (Map<String, Object> district : districts) {
                        // Kiểm tra tên trực tiếp
                        Object districtNameObj = district.get("DistrictName");
                        if (districtNameObj != null && districtName.equals(districtNameObj.toString())) {
                            return convertToInt(district.get("DistrictID"));
                        }

                        // Kiểm tra trong danh sách tên mở rộng
                        Object extensionNamesObj = district.get("NameExtension");
                        if (extensionNamesObj instanceof List) {
                            List<String> extensionNames = (List<String>) extensionNamesObj;
                            if (extensionNames.contains(districtName)) {
                                return convertToInt(district.get("DistrictID"));
                            }
                        }
                    }

                    throw new AppException(ErrorCode.GHN_DISTRICT_NOT_FOUND);
                } else {
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
                }
            } else {
                log.warn("Error response getting district ID: {}", response.getStatusCode());
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to get district ID for province {}, district name: {}", provinceId, districtName, e);
            throw new AppException(ErrorCode.GHN_FAILED_GET_DISTRICT_ID);
        }
    }

    public String getWardCode(int districtId, String wardName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Integer> body = new HashMap<>();
            body.put("district_id", districtId);

            HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    getWardListApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseMap = response.getBody();

                if (responseMap != null && responseMap.containsKey("data")) {

                    List<Map<String, Object>> data = (List<Map<String, Object>>) responseMap.get("data");
                    for (Map<String, Object> map : data) {
                        // Kiểm tra tên trực tiếp
                        if (map.get("WardName") != null && map.get("WardName").equals(wardName)) {
                            return (String) map.get("WardCode");
                        }

                        // Kiểm tra trong danh sách tên mở rộng
                        List<String> extensionNames = (List<String>) map.get("NameExtension");
                        if (extensionNames != null && extensionNames.contains(wardName)) {
                            return (String) map.get("WardCode");
                        }
                    }
                    throw new AppException(ErrorCode.GHN_WARD_NOT_FOUND);
                } else {
                    throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
                }
            } else {
                log.warn("Error response getting ward CODE: {}", response.getStatusCode());
                throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.GHN_FAIlED_GET_WARD_CODE);
        }
    }

    private int convertToInt(Object value) {
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
