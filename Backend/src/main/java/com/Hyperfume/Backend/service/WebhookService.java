package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.GHNWebhookDTO;

public interface WebhookService {
    void processGHNWebhook(GHNWebhookDTO webhookData);
}
