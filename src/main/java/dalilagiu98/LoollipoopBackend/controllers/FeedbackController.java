package dalilagiu98.LoollipoopBackend.controllers;

import dalilagiu98.LoollipoopBackend.entities.Feedback;
import dalilagiu98.LoollipoopBackend.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public Page<Feedback> getAllFeedback (@RequestParam (defaultValue = "0") int page,
                                          @RequestParam (defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sort) {
        return this.feedbackService.getAllFeedback(page, size, sort);
    }

}
