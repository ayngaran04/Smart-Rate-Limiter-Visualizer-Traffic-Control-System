package com.ayngaran.smart_rate_limiter.Controller;

import com.ayngaran.smart_rate_limiter.DTO.SimControllerDTO;
import com.ayngaran.smart_rate_limiter.DTO.SimControllerToFEDTO;
import com.ayngaran.smart_rate_limiter.Service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/sim")
public class SimController {

    private final RateLimiterService rateLimiterService;

    public SimController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/request")
    public ResponseEntity<SimControllerToFEDTO> simulateRequest(@RequestBody SimControllerDTO requestDTO) {
        boolean isAllowed = rateLimiterService.processRequest(requestDTO);
        SimControllerToFEDTO response = new SimControllerToFEDTO(
                requestDTO.getClientId(),
                requestDTO.getAlgorithm(),
                isAllowed
        );
        if (isAllowed) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(429).body(response);
        }
    }
}