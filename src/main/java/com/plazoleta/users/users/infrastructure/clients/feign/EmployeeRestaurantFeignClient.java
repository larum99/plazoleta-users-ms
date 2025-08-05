package com.plazoleta.users.users.infrastructure.clients.feign;

import com.plazoleta.users.users.infrastructure.clients.feign.dto.CreateEmployeeRestaurantRequest;
import com.plazoleta.users.users.infrastructure.utils.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = FeignConstants.NAME_SERVICE, url = FeignConstants.URL_SERVICE)
public interface EmployeeRestaurantFeignClient {

    @PostMapping(FeignConstants.CREATE_EMPLOYEE_PATH)
    void createEmployee(@RequestBody CreateEmployeeRestaurantRequest request);
}

