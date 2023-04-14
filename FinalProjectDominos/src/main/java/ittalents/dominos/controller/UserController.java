package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.UserRegisterDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController  {

    @Autowired
    private UserService userService;

    private UserWithoutPassDTO register(@RequestBody UserRegisterDTO registerDTO)
    {
        return userService.register(registerDTO);
    }
}
