package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.exceptions.BadRequestException;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.NewUserResponseDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.UserLoginRequestDTO;
import dalilagiu98.LoollipoopBackend.payloads.user_payloads.UserLoginResponseDTO;
import dalilagiu98.LoollipoopBackend.services.AuthService;
import dalilagiu98.LoollipoopBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO login (@RequestBody UserLoginRequestDTO payload) {
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO save(@RequestBody @Validated NewUserRequestDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponseDTO(this.userService.save(body).getId());
    }
}
