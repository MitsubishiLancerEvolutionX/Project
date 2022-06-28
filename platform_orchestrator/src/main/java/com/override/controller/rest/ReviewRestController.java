package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.ReviewService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PatchMapping
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.saveOrUpdateReview(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @PatchMapping("/acceptation")
    public ResponseEntity<String> acceptReview(@RequestBody ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.acceptReview(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }


    @PostMapping
    public List<ReviewDTO> findReview(@RequestBody ReviewFilterDTO reviewFilterDTO) {
        return reviewService.findReview(reviewFilterDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/cancellation")
    public ResponseEntity<String> deleteReviewNotification(@RequestParam Long id) {
        reviewService.cancelReview(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }
}
