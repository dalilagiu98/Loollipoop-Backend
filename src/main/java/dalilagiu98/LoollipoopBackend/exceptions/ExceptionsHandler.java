package dalilagiu98.LoollipoopBackend.exceptions;

import dalilagiu98.LoollipoopBackend.payloads.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // ---> 400
    public ErrorResponseDTO handleBadRequest(BadRequestException exception){
        if (exception.getErrorList() != null) {
            String message = exception.getErrorList().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(", "));
            return new ErrorResponseDTO(message, LocalDateTime.now());
        } else {
            return new ErrorResponseDTO(exception.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // ---> 401
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException exception) {
        return new ErrorResponseDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // --->403
    public ErrorResponseDTO handleForbidden(AccessDeniedException exception){
        return new ErrorResponseDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND) // ---> 404
    public ErrorResponseDTO handleNotFound(NotFoundException exception) {
        return new ErrorResponseDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // ---> 500
    public ErrorResponseDTO handleGenericErrors(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponseDTO("Problem server side! We are working for it.", LocalDateTime.now());
    }

}
