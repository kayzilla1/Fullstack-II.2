package com.arkadium.arkadium.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arkadium.arkadium.Model.User;
import com.arkadium.arkadium.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public User findUserByRut(String rut){
        return userRepository.FindByRut(rut);
    }

    public User findUserByNombre(String nombres){
        return userRepository.FindByNombre(nombres);
    }

    public User findUserById(Integer id){
        return userRepository.findById(id).get();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
