package Entities;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//ESTA CLASE IMPLEMENTA SERIALIZABLE PARA CONFIGURARLO CON LAS ENTIDADES EN LA BASE DE DATOS
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;


    private String password;
    private String email; //unique
    private List<Booking> userBookings;
    private Date birthDate;
    private String userType;


    //constructor

    public User(String password, String email, Date birthDate) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.userBookings = new ArrayList<>();
        this.userType = "USER";
    }
    public User(String password, String email, Date birthDate, String userType) {
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.userBookings = new ArrayList<>();
        this.userType = userType;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userBookings=" + userBookings +
                ", birthDate=" + birthDate +
                '}';
    }
}
