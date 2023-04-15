package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.UserRegisterDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {


  //  private BCryptPasswordEncoder encoder;
    private UserRepository userRepository;
    private ModelMapper mapper;

    public UserWithoutPassDTO register(UserRegisterDTO registerDTO) {
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
        }
//        if(!dto.getPassword().matches()){
//            throw new BadRequestException("Weak password!");
//        }
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        User u = mapper.map(registerDTO, User.class);
      //  u.setPassword(encoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPassDTO.class);
    }
}
