package com.arkadium.arkadium.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arkadium.arkadium.Controller.Request.LoginRequest;
import com.arkadium.arkadium.Controller.Request.RegisterRequest;
import com.arkadium.arkadium.Controller.Request.TokenResponse;
import com.arkadium.arkadium.Model.Token;
import com.arkadium.arkadium.Model.User;
import com.arkadium.arkadium.Repository.TokenRepository;
import com.arkadium.arkadium.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Registro de usuario con token JWT
     public TokenResponse register(RegisterRequest request) {
          var user = User.builder()
               .correo(request.correo())
               .password(passwordEncoder.encode(request.password()))
               .nombres(request.nombres())
               .apellidos(request.apellidos())
               .rut(request.rut())
               .rol("USER")
               .build();
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);
            return new TokenResponse(jwtToken);
     }
     //Guardar token en la base de datos
     private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken).tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
     }
     //Login de usuario con token JWT (Se verfican las credenciales en este caso correo y password)
     public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.correo(),
                request.password()
                )
        );
        var user = userRepository.findByCorreo(request.correo());
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken);
    }
}
