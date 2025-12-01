package com.arkadium.arkadium.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arkadium.arkadium.Controller.Request.RegisterRequest;
import com.arkadium.arkadium.Model.User;
import com.arkadium.arkadium.Services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<User> getUserByRut(@PathVariable String rut) {
        User user = userService.findUserByRut(rut);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/nombre/{nombres}")
    public ResponseEntity<User> getUserByNombre(@PathVariable String nombres) {
        User user = userService.findUserByNombre(nombres);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody RegisterRequest request){ 
        User user = new User();
        user.setNombres(request.nombres());
        user.setApellidos(request.apellidos());
        user.setCorreo(request.correo());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRut(request.rut());
        user.setRol("USER");
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(Response.SC_CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails){
        try {
            User existingUser = userService.findUserById(id);
            existingUser.setRut(userDetails.getRut());
            existingUser.setNombres(userDetails.getNombres());
            existingUser.setApellidos(userDetails.getApellidos());
            existingUser.setCorreo(userDetails.getCorreo());
            User updatedUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}