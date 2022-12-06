package com.token.authenticate.controller;

import com.token.authenticate.domain.dto.ReviewCreateRequest;
import com.token.authenticate.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("")
    public ResponseEntity<String> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest, Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName() + reviewService.write( reviewCreateRequest.getUserName()));
    }
}
