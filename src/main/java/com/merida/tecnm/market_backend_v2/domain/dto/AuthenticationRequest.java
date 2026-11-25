package com.merida.tecnm.market_backend_v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AuthenticationRequest {

    @JsonAlias({"user", "usuario", "clientId", "clienteId", "email"})
    private String username;

    @JsonAlias({"clave", "contrasena"})
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
