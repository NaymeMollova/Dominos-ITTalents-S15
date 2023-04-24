package ittalents.dominos.controller;
import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;


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
        getLoggedId(s);
        s.invalidate();
    }
    @PutMapping("/dominos/users/profile")
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
    public UserWithoutPassDTO changePassword(@Valid @RequestBody UserChangePasswordDTO dto, HttpSession s){
        int userId = getLoggedId(s);
        UserWithoutPassDTO userWithoutPassDTO = userService.changePassword(userId, dto);
        return userWithoutPassDTO;
    }
    @GetMapping("/dominos/users")
    public List<UserWithoutPassDTO> getAll(HttpSession session) {
        isAdminLoggedIn(session);
        return userService.getAll();
    }
}


