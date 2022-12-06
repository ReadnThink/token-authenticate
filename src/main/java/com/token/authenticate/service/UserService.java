package com.token.authenticate.service;

import com.token.authenticate.domain.User;
import com.token.authenticate.domain.dto.UserJoinRequest;
import com.token.authenticate.domain.dto.UserLoginRequest;
import com.token.authenticate.repository.UserRepository;
import com.token.authenticate.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("&{jwt.token.secret}")
    private String key;

    private long expireTimeMs = 1000 * 60 * 60L;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public String join(UserJoinRequest userJoinRequest) {
        //중복 처리
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent((user -> {
                    throw new RuntimeException("id가 중복입니다.");
                }));

        User user = User.builder()
                .userName(userJoinRequest.getUserName())
                .password(encoder.encode(userJoinRequest.getPassword()))
                .build();
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    public String login(UserLoginRequest userLoginRequest) {
        //아이디 확인
        User findUser = userRepository.findByUserName(userLoginRequest.getUserName())
                .orElseThrow(()-> new RuntimeException("아이디가 중복됩니다."));

        //password확인
        if (!encoder.matches(userLoginRequest.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("password 불일치");
        }

        String token = JwtTokenUtil.CreateToken(userLoginRequest.getUserName(), key, expireTimeMs);
        //토큰 발급
        return token;
    }
}
