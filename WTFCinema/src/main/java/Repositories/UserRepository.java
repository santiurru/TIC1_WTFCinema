package Repositories;

import Entities.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<WebUser, Long> {

    public Optional<WebUser> findByEmail(String email);
}
