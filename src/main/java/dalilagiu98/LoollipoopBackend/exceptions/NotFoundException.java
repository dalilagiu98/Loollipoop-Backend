package dalilagiu98.LoollipoopBackend.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("Object with id " + id + " not found!");
    }

    public NotFoundException(String email){
        super("User with email " + email + " not found!");
    }
}
