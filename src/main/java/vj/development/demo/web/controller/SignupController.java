package vj.development.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vj.development.demo.domain.AppUser;
import vj.development.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/singup")
    public String SignupView(AppUser appUser) {
        return "singup/index";
    }

    @PostMapping("/singup")
    public String registerUser(@Valid AppUser appUser, BindingResult bindingResult) {

        if (userService.emailInUse(appUser.getEmail())) {
            bindingResult.addError(new FieldError("appUser", "email", "Adresa " + appUser.getEmail() + " se vec koristi."));
            return "singup/index";
        }
        if (userService.userExists(appUser.getUsername())) {
            bindingResult.addError(new FieldError("appUser", "username", "Korisnicko ime " + appUser.getUsername() + " se vec koristi."));
            return "singup/index";
        }

        if (bindingResult.hasErrors()) {
            return "singup/index";
        }
        userService.saveUser(appUser);
        return "/login";
    }

}
