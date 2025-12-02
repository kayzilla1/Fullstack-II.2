package com.arkadium.arkadium.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arkadium.arkadium.Controller.Request.LoginRequest;
import com.arkadium.arkadium.Controller.Request.RegisterRequest;
import com.arkadium.arkadium.Controller.Request.TokenResponse;
import com.arkadium.arkadium.Services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Operaciones de autenticación y registro")
public class AuthController {

    private final AuthService authService;

    @Operation  (summary = "Iniciar sesión", description = "Autentica a un usuario y devuelve un token JWT")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation  (summary = "Registrar usuario", description = "Registra un nuevo usuario y devuelve un token JWT")
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
