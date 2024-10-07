package Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User{

    private List customerBookings;

    public Customer(String password, String email, Date birthDate) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.customerBookings = new ArrayList();
    }
}
