package org.roza.web.controllers.rest;

import org.modelmapper.ModelMapper;
import org.roza.components.UserCreation;
import org.roza.domain.models.rest.AddUserBindingModel;
import org.roza.domain.models.services.UserServiceModel;
import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserCreation userCreation;
    private final ModelMapper mapper;

    @Autowired
    public UserApiController(UserCreation userCreation, ModelMapper mapper) {
        this.userCreation = userCreation;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public String addUser(@Valid @RequestBody AddUserBindingModel model,
                        HttpServletRequest request)
            throws UserAlreadyExistsException, MessagingException {

        UserServiceModel userServiceModel = this.userCreation.
                addUser(this.mapper.map(model, UserServiceModel.class));
        VerificationTokenServiceModel tokenServiceModel = this.userCreation.createToken(userServiceModel);
        this.userCreation.sendVerificationEmail(tokenServiceModel, buildDomain(request));

        return tokenServiceModel.getId();
    }

    @PostMapping("/register/resendcode")
    public void resendCode(@Valid @RequestBody String userId,
                        HttpServletRequest request)
            throws MessagingException {

    }

    private String buildDomain(HttpServletRequest request) {
        return new StringBuilder().append(request.getScheme()).append("://")
                .append(request.getServerName()).append(":")
                .append(request.getServerPort()).toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAddUserValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        return errors.get(0).getDefaultMessage();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
