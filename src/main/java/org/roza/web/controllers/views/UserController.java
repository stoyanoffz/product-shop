package org.roza.web.controllers.views;

import org.roza.exceptions.ExpiredTokenException;
import org.roza.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final VerificationTokenService tokenService;

    @Autowired
    public UserController(VerificationTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return view("users/register");
    }

    @GetMapping("/confirmregistration/{id}")
    public ModelAndView confirmRegistration(@PathVariable String id) throws ExpiredTokenException {
        this.tokenService.checkToken(id);

        return view("users/confirm-registration");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return view("users/login");
    }

    @GetMapping("/forgotpassword")
    public ModelAndView getPasswordReset() {
        return view("users/password-reset");
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public ModelAndView handleExpiredTokenException(ExpiredTokenException e) {
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.addObject("errorMessage", e.getMessage());

        return view("errors", modelAndView);
    }
}
