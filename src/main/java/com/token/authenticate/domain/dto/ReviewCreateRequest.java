package com.token.authenticate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {
    private String userId;
    private String title;
    private String content;
    private String userName;
}
