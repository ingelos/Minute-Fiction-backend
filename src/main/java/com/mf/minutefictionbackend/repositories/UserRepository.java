package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
