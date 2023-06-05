package com.einuvy.apigateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
       // url = "${buy.service.url}",
        path = "api/buy",
        value = "BUY-SERVICE",
        configuration = FeingConfiguration.class)
public interface BuyServiceRequest {

    @PostMapping("")
    Object saveBuy(@RequestBody Object buyDTO);

    @GetMapping("/{id}")
    List<Object> findAllBuys(@PathVariable Long id);
}
