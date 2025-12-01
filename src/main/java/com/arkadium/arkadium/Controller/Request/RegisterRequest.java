package com.arkadium.arkadium.Controller.Request;

public record RegisterRequest(
    String nombres,
    String apellidos,
    String correo,
    String password,
    String rut
) {

}
