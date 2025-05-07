package com.Hyperfume.Backend.util;

public class GetIntValue {
    public static int getIntValue(Object value) {
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
