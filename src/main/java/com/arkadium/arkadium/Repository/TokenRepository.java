package com.arkadium.arkadium.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.arkadium.arkadium.Model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    
}
