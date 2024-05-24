package dalilagiu98.LoollipoopBackend.services;

import dalilagiu98.LoollipoopBackend.entities.Feedback;
import dalilagiu98.LoollipoopBackend.payloads.FeedbackRequestDTO;
import dalilagiu98.LoollipoopBackend.repositories.FeedbackDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackDAO feedbackDAO;
    @Autowired
    private UserService userService;

    public Page<Feedback> getAllFeedback(int page, int size, String sort) {
        if(size>50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.feedbackDAO.findAll(pageable);
    }

    public Feedback save(FeedbackRequestDTO payload, long id) {
        Feedback newFeedback = new Feedback(payload.score(), payload.title(), payload.description(), this.userService.findById(id));
        return this.feedbackDAO.save(newFeedback);
    }
}
