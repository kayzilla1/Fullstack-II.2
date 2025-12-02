package com.arkadium.arkadium.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.arkadium.arkadium.Controller.Request.RegisterRequest;
import com.arkadium.arkadium.Model.User;
import com.arkadium.arkadium.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operaciones CRUD para usuarios")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation (summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation (summary = "Obtener un usuario por RUT", description = "Devuelve un usuario basado en su RUT")
    @GetMapping("/rut/{rut}")
    public ResponseEntity<User> getUserByRut(@PathVariable String rut) {
        User user = userService.findUserByRut(rut);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation (summary = "Obtener un usuario por nombres", description = "Devuelve un usuario basado en sus nombres")
    @GetMapping("/nombre/{nombres}")
    public ResponseEntity<User> getUserByNombre(@PathVariable String nombres) {
        User user = userService.findUserByNombre(nombres);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation (summary = "Obtener un usuario por ID", description = "Devuelve un usuario basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation (summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados")
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

    @Operation (summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
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

    @Operation (summary = "Cambiar rol de un usuario", description = "Cambia el rol de un usuario existente")
    @PutMapping("/{id}/rol")
    public ResponseEntity<User> cambiarRolUsuario(@PathVariable Integer id, @RequestBody String nuevoRol){
        try {
            User existingUser = userService.findUserById(id);
            existingUser.setRol(nuevoRol);
            User updatedUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation (summary = "Eliminar un usuario", description = "Elimina un usuario existente por su ID")
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