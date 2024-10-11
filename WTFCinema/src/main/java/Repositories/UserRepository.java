package Repositories;

import Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    public Optional<AppUser> findByEmail(String email);
}
