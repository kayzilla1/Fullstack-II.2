package com.arkadium.arkadium.Services;

import java.util.Set;

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

        private static final Set<String> ADMIN_EMAILS = Set.of("ge.acunam@duocuc.cl", "ro.groom@duocuc.cl");

        public TokenResponse register(RegisterRequest request) {
             String assignedRole = ADMIN_EMAILS.contains(request.correo()) ? "ROLE_ADMIN" : "ROLE_USER";

             var existingUser = userRepository.findByCorreo(request.correo());
             final User savedUser;
             if (existingUser != null) {
                 // Si el usuario ya existe, actualizamos su rol (y opcionalmente la contraseña si viene)
                 existingUser.setRol(assignedRole);
                 if (request.password() != null && !request.password().isEmpty()) {
                     existingUser.setPassword(passwordEncoder.encode(request.password()));
                 }
                 // No sobrescribimos otros campos por si ya están establecidos
                 savedUser = userRepository.save(existingUser);
             } else {
                var user = User.builder()
                     .correo(request.correo())
                     .password(passwordEncoder.encode(request.password()))
                     .nombres(request.nombres())
                     .apellidos(request.apellidos())
                     .rut(request.rut())
                     .rol(assignedRole)
                     .build();
                savedUser = userRepository.save(user);
             }
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
