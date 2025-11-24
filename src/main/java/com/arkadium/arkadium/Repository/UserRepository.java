package com.arkadium.arkadium.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arkadium.arkadium.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("select c from User c where lower(c.rut) = lower(:rut)")
    User FindByRut(String rut);

    @Query("select c from User c where lower(c.nombres) = lower(:nombres)")
    User FindByNombre(String nombres);
}
