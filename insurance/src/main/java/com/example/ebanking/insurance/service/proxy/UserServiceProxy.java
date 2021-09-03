package com.example.ebanking.insurance.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="netflix-zuul-api-gateway-server")
public interface UserServiceProxy {

    @GetMapping("/user/isInsuranceUsed/{id}")
    boolean isInsuranceUsed(@PathVariable("id") long id);

}
