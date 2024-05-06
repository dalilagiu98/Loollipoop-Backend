package dalilagiu98.LoollipoopBackend.payloads.user_payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewUserRequestDTO(@NotEmpty(message = "Name field must not be empty.")
                                String name,
                                @NotEmpty(message = "Surname field must not be empty.")
                                String surname,
                                @NotNull(message = "The email is required.")
                                @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", message = "Invalid email address.")
                                String email,
                                @NotNull(message = "The password is required.")
                                @Size(min = 8, message = "Password must be at least 8 characters long.")
                                String password) {
}
