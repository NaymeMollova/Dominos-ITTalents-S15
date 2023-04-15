package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.UserLoginDTO;
import ittalents.dominos.model.DTOs.UserRegisterDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;


import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractService {


  //  private BCryptPasswordEncoder encoder;
    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder encoder;



    public UserWithoutPassDTO register(UserRegisterDTO register) {
        if(!register.getFirstName().startsWith("^[A-Z].*")) { //regex for upper case
            throw new BadRequestException("First name must be starts with upper case");
        }
        if(!register.getLastName().startsWith("^[A-Z].*")){   //regex for upper case
            throw new BadRequestException("Last name must be starts with upper case");
        }

        if(!register.getPassword().equals(register.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
    }
        if(!register.getPassword().matches(register.getConfirmPassword())){
            throw new BadRequestException("Weak password!");
        }
        if(userRepository.existsByEmail(register.getEmail())){
            throw new BadRequestException("Email already exists!");
        }

       // User u = mapper.map(registerDTO, User.class);
      //  u.setPassword(encoder.encode(u.getPassword()));

        User u = mapper.map(register, User.class);
        u.setPassword(encoder.encode(u.getPassword()));

        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }
    public UserWithoutPassDTO login(UserLoginDTO login){
        Optional<User> u = userRepository.getByEmail(login.getEmail());
        if(!u.isPresent()){
            throw new UnauthorizedException("Wrong credentials");
        }
        if(!encoder.matches(login.getPassword(), u.get().getPassword())){
            throw new UnauthorizedException("Wrong credentials");
        }
        return mapper.map(u, UserWithoutPassDTO.class);
    }
}
