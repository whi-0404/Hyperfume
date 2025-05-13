package com.Hyperfume.Backend.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseAddress {
    public static Map<String, String> parseAddress(String address) {
        Map<String, String> addressComponents = new HashMap<>();
        addressComponents.put("detail", "");
        addressComponents.put("ward", "");
        addressComponents.put("district", "");
        addressComponents.put("province", "");

        String[] parts = address.split(",");
        List<String> cleanedParts = new ArrayList<>();
        for (String part : parts) {
            cleanedParts.add(part.trim());
        }

        // Ưu tiên lấy tỉnh từ phần cuối
        for (int i = cleanedParts.size() - 1; i >= 0; i--) {
            String part = cleanedParts.get(i);
            if (addressComponents.get("province").isEmpty() &&
                    part.matches("(?i).*(Tỉnh|Thành phố|TP).*")) {
                addressComponents.put("province", part);
                cleanedParts.remove(i);
                break;
            }
        }

        // Tìm quận/huyện/thành phố cấp huyện
        for (int i = cleanedParts.size() - 1; i >= 0; i--) {
            String part = cleanedParts.get(i);
            if (addressComponents.get("district").isEmpty() &&
                    part.matches("(?i).*(Quận|Huyện|Thị xã|Thành phố|TP).*")) {
                addressComponents.put("district", part);
                cleanedParts.remove(i);
                break;
            }
        }

        // Tìm phường/xã/thị trấn
        for (int i = 0; i < cleanedParts.size(); i++) {
            String part = cleanedParts.get(i);
            if (addressComponents.get("ward").isEmpty() &&
                    part.matches("(?i).*(Phường|Xã|Thị trấn).*")) {
                addressComponents.put("ward", part);
                cleanedParts.remove(i);
                break;
            }
        }

        // Những phần còn lại là detail
        String detail = String.join(", ", cleanedParts);
        addressComponents.put("detail", detail);

        return addressComponents;
    }

}