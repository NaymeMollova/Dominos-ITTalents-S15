package ittalents.dominos.controller;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

public class AbstractController {
    protected int getLoggedId(HttpSession s){
        if(s.getAttribute("LOGGED_ID") == null){
            throw new UnauthorizedException("You have to login first");
        }
        return (int) s.getAttribute("LOGGED_ID");
    }
}
