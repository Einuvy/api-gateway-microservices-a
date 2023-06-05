package com.einuvy.apigateway.controllers;

import com.einuvy.apigateway.request.InmuebleServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/gateway/inmueble")
public class InmuebleController {

    @Autowired
    private InmuebleServiceRequest inmuebleServiceRequest;

    @PostMapping("")
    public  ResponseEntity<?> saveInmueble(@RequestBody Object object){
        return new ResponseEntity<>(inmuebleServiceRequest.saveInmueble(object), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInmueble(@PathVariable Long id){
        inmuebleServiceRequest.deleteInmueble(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllInmuebles(){
        return new ResponseEntity<>( inmuebleServiceRequest.findAllInmuebles() ,OK);
    }

}
