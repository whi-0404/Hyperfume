package com.Hyperfume.Backend.controller;

import com.Hyperfume.Backend.dto.GHNWebhookDTO;
import com.Hyperfume.Backend.dto.response.ApiResponse;
import com.Hyperfume.Backend.service.WebhookService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookController {

    WebhookService webhookService;

    @PostMapping("/ghn/status")
    public ApiResponse<GHNWebhookDTO> callBackStatusOrder(@RequestBody GHNWebhookDTO ghnWebhookDTO) {

        return ApiResponse.<GHNWebhookDTO> builder()
                .result(ghnWebhookDTO)
                .build();
    }
}
