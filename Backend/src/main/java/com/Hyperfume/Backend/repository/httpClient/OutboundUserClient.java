package com.Hyperfume.Backend.repository.httpClient;

import com.Hyperfume.Backend.dto.request.ExchangeTokenRequest;
import com.Hyperfume.Backend.dto.response.ExchangeTokenResponse;
import com.Hyperfume.Backend.dto.response.OutboundUserResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-user-client", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OutboundUserResponse getUserInfo(@RequestParam("alt") String alt,
                                     @RequestParam("access_token") String accessToken);
}
