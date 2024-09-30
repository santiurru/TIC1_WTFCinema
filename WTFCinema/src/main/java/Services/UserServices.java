package Services;

import Entities.User;
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

    public User getByEmail(String email){
        Optional<User> result = userRepository.findByEmail(email);
        if(result.isPresent())
            return result.get();
        return null;
    }

    public User addUser(String password, String email, Date birthDate) throws InvalidDataException {
        if(password == null || email == null || birthDate == null)
        {
            throw new InvalidDataException("lOS DATOS DEL NOMBRE, O APELLIDO O FECHA DE NACIMIENTO NO SON CORRECTOS");
        }
        if(password.trim().equals("") || email.trim().equals(""))
        {
            throw new InvalidDataException("Alguno de los datos de nombre o apellido debe ser completado");
        }

        User aUser = User.builder()
                .firstName(password)
                .lastName(email)
                .birthDate(birthDate)
                .build();
        return userRepository.save(aUser);
    }

    public List<User> allUsers(){return userRepository.findAll();}

    public Optional<User> findById(Long id) {return userRepository.findById(id);}
}
