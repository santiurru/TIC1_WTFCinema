package Services;

import Entities.WebUser;
import Exceptions.ExistingEntity;
import Exceptions.InvalidDataException;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebUserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebUser registerUser(WebUser user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public Optional<WebUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public WebUser addUser(String name,String surname,String nationalId,String password, String email, Date birthDate,Integer phoneNumber)
            throws InvalidDataException, ExistingEntity {
        if(name== null|| surname== null || phoneNumber == null || password == null || email == null || birthDate == null)
        {
            //todo verificar que el mail, telefono y cedula sean correctos
//       "LOS DATOS DEL NOMBRE, O APELLIDO O FECHA DE NACIMIENTO NO SON CORRECTOS"
            throw new InvalidDataException();
        }
        if(name.trim().equals("") || surname.trim().equals("") || nationalId.trim().equals("")
                || password.trim().equals("") || email.trim().equals(""))
        {
//            "Alguno de los datos de nombre o apellido debe ser completado"
            throw new InvalidDataException();
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ExistingEntity();
        }

        WebUser webUser = WebUser.builder()
                .name(name)
                .surname(surname)
                .password(password)
                .email(email)
                .birthDate(birthDate)
                .phoneNumber(phoneNumber)
                .build();
        return userRepository.save(webUser);
    }

    public List<WebUser> allUsers(){
        return userRepository.findAll();
    }

    public Optional<WebUser> findById(Long id) {
        return userRepository.findById(id);
    }

    private boolean isValidEmail(String email) {
        //what is love
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        //baby don't hurt me
        return email.matches(emailRegex);
        //don't hurt me
    }

}
