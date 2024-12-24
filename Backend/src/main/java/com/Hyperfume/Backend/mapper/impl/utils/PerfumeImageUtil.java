package com.Hyperfume.Backend.mapper.impl.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class PerfumeImageUtil {
    public String encodeImageData(byte[] imageData) {
        return imageData != null ? Base64.getEncoder().encodeToString(imageData) : null;
    }
}
