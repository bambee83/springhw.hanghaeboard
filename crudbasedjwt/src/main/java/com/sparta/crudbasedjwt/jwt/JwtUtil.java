package com.sparta.crudbasedjwt.jwt;

import com.sparta.crudbasedjwt.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.security.Key;
import java.util.Date;

// 걍 흐름 이해정도만...?? 다 복붙해버림...... ㅠ
@Slf4j
@Component //빈 등록 !
@RequiredArgsConstructor
public class JwtUtil {

    //토큰 생성에 필요한 값
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // TOKEN 식별자
    private static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L; //1시간 (ms 기준)

    @Value("${jwt.secret.key}")  //application property 키값을 가져온다
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct //처음에 객체를 생성할 때 초기화하는 함수 > 구글링해서 한 번 읽어보기
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); //값을 가져와서 디코딩 (반환값이 바이트임)
        key = Keys.hmacShaKeyFor(bytes); // 키객체에 만들어진 바이트값 넣어주기
    }

    // header 에서 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); //AUTHORIZATION 에 있는 토큰값을 가져오는 코드
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) { // 해당 코드가 있는지 확인 BEARER_PREFIX 로 시작하는지
            return bearerToken.substring(7); //앞에있는 7 글자를 떼어낸 후 반환
        }
        return null;
    }

    // 토큰 생성 > 스트링 형식의 JSON 토큰으로 반환된다
    public String createToken(String username, UserRoleEnum role) {  //이름 과 권한을 줘야 함
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //현재시간 + 토큰시간
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증 > //내부적으로 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기 > 앞에서 토큰을 검증해서 유효한 토큰임을 확인해서 try catch 가 없다.
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); //getBody 를 통해 정보를 가져온다
    }
}