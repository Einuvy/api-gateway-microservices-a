package com.einuvy.apigateway.enums;

import lombok.Getter;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
