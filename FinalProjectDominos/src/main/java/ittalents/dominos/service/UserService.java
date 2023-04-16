package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.UserEditDTO;
import ittalents.dominos.model.DTOs.UserLoginDTO;
import ittalents.dominos.model.DTOs.UserRegisterDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
        u.setAdmin(true);
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
        //проверка за съществуване на user
        Optional<User> u = userRepository.findById(id);
            if(u.isPresent()){
                User user = u.get();
                user.setFirstName(dto.getFirstName());
                user.setLastName(dto.getLastName());
                user.setPhoneNumber(dto.getPhoneNumber());
                user.setEmail(dto.getEmail());


                userRepository.save(user);
                return mapper.map(user, UserWithoutPassDTO.class);
            }else {
                //не съществува потребител
                throw new NotFoundException("No such user exists");
            }
        }

        public UserWithoutPassDTO viewProfile(int id) {
            Optional<User> u = userRepository.findById(id);
            if(u.isPresent()){
                User user = u.get();
                return mapper.map(user, UserWithoutPassDTO.class);
            }else{
                //не съществува
                throw new NotFoundException("No such user exists");
            }
        }
        public UserWithoutPassDTO changePassword(int id) {
            Optional<User> u = userRepository.findById(id);
            if (u.isPresent()) {
                User user = u.get();
                return mapper.map(user, UserWithoutPassDTO.class);
            }else{
                throw new NotFoundException("No such user exists");
            }
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
