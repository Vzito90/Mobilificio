package com.example.demo;


import com.example.demo.IPInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IPInfoService {

    private final RestTemplate restTemplate;
    private final String accessToken = ""; // Inserisci il tuo token

    public IPInfoService() {
        this.restTemplate = new RestTemplate();
    }

    public IPInfoResponse getIPInfo(String ipAddress) {
        String url = String.format("https://ipinfo.io/%s?token=%s", ipAddress, accessToken);
        return restTemplate.getForObject(url, IPInfoResponse.class);
    }
}