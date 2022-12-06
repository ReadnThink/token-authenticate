# token-authenticate

# 프로젝트 설명
Spring-security를 사용하여 인증을 연습하는 프로젝트.
Token이 잘 발행 되는지, 발행된 Token을 이용해 인증 시도 했을때 유효한 Token인지

## Endpoints <br/>

Post /api/v1/login 단순 토큰 발급<br/>
Post /api/v1/reviews 토큰 인증

## 실행방법
http://localhost:8080/reviews 를 호출 할 때 Header에 Authorization에 Bearer </login에서 받은 token>
을 넘겨 