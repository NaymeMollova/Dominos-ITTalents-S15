package ittalents.dominos.controller;
import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    public UserWithoutPassDTO register(@Valid @RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/dominos/users/login")
    public UserWithoutPassDTO login(@RequestBody UserLoginDTO dto, HttpSession s) {
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;
    }

    @PostMapping("/dominos/users/logout")
    public void logout(HttpSession s) {
        if (s != null && !s.isNew()) {
            s.invalidate();
        } else {
            throw new UnauthorizedException("First login");
        }
    }
    @PutMapping("/dominos/users/profile")
    public UserWithoutPassDTO edit(@RequestBody UserEditDTO dto, HttpSession s) {
        if (s != null && s.getAttribute("LOGGED") != null && !s.isNew()) {
            int userId = (int) s.getAttribute("LOGGED_ID");
            UserWithoutPassDTO userWithoutPassDTO = userService.edit(userId, dto);
            return userWithoutPassDTO;
        }else {
            throw new BadRequestException("Users is not found");
        }
    }
    @GetMapping("dominos/users/profile")
    public UserWithoutPassDTO view(HttpSession s){
        if(s != null && s.getAttribute("LOGGED") != null && !s.isNew()){
            int userId = (int) s.getAttribute("LOGGED_ID");
            UserWithoutPassDTO userWithoutPassDTO = userService.viewProfile(userId);
            return userWithoutPassDTO;
        }else {
            throw new BadRequestException("Users is not found");
        }
    }
    @PutMapping("/dominos/users/password")
    public UserWithoutPassDTO changePassword(@RequestBody UserChangePasswordDTO dto, HttpSession s){
        if (s != null && s.getAttribute("LOGGED") != null && !s.isNew()) {
            int userId = (int) s.getAttribute("LOGGED_ID");
            UserWithoutPassDTO userWithoutPassDTO = userService.changePassword(userId);
            return userWithoutPassDTO;
        }else {
            throw new BadRequestException("Users is not found");
        }
    }
    @GetMapping("/dominos/users")
    public List<UserWithoutPassDTO> getAll() {
        return userService.getAll();
    }
}


