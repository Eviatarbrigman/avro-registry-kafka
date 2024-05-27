package com.eviatar.model;

public class AuthenticationResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
