package com.einuvy.apigateway.controllers;

import com.einuvy.apigateway.configuration.UserPrincipal;
import com.einuvy.apigateway.request.BuyServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/gateway/buy")
public class BuyController {

    @Autowired
    BuyServiceRequest buyServiceRequest;

    @PostMapping("")
    public ResponseEntity<?> saveBuy(@RequestBody Object buyDTO){
        return new ResponseEntity<>(buyServiceRequest.saveBuy(buyDTO), CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBuysOfUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(buyServiceRequest.findAllBuys(userPrincipal.getId()), OK);
    }
}
