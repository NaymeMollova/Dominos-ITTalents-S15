package ittalents.dominos.controller;


import ittalents.dominos.model.DTOs.ErrorDTO;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Object o){
        return generateErrorDTO(o, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Object o){
        return generateErrorDTO(o, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Object o){
        return generateErrorDTO(o, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Object o){
        return generateErrorDTO(o, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO generateErrorDTO(Object e, HttpStatus s){
        return ErrorDTO.builder()
                .msg(e)
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

}

