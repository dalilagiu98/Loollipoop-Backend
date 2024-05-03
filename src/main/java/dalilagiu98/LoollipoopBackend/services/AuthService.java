package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.exceptions.UnauthorizedException;
import dalilagiu98.LoollipoopBackend.payloads.UserLoginRequestDTO;
import dalilagiu98.LoollipoopBackend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginRequestDTO userLogin) {
        User user = this.userService.findByEmail(userLogin.email());
        if (user.getPassword().equals(userLogin.password())){
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials. Please login again.");
        }
    }
}
