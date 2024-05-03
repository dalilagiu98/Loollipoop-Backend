package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.User;
import dalilagiu98.LoollipoopBackend.exceptions.BadRequestException;
import dalilagiu98.LoollipoopBackend.payloads.NewUserRequestDTO;
import dalilagiu98.LoollipoopBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam (defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){
        return this.userService.getAllUser(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable long id){
        return this.userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserRequestDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return userService.save(body);
    }

    @PutMapping("/{userId}")
    public User updateById(@PathVariable long id, @RequestBody @Validated NewUserRequestDTO body, BindingResult validation){
        if (validation.hasErrors()) throw new BadRequestException(validation.getAllErrors());
        else return userService.update(id, body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id){
        this.userService.delete(id);
    }
}
