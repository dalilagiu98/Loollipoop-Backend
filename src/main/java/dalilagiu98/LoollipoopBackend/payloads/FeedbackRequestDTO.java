package dalilagiu98.LoollipoopBackend.payloads;

import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDTO(int score, String title, String description) {
}
