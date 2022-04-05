package com.override.controller.rest;

import com.override.models.Review;
import com.override.service.ReviewService;
import dtos.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.saveOrUpdateReview(reviewDTO);
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @GetMapping
    public List<Review> findReview(@RequestBody ReviewDTO reviewDTO) {
        if (reviewDTO.getMentorLogin() != null && reviewDTO.getStudentLogin() == null) {
            return reviewService.findReviewByMentorLogin(reviewDTO);
        }
        if (reviewDTO.getStudentLogin() != null && reviewDTO.getMentorLogin() == null) {
            return reviewService.findReviewByStudentLogin(reviewDTO);
        }
        if (reviewDTO.getStudentLogin() != null && reviewDTO.getMentorLogin() != null) {
            return reviewService.findReviewByMentorLoginAndStudentLogin(reviewDTO);
        }
        if (reviewDTO.getBookedDate() != null && reviewDTO.getBookedTime() == null) {
            return reviewService.findReviewByBookedDate(reviewDTO);
        }
        if (reviewDTO.getBookedDate() != null && reviewDTO.getBookedTime() != null) {
            return reviewService.findReviewByBookedDateAndTime(reviewDTO);
        }
        else {
            return reviewService.findReviewByMentorIsNull();
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.deleteReview(reviewDTO);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }
}
