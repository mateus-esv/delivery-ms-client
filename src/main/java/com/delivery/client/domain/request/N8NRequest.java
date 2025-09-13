package com.delivery.client.domain.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "customer-n8n-request", url = "${url.send}")
public interface N8NRequest {

    @PostMapping("/webhook-test/d1307f87-789e-4df8-95a9-86f8f020b9ef")
    public ResponseEntity<?> sendN8N(MessageToN8N messageToN8N);
}
