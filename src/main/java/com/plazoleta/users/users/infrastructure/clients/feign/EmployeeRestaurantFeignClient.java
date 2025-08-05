package com.plazoleta.users.users.infrastructure.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "plazoleta-service", url = "${microservices.plazoleta.url}")
public interface EmployeeRestaurantFeignClient {

    @PostMapping("/api/v1/employee-restaurant")
    void createEmployee(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("restaurantId") Long restaurantId
    );
}

