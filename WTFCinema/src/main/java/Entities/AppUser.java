package Entities;


import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Getter //@Data
@Setter
@Entity
//@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String nationalId;

    @NotNull
    @Temporal(TemporalType.DATE)
    protected Date birthDate;

    @Column(unique = true, nullable = false)
    protected String email; //unique

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
