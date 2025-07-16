package org.jobtracker.app.UserService.repository;

import org.jobtracker.app.UserService.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByUsername(String username);
}
