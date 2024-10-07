package Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends User{


    public Admin(String password, String email, Date birthDate) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }
}
