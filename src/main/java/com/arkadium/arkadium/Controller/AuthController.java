package com.arkadium.arkadium.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.arkadium.arkadium.Controller.Request.LoginRequest;
import com.arkadium.arkadium.Controller.Request.RegisterRequest;

import lombok.RequiredArgsConstructor;
import com.arkadium.arkadium.Controller.Request.TokenResponse;
import com.arkadium.arkadium.services.AuthService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request){
        final TokenResponse token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request){
        final TokenResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
