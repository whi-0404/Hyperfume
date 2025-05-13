package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.VNPayCallBackDTO;
import com.Hyperfume.Backend.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface VNPayService {
    String createPaymentUrl(int orderId, HttpServletRequest request);
    boolean verifyPaymentCallback(Map<String, String> fields) throws UnsupportedEncodingException;
    boolean processPaymentCallback(Map<String, String> fields) throws UnsupportedEncodingException;
}
