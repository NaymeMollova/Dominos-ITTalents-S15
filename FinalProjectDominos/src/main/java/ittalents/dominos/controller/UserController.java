package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.UserLoginDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
<<<<<<< HEAD
import jakarta.validation.Valid;
import jakarta.validation.Validator;
=======
>>>>>>> 5b443fd058749983440b4cde4b0e97697344d84b
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends AbstractController  {

    @Autowired
    private UserService userService;

<<<<<<< HEAD
    @PostMapping("/dominos/users")
    public UserWithoutPassDTO register(@Valid @RequestBody UserRegisterDTO dto){
        return userService.register(dto);
    }
=======

//    @PostMapping("/dominos/users")
//    public UserWithoutPassDTO register(@Valid @RequestBody UserRegisterDTO dto){
//        return userService.register(dto);
//    }
>>>>>>> 5b443fd058749983440b4cde4b0e97697344d84b

    @PostMapping("/dominos/users/login")
    public UserWithoutPassDTO login(@RequestBody UserLoginDTO dto, HttpSession s){
        UserWithoutPassDTO respDto = userService.login(dto);
        s.setAttribute("LOGGED", true);
        s.setAttribute("LOGGED_ID", respDto.getId());
        return respDto;

    }
}
