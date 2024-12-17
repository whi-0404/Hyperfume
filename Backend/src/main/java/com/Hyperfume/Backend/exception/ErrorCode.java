package com.Hyperfume.Backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001,"Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_EXISTED(1008, "Role not existed", HttpStatus.BAD_REQUEST),

    BRAND_EXISTED(1009, "Brand existed",HttpStatus.BAD_REQUEST),
    BRAND_NOT_EXISTED(1010, "Brand not existed", HttpStatus.BAD_REQUEST),

    SCRENT_FAMILY_EXISTED(1011, "Scrent_Family existed",HttpStatus.BAD_REQUEST),
    SCRENT_FAMILY_NOT_EXISTED(1012, "Scrent_Family not existed", HttpStatus.BAD_REQUEST),

    COUNTRY_EXISTED(1013, "Country existed",HttpStatus.BAD_REQUEST),
    COUNTRY_NOT_EXISTED(1014, "Country not existed", HttpStatus.BAD_REQUEST),

    OVER_SIZE_PERFUME_IMAGE(1015, "Perfume image must <=5",HttpStatus.BAD_REQUEST),
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

    PERFUME_ALREADY_IN_FAVORITES(1026,"Perfume already in favorites", HttpStatus.BAD_REQUEST),

    STAR_RATE_INVALID(1027,"rates star must be between at 0 - 5" ,HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(1028, "quantity invalid", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_STOCK_AVAILABLE(1029, "Not enough stock available", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(1030, "cart not found", HttpStatus.BAD_REQUEST),

    PAYMENT_METHOD_EXISTED(1031, "payment method existed", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NOT_EXISTED(1032, "payment method not existed", HttpStatus.BAD_REQUEST),

    SHIPPING_EXISTED(1033, "shipping method existed", HttpStatus.BAD_REQUEST),
    SHIPPING_NOT_EXISTED(1034,"shipping method not existed", HttpStatus.BAD_REQUEST),

    SHIPPING_ADDRESS_NOT_EXISTED(1035,"shipping address not existed", HttpStatus.BAD_REQUEST)
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode)
    {
        this.code=code;
        this.message=message;
        this.statusCode=statusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
