package Services;

import Entities.AppUser;
import Exceptions.InvalidDataException;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public AppUser getByEmail(String email){
        Optional<AppUser> result = userRepository.findByEmail(email);
        if(result.isPresent())
            return result.get();
        return null;
    }

    public AppUser addUser(String password, String email, Date birthDate) throws InvalidDataException {
        if(password == null || email == null || birthDate == null)
        {
//            "lOS DATOS DEL NOMBRE, O APELLIDO O FECHA DE NACIMIENTO NO SON CORRECTOS"
            throw new InvalidDataException();
        }
        if(password.trim().equals("") || email.trim().equals(""))
        {
//            "Alguno de los datos de nombre o apellido debe ser completado"
            throw new InvalidDataException();
        }

        AppUser aAppUser = AppUser.builder()
                .password(password)
                .email(email)
                .birthDate(birthDate)
                .build();
        return userRepository.save(aAppUser);
    }

    public List<AppUser> allUsers(){return userRepository.findAll();}

    public Optional<AppUser> findById(Long id) {return userRepository.findById(id);}
}
