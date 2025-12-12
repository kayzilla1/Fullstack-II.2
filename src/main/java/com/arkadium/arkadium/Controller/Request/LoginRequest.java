package com.arkadium.arkadium.Controller.Request;

public record LoginRequest(
    String correo,
    String password,
    String rol
) {

}
