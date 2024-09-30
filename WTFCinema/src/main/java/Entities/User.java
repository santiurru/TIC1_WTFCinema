package Entities;


import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email; //unique

    @NotNull
    private String name;

    //@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Booking> userBookings;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotNull
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
