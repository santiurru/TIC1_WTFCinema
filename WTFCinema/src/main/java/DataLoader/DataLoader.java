package DataLoader;

import Entities.Admin;
import Repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner loadInitialData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (adminRepository.count() == 0) {
                Admin admin0 = new Admin();
                admin0.setEmail("admin@example.com");
                admin0.setPassword(passwordEncoder.encode("admin"));
                adminRepository.save(admin0);
            }
        };
    }
}

