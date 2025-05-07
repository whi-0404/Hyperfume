package com.Hyperfume.Backend.service.impl.Shipment;

import com.Hyperfume.Backend.dto.response.ShipmentResponse;
import com.Hyperfume.Backend.entity.Order;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.ShippingAddress;
import com.Hyperfume.Backend.enums.OrderStatus;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.OrderRepository;
import com.Hyperfume.Backend.repository.ShipmentRepository;
import com.Hyperfume.Backend.repository.ShipmentTrackingRepository;
import com.Hyperfume.Backend.repository.ShippingAddressRepository;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.service.redis.ShipmentRedisService;
import com.Hyperfume.Backend.util.ParseAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Hyperfume.Backend.util.GetIntValue.getIntValue;

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


    ShipmentRepository shipmentRepository;
    ShipmentRedisService shipmentRedisService;
    ShipmentAddressService shipmentAddressService;
    ShippingAddressRepository shippingAddressRepository;
    ShipmentTrackingRepository shipmentTrackingRepository;
    ShipmentTrackingServiceImpl shipmentTrackingService;
    OrderRepository orderRepository;
    RestTemplate restTemplate = new RestTemplate();

    public ShipmentResponse getShipmentOrderInfo(int shippingAddressId, int quantity) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() ->new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED));


        Map<String, String> toAddress = ParseAddress.parseAddress(shippingAddress.getShipAddress());
        Map<String, String> fromAddress = ParseAddress.parseAddress(fromAddressKey);

        int toProvinceId = shipmentAddressService.getProvinceId(toAddress.get("province"));
        int toDistrictId = shipmentAddressService.getDistrictId(toProvinceId, toAddress.get("district"));
        String toWardCode = shipmentAddressService.getWardCode(toDistrictId, toAddress.get("ward"));

        int fromProvinceId = shipmentAddressService.getProvinceId(fromAddress.get("province"));
        int fromDistrictId = shipmentAddressService.getDistrictId(fromProvinceId, fromAddress.get("district"));
        String fromWardCode = shipmentAddressService.getWardCode(fromDistrictId, fromAddress.get("ward"));

        int weight = quantity * 350;

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

        ShipmentResponse response =  ShipmentResponse.builder()
                .serviceId(serviceId)
                .serviceName(serviceName)
                .fee(fee)
                .shippingAddress(shippingAddress.getShipAddress())
                .shippingAddressId(shippingAddressId)
                .expectedDeliveryDate(expectedDeliveryDate)
                .build();

        String shipmentToken = shipmentRedisService.cacheShipmentInfo(response);

        response.setShipmentToken(shipmentToken);

        return response;
    }

    public Shipment createShipment(Order order, String shipmentToken) {
        if(order.getShipment() != null){
            return order.getShipment();
        }

        ShipmentResponse response = shipmentRedisService.getShipmentInfo(shipmentToken);

        if(response == null){
            throw new AppException(ErrorCode.REDIS_SHIPMENT_INFO_NOT_FOUND);
        }

        ShippingAddress shippingAddress = shippingAddressRepository.findById(response.getShippingAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED));

        Shipment shipment = Shipment.builder()
                .serviceId(response.getServiceId())
                .order(order)
                .shippingAddress(shippingAddress)
                .currentLocation("Shop Hyperfume")
                .expectedDeliveryDate(response.getExpectedDeliveryDate())
                .name(response.getServiceName())
                .fee(response.getFee())
                .build();

        //save shipment to db
        shipment = shipmentRepository.save(shipment);

        //save order
        order.setShipment(shipment);
        orderRepository.save(order);

        //create and save shipment tracking to db
        shipmentTrackingService.createShipmentTracking(shipment);

        //remove cache from redis
        shipmentRedisService.deleteShipmentInfo(shipmentToken);

        return shipment;
    }


    @Transactional
    public Shipment updateShipmentStatus(Shipment shipment, ShipmentStatus status, String location, String description){

        shipment.setStatus(status);
        if(location!=null && !location.isBlank()){
            shipment.setCurrentLocation(location);
        }

        shipmentTrackingService.createShipmentTracking(shipment);

        updateOrderStatusByShipment(shipment);

        //Send Status notification
        //...


        return shipmentRepository.save(shipment);
    }



    private int getFee(Integer serviceId, int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int weight) {
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
            throw new AppException(ErrorCode.GHN_FAILED_GET_SHIPPING_FEE);
        }
    }

    private LocalDate getExpectedDeliveryDate(Integer serviceId, int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int weight) {
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
            throw new AppException(ErrorCode.GHN_FAILED_GET_EXPECTED_DELIVERY_DATE);
        }
    }


    private List<Map<String, Object>> getServiceList(int fromDistrictId, int toDistrictId) {
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
            throw new AppException(ErrorCode.GHN_FAILED_GET_SERVICE_LIST);
        }
    }

    private void updateOrderStatusByShipment(Shipment shipment) {
        Order order = shipment.getOrder();

        if(order == null){
            return;
        }

        OrderStatus newStatus = mapShipmentStatusToOrderStatus(shipment.getStatus());

        if(!order.getStatus().equals(newStatus)){
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    private OrderStatus mapShipmentStatusToOrderStatus(ShipmentStatus shipmentStatus) {
        switch (shipmentStatus) {
            case READY_TO_PICK, PICKING -> {
                return OrderStatus.PROCESSING;
            }
            case PICKED, STORING, TRANSPORTING, SORTING -> {
                return OrderStatus.IN_TRANSIT;
            }

            case DELIVERING,MONEY_COLLECT_DELIVERING -> {
                return OrderStatus.DELIVERING;
            }
            case DELIVERED -> {
                return OrderStatus.DELIVERED;
            }

            case RETURN, RETURN_TRANSPORTING, RETURNING -> {
                return OrderStatus.RETURNING;
            }

            case RETURNED -> {
                return OrderStatus.RETURNED;
            }

            default -> {
                return null;
            }
        }
    }

    private Map<String, Object> findServiceByName(List<Map<String, Object>> serviceList, String serviceName) {
        return serviceList.stream()
                .filter(service -> serviceName.equals(service.get("short_name")))
                .findFirst()
                .orElse(null);
    }
}
