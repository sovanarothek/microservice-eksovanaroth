package com.master.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

@RestController
class FallbackController {
    @GetMapping("/fallback/user")
    public Map<String, Object> userFallback() {
        return Map.of(
            "status", "error",
            "message", "User service is unavailable",
            "data", null
        );
    }

    @GetMapping("/fallback/order")
    public Map<String, Object> orderFallback() {
        return Map.of(
            "status", "error",
            "message", "Order service is unavailable",
            "data", null
        );
    }
}
