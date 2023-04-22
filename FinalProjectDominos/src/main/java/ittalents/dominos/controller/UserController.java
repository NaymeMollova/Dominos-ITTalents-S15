package ittalents.dominos.controller;
import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @GetMapping("/email")
//    public void sendMail(){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("naymeto25@gmail.com");
//        message.setTo("naimeto@abv.bg");
//        message.setText("pisna mi");
//
//        javaMailSender.send(message);
//        System.out.println("done");
//    }


    @PostMapping("/dominos/users")
    @Transactional
    public UserWithoutPassDTO register(@Valid @RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/dominos/users/login")
    @Transactional
    public UserWithoutPassDTO login(@RequestBody UserLoginDTO dto, HttpSession s) {
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @PostMapping("/dominos/users/logout")
    @Transactional
    public void logout(HttpSession s) {
        getLoggedId(s);
            s.invalidate();
    }
    @PutMapping("/dominos/users/profile")
    @Transactional
    public UserWithoutPassDTO edit(@Valid @RequestBody UserEditDTO dto, HttpSession s) {
        int userId = getLoggedId(s);
            UserWithoutPassDTO userWithoutPassDTO = userService.edit(userId, dto);
            return userWithoutPassDTO;
    }
    @GetMapping("dominos/users/profile")
    public UserWithoutPassDTO view(HttpSession s){
        int userId = getLoggedId(s);
        UserWithoutPassDTO user = userService.viewProfile(userId);
            return user;
    }


    @PutMapping("/dominos/users/password")
    @Transactional
    public UserWithoutPassDTO changePassword(@Valid @RequestBody UserChangePasswordDTO dto, HttpSession s){
        int userId = getLoggedId(s);
        System.out.println(userId);
        UserWithoutPassDTO userWithoutPassDTO = userService.changePassword(userId, dto);
        return userWithoutPassDTO;
    }
    @GetMapping("/dominos/users")
    public List<UserWithoutPassDTO> getAll() {
        return userService.getAll();
    }
}


