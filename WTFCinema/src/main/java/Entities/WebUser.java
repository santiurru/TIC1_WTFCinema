package Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Getter //@Data
@Setter
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private long id;

    @NotBlank(message = "error")
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String nationalId;

    @NotNull
    @Temporal(TemporalType.DATE)
    protected Date birthDate;

    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @NotNull
    private long phoneNumber;



    //constructor

   //public User(String password, String email, Date birthDate) {
   //    this.password = password;
   //    this.email = email;
   //    this.birthDate = birthDate;
   //    this.userBookings = new ArrayList<>();
   //}
   // public User(String password, String email, Date birthDate, String userType) {
   //     this.password = password;
   //     this.email = email;
   //     this.birthDate = birthDate;
   //     this.userBookings = new ArrayList<>();
   //     this.userType = userType;
   // }


    //@Override
    //public String toString() {
    //    return "User{" +
    //            "id=" + id +
    //            ", password='" + password + '\'' +
    //            ", email='" + email + '\'' +
    //            ", userBookings=" + userBookings +
    //            ", birthDate=" + birthDate +
    //            '}';
    //}

}
