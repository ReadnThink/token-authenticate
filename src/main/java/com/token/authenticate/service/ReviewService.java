package com.token.authenticate.service;

import com.token.authenticate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;

    public String write(String userName) {
        return "리뷰 등록이 되었습니다";
    }
}
