package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Integer> {
    public boolean existsByUsername(String username);
    public Account getByUsernameAndPassword(String username, String password);
}
