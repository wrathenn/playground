package com.wrathenn.fieldhash.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/management/health")
class HealthController {
    @GetMapping
    fun healthCheck(): String {
        return "Healthy"
    }
}
