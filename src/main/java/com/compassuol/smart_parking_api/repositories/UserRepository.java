package com.compassuol.smart_parking_api.repositories;

import com.compassuol.smart_parking_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
