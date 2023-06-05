package com.einuvy.apigateway.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        //url = "${inmueble.service.url}",
        path = "api/inmueble",
        value = "INMUEBLE-SERVICE",
        configuration = FeingConfiguration.class)
public interface InmuebleServiceRequest {

    @PostMapping("")
    Object saveInmueble(@RequestBody Object object);

    @DeleteMapping("/{id}")
    Object deleteInmueble(@PathVariable Long id);

    @GetMapping("")
    List<Object> findAllInmuebles();

}
