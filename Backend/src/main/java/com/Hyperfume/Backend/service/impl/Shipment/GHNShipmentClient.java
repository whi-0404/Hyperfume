package com.Hyperfume.Backend.service.impl.Shipment;

import com.Hyperfume.Backend.dto.GHNCreateOrderRequest;
import com.Hyperfume.Backend.dto.GHNWebhookDTO;
import com.Hyperfume.Backend.entity.OrderItem;
import com.Hyperfume.Backend.entity.Shipment;
import com.Hyperfume.Backend.entity.ShippingAddress;
import com.Hyperfume.Backend.enums.OrderStatus;
import com.Hyperfume.Backend.enums.ShipmentStatus;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.OrderItemRepository;
import com.Hyperfume.Backend.repository.OrderRepository;
import com.Hyperfume.Backend.repository.ShipmentRepository;
import com.Hyperfume.Backend.repository.ShippingAddressRepository;
import com.Hyperfume.Backend.service.OrderService;
import com.Hyperfume.Backend.service.ShipmentService;
import com.Hyperfume.Backend.service.ShipmentTrackingService;
import com.Hyperfume.Backend.util.ParseAddress;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Hyperfume.Backend.util.GetIntValue.getIntValue;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GHNShipmentClient {
    private final ShipmentService shipmentService;
    @NonFinal
    @Value("${shipment.GHN.token}")
    protected String token;

    @NonFinal
    @Value("${shipment.GHN.shop-id}")
    protected int shopId;

    @NonFinal
    @Value("${shipment.GHN.api.create-order}")
    protected String createOrderApi;

    @NonFinal
    @Value("${shipment.GHN.api.cancel-order}")
    protected String cancelOrderApi;

    @NonFinal
    @Value("${shipment.GHN.api.tracking}")
    protected String trackingApi;

    @NonFinal
    @Value("${shipment.GHN.api.return-order}")
    protected String returnOrderApi;

    @NonFinal
    @Value("${shipment.GHN.from-address}")
    protected String fromAddressKey;

    ShippingAddressRepository ShippingAddressRepository;
    ShipmentTrackingService shipmentTrackingService;
    ShipmentRepository shipmentRepository;
    RestTemplate restTemplate = new RestTemplate();
    OrderItemRepository orderItemRepository;
    OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    public String createOrder(GHNCreateOrderRequest request) {
        try {
            ShippingAddress shippingAddress = ShippingAddressRepository.findById(request.getShippingAddressId())
                    .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_ADDRESS_NOT_EXISTED));

            List<OrderItem> orderItems = orderItemRepository.findByOrderId(request.getOrderId());

            Map<String, String> toAddress = ParseAddress.parseAddress(shippingAddress.getShipAddress());


            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.set("ShopId", String.valueOf(shopId));
            headers.setContentType(MediaType.APPLICATION_JSON);

            //from Address Info
            Map<String, String> fromAddress = ParseAddress.parseAddress(fromAddressKey);

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("from_name", "Hyperfume");
            requestBody.put("from_phone", "0962222222");
            requestBody.put("from_address", fromAddress.get("detail"));
            requestBody.put("from_ward_name", fromAddress.get("ward"));
            requestBody.put("from_district_name", fromAddress.get("district"));
            requestBody.put("from_province_name", fromAddress.get("province"));
            requestBody.put("to_name", shippingAddress.getRecipientName());
            requestBody.put("to_phone", shippingAddress.getPhone());
            requestBody.put("to_address", toAddress.get("detail"));
            requestBody.put("to_ward_name", toAddress.get("ward"));
            requestBody.put("to_district_name", toAddress.get("district"));
            requestBody.put("to_province_name", toAddress.get("province"));
//            requestBody.put("to_ward_name", "Bến Thành");
//            requestBody.put("to_district_name", "Quận 1");
//            requestBody.put("to_province_name","TP Hồ Chí Minh");
            requestBody.put("service_type_id", 2);
            requestBody.put("length", request.getLength());
            requestBody.put("width", request.getWidth());
            requestBody.put("height", request.getHeight());
            requestBody.put("weight", request.getWeight());
            requestBody.put("payment_type_id", request.getPaymentTypeId() != null ? request.getPaymentTypeId() : 1);
            requestBody.put("required_note", request.getRequiredNote() != null ? request.getRequiredNote() : "CHOXEMHANGKHONGTHU");


            List<Map<String, Object>> items = new java.util.ArrayList<>();

            log.info("Order items: {}", orderItems);

            for(OrderItem item : orderItems) {
                Map<String, Object> itemMap = new HashMap<>();

                itemMap.put("name", item.getPerfumeVariant().getName());
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("price",  item.getPerfumeVariant().getPrice().intValue());
                itemMap.put("code", String.valueOf(item.getPerfumeVariant().getId()));
                itemMap.put("length", 10);
                itemMap.put("width", 10);
                itemMap.put("height", 10);
                itemMap.put("weight", 350);

                items.add(itemMap);
            }

            requestBody.put("items", items);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    createOrderApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

                    Shipment shipment = shipmentRepository.findByOrderId(request.getOrderId())
                            .orElseThrow(()-> new AppException(ErrorCode.SHIPMENT_NOT_EXISTED));

                    shipment.setShippingCode((String) data.get("order_code"));
                    shipment.setFee(getIntValue(data.get("total_fee")));

                    String timestamp = (String)(data.get("expected_delivery_time"));

                    LocalDate expectedDate = Instant.parse(timestamp)
                            .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                            .toLocalDate();

                    shipment.setExpectedDeliveryDate(expectedDate);

                    shipment.setStatus(ShipmentStatus.READY_TO_PICK);

                    shipmentTrackingService.createShipmentTracking(shipment);

                    shipmentRepository.save(shipment);

                    orderService.updateOrderStatus(request.getOrderId(), OrderStatus.PROCESSING);

                    return (String) data.get("order_code");
                } else throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
            } else throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error when create order: {}", e.getMessage());
            throw new AppException(ErrorCode.GHN_FAILED_CREATE_ORDER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String cancelOrder(String orderCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.set("ShopId", String.valueOf(shopId));
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("order_codes", new String[]{orderCode});

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    cancelOrderApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("code")) {
                    return responseBody.get("code").equals(200) ? "Success" : "Failed";
                }
            }
            return "Failed";
        } catch (Exception e) {
            throw new AppException(ErrorCode.GHN_FAILED_CANCEL_ORDER);
        }
    }

    public GHNWebhookDTO trackOrder(String orderCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("order_code", orderCode);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    trackingApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

                    GHNWebhookDTO trackingResponse = GHNWebhookDTO.builder()
                            .orderCode(orderCode)
                            .status(data.get("Status") != null ? data.get("Status").toString() : null)
                            .description(data.get("Description") != null ? data.get("Description").toString() : null)
                            .wareHouse(data.get("Warehouse") != null ? data.get("Warehouse").toString() : null)
                            .time(data.get("Time") != null ?
                                    LocalDateTime.ofInstant(Instant.parse(data.get("Time").toString()), ZoneId.of("Asia/Ho_Chi_Minh"))
                                    : null)
                            .build();

                    return trackingResponse;
                }
                else throw new AppException(ErrorCode.GHN_NO_DATA_IN_RESPONSE);
            }
            else throw new AppException(ErrorCode.GHN_RETURNED_ERROR);
        }
        catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            throw new AppException(ErrorCode.GHN_FAILED_GET_ORDER_STATUS);
        }
    }

    public boolean returnOrder(String orderCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            headers.set("ShopId", String.valueOf(shopId));
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("order_codes", new String[]{orderCode});

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    returnOrderApi,
                    requestEntity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("code")) {
                    return responseBody.get("code").equals(200);
                }
            }
            return false;
        }
        catch (Exception e)
        {
            throw new AppException(ErrorCode.GHN_FAILED_RETURN_ORDER);
        }
    }
}
