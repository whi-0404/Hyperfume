package com.Hyperfume.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_EXISTED(1008, "Role not existed", HttpStatus.BAD_REQUEST),

    BRAND_EXISTED(1009, "Brand existed", HttpStatus.BAD_REQUEST),
    BRAND_NOT_EXISTED(1010, "Brand not existed", HttpStatus.BAD_REQUEST),

    SCRENT_FAMILY_EXISTED(1011, "Scrent_Family existed", HttpStatus.BAD_REQUEST),
    SCRENT_FAMILY_NOT_EXISTED(1012, "Scrent_Family not existed", HttpStatus.BAD_REQUEST),

    COUNTRY_EXISTED(1013, "Country existed", HttpStatus.BAD_REQUEST),
    COUNTRY_NOT_EXISTED(1014, "Country not existed", HttpStatus.BAD_REQUEST),

    OVER_SIZE_PERFUME_IMAGE(1015, "Perfume image must <=5", HttpStatus.BAD_REQUEST),
    DUPLICATE_THUMBNAIL_IMAGE(1016, "Duplicate thumbnail perfume image", HttpStatus.BAD_REQUEST),
    PERFUME_NOT_EXISTED(1017, "Perfume not existed", HttpStatus.BAD_REQUEST),
    IMAGE_NOT_FOUND(1018, "Image not found", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_FILE(1019, "Image too large", HttpStatus.PAYLOAD_TOO_LARGE),
    FAILED_READ_IMAGE(1020, "Failed to read image file", HttpStatus.BAD_REQUEST),
    OUT_OF_PERFUME_IMAGE(1021, "Out of perfume image", HttpStatus.BAD_REQUEST),
    THUMBNAIL_NOT_FOUND(1022, "Thumbnail not found", HttpStatus.BAD_REQUEST),

    VARIANT_NOT_FOUND(1023, "Variant not found", HttpStatus.BAD_REQUEST),
    OUT_OF_STOCK(1024, "Out of stock perfume variant", HttpStatus.BAD_REQUEST),
    DISCOUNT_INVALID(1025, "Discount must be between at 0 - 100% ", HttpStatus.BAD_REQUEST),
    STOCK_INVALID(1026, "Stock invalid", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1025, "price invalid ", HttpStatus.BAD_REQUEST),

    PERFUME_ALREADY_IN_FAVORITES(1026, "Perfume already in favorites", HttpStatus.BAD_REQUEST),

    STAR_RATE_INVALID(1027, "rates star must be between at 0 - 5", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(1028, "quantity invalid", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_STOCK_AVAILABLE(1029, "Not enough stock available", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(1030, "cart not found", HttpStatus.BAD_REQUEST),

    PAYMENT_METHOD_EXISTED(1031, "payment method existed", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NOT_EXISTED(1032, "payment method not existed", HttpStatus.BAD_REQUEST),

    SHIPPING_EXISTED(1033, "shipping method existed", HttpStatus.BAD_REQUEST),
    SHIPPING_NOT_EXISTED(1034, "shipping method not existed", HttpStatus.BAD_REQUEST),

    SHIPPING_ADDRESS_NOT_EXISTED(1035, "shipping address not existed", HttpStatus.BAD_REQUEST),

    NO_FOUND_BY_SEARCH_NAME(1036, "don't find anything", HttpStatus.BAD_REQUEST),
    RATE_EXISTED(1037, "rate existed", HttpStatus.BAD_REQUEST),
    RATE_NOT_EXISTED(1038, "rate not existed", HttpStatus.BAD_REQUEST),

    TOKEN_NOT_FOUND_IN_COOKIES(1039, "token not found in cookie", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(1040, "token has expired", HttpStatus.UNAUTHORIZED),

    PERFUME_NOT_IN_FAVORITES(1041, "perfume not in favorites", HttpStatus.BAD_REQUEST),

    ACTIVE_FLASH_SALE_NOT_EXISTED(1042, "active flash sale not existed", HttpStatus.BAD_REQUEST),
    FLASH_SALE_NOT_EXISTED(1043, "flash sale not existed", HttpStatus.BAD_REQUEST),
    ACTIVE_FLASH_SALE_EXISTED(1044, "active flash sale has been existed", HttpStatus.BAD_REQUEST),
    PERFUME_ALREADY_IN_FLASH_SALE(1045, "perfume has been in flash sale", HttpStatus.BAD_REQUEST),
    FLASH_SALE_ITEM_NOT_EXISTED(1046, "flash sale item not existed", HttpStatus.BAD_REQUEST),
    FLASH_SALE_ITEM_NOT_IN_FLASH_SALE(1047, "flash sale item not in flash sale", HttpStatus.BAD_REQUEST),
    VARIANT_ALREADY_IN_CART(1048, "variant has been in cart", HttpStatus.BAD_REQUEST),

    GHN_RETURNED_ERROR(1049, "GHN shipping service returned error", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_SHIPPING_FEE(1050, "failed to get shipping fee api", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_EXPECTED_DELIVERY_DATE(1051, "failed to get expected delivery date api", HttpStatus.BAD_REQUEST),
    GHN_NO_DATA_IN_RESPONSE(1052, "data is null or empty in response of GHN service shipping", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_SERVICE_LIST(1053, "failed to get shipping service list api", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_PROVINCE_ID(1054, "failed to get province id api", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_DISTRICT_ID(1055, "failed to get district id api", HttpStatus.BAD_REQUEST),
    GHN_FAIlED_GET_WARD_CODE(1056, "failed to get ward code api", HttpStatus.BAD_REQUEST),
    GHN_DISTRICT_NOT_FOUND(1057, "not found district name consistent", HttpStatus.BAD_REQUEST),
    GHN_PROVINCE_NOT_FOUND(1057, "not found province name consistent", HttpStatus.BAD_REQUEST),
    GHN_WARD_NOT_FOUND(1057, "not found ward name consistent", HttpStatus.BAD_REQUEST),

    GHN_FAILED_CREATE_ORDER(1058, "failed to create order api GHN", HttpStatus.BAD_REQUEST),
    GHN_FAILED_CANCEL_ORDER(1059, "failed to cancel order api GHN", HttpStatus.BAD_REQUEST),
    GHN_FAILED_GET_ORDER_STATUS(1060, "failed to get order status api GHN", HttpStatus.BAD_REQUEST),
    GHN_FAILED_RETURN_ORDER(1061, "failed to return order api GHN", HttpStatus.BAD_REQUEST),

    ORDER_NOT_FOUND(1062, "order not found", HttpStatus.BAD_REQUEST),

    REDIS_SHIPMENT_INFO_NOT_FOUND(1063, "shipment info not found in redis", HttpStatus.BAD_REQUEST),
    SHIPMENT_TOKEN_INVALID(1064, "shipment token invalid", HttpStatus.BAD_REQUEST),
    SHIPMENT_CREATION_FAILED(1065, "shipment creation failed", HttpStatus.BAD_REQUEST),
    SHIPMENT_NOT_EXISTED(1066, "shipment not existed", HttpStatus.BAD_REQUEST),

    NOTIFICATION_NOT_EXISTED(1067, "notification not existed", HttpStatus.BAD_REQUEST),

    INVALID_ROLE_CHAT(1068, "Chat is only allowed between users and admin", HttpStatus.BAD_REQUEST),
    CHATROOM_NOT_EXISTED(1069, "Chatroom not existed", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(1070, "File upload failed", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1071, "File not found", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(1072, "File too large", HttpStatus.PAYLOAD_TOO_LARGE),
    INVALID_FILE_TYPE(1073, "Invalid file type", HttpStatus.BAD_REQUEST),
    DIRECTORY_CREATION_FAILED(1074, "Directory creation failed", HttpStatus.BAD_REQUEST),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
