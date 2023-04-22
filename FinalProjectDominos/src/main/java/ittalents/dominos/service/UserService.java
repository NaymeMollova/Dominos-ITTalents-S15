package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {

    @Autowired
    private BCryptPasswordEncoder encoder;


    public UserWithoutPassDTO register(UserRegisterDTO register) {
        if(userRepository.existsByEmail(register.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        if(!register.getPassword().equals(register.getConfirmPassword())) {
            throw new BadRequestException("Passwords mismatch!");
        }

        User u = mapper.map(register, User.class);
        u.setPassword(encoder.encode(u.getPassword()));
    //    u.setAdmin(true);
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
    public UserWithoutPassDTO edit(int id, UserEditDTO dto){
        User user = getUserById(id);
                user.setFirstName(dto.getFirstName());
                user.setLastName(dto.getLastName());
                user.setPhoneNumber(dto.getPhoneNumber());
                user.setEmail(dto.getEmail());
                userRepository.save(user);
                return mapper.map(user, UserWithoutPassDTO.class);
        }

        public UserWithoutPassDTO viewProfile(int id) {
            User user = getUserById(id);
                return mapper.map(user, UserWithoutPassDTO.class);
        }
        public UserWithoutPassDTO changePassword(int id, UserChangePasswordDTO dto) {
            User user = getUserById(id);
            user.setPassword(encoder.encode(dto.getNewPassword()));
            userRepository.save(user);
                return mapper.map(user, UserWithoutPassDTO.class);
        }
        public List<UserWithoutPassDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map( u -> mapper.map(u, UserWithoutPassDTO.class))
                .collect(Collectors.toList());
    }


    public User findLoggedUser(int userId) {
        return userRepository.getReferenceById(userId);
    }
}
