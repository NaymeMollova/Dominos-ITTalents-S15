package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.ErrorDTO;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {
    public static final String CART = "CART";
    @Autowired
    protected UserService userService;



    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e) {
        return generateErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e) {
        return generateErrorDTO(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e) {
        return generateErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e) {
        return generateErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO generateErrorDTO(Object o, HttpStatus s) {
        return ErrorDTO.builder()
                .msg(o)
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return generateErrorDTO(errors, HttpStatus.BAD_REQUEST);
    }

    protected void isAdminLoggedIn(HttpSession session) {
        getLoggedId(session);
        int userId = (int) session.getAttribute("LOGGED_ID");
        if (!userService.findLoggedUser(userId).isAdmin()) {
            System.out.println(userService.findLoggedUser(userId).isAdmin());
            //return false
            throw new UnauthorizedException("You are not admin");
        }
        //return true;
    }

//    protected boolean isUserLoggedIn(HttpSession session) {
//        if (!isThereLoggedInUser(session)) {
//            throw new UnauthorizedException("You have to log in first");
//        }
//        int userId = getLoggedId(session);
//        ////////?????????????????????????????????//
////        if (userService.findLoggedUser(userId) == null) {
////            throw new UnauthorizedException("You have to log in first");
////        }
//        return true;
//    }

//    protected boolean isThereLoggedInUser(HttpSession session) {
//        if (session.getAttribute("LOGGED") == null) {
//            return false;
//        }
//        return true;
//    }

    protected int getLoggedId(HttpSession s) {
        if (s.getAttribute("LOGGED_ID") == null) {
            throw new UnauthorizedException("You have to login first");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
//    protected Map<ItemInCartDTO, Integer> getCart(HttpSession session){
//        Map<ItemInCartDTO, Integer> cart;
//        if(session.getAttribute(CART) == null){
//            cart = new LinkedHashMap<>();
//        }
//        else {
//            cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
//        }
//        session.setAttribute(CART,cart);
//        return cart;
//    }

}

