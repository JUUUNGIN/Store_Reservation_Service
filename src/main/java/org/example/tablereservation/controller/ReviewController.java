package org.example.tablereservation.controller;

import org.example.tablereservation.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeReview(
            @RequestParam String phoneNumber,
            @RequestParam String storeName,
            @RequestParam String content,
            @RequestParam int rating) {

        try {
            String response = reviewService.writeReview(phoneNumber, storeName, content, rating);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    리뷰 수정
    @PostMapping("/edit")
    public ResponseEntity<String> editReview(
            @RequestParam String phoneNumber,
            @RequestParam String storeName,
            @RequestParam String newContent,
            @RequestParam int newRating) {

        try {
            String response = reviewService.editReview(phoneNumber, storeName, newContent, newRating);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

//    리뷰 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteReview(
            @RequestParam String phoneNumber,
            @RequestParam String storeName,
            @RequestParam boolean isUserRequest) {

        try {
            String response = reviewService.deleteReview(phoneNumber, storeName, isUserRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
