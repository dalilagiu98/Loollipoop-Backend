package dalilagiu98.LoollipoopBackend.payloads.user_payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(@NotNull(message = "The password is required.")
                                @Size(min = 8, message = "Password must be at least 8 characters long.") String password) {
}
