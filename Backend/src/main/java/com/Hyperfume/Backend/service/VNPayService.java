package com.Hyperfume.Backend.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createPaymentUrl(int orderId, HttpServletRequest request);

    boolean verifyPaymentCallback(Map<String, String> fields) throws UnsupportedEncodingException;

    boolean processPaymentCallback(Map<String, String> fields) throws UnsupportedEncodingException;
}
