package com.example.api.common.config.jwt.provider;

import com.example.api.common.base.vo.Code;
import com.example.api.common.config.jwt.biz.CustomUserDetailService;
import com.example.api.common.config.jwt.enums.JwtExpirationEnums;
import com.example.api.common.config.jwt.enums.JwtHeaderUtilEnums;
import com.example.api.common.exception.GeneralException;
import com.example.api.common.rep.auth.redis.logout.LogoutAccessTokenRedis;
import com.example.api.common.rep.auth.redis.logout.LogoutAccessTokenRedisRepository;
import com.example.api.common.rep.auth.redis.refresh.RefreshTokenRedis;
import com.example.api.common.rep.auth.redis.refresh.RefreshTokenRedisRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailService customUserDetailService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public String generateAccessToken(String userEmail) {
        return doGenerateToken(userEmail, JwtExpirationEnums.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    }

    public RefreshTokenRedis saveRefreshToken(String userEmail) {
        return refreshTokenRedisRepository.save(RefreshTokenRedis.createRefreshToken(userEmail,
                generateRefreshToken(userEmail), JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
    }

    public String generateRefreshToken(String userEmail) {
        return doGenerateToken(userEmail, JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    }

    private String doGenerateToken(String userEmail, long expireTime) { // 1
        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public void deleteByIdRefreshRedis(String userEmail) {
        refreshTokenRedisRepository.deleteById(userEmail);
    }

    public void saveLogoutAccessRedis(String accessToken, String userEmail, long remainMilliSeconds) {
        logoutAccessTokenRedisRepository.save(LogoutAccessTokenRedis.of(accessToken, userEmail, remainMilliSeconds));
    }

    public Optional<RefreshTokenRedis> findById(String username) {
        return refreshTokenRedisRepository.findById(username);
    }

//    ------------------------------------------------------------------------------------------------------------------

    public String resolveToken(String token) {
        return token.substring(7);
    }

    public String getHeaderToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(JwtHeaderUtilEnums.GRANT_TYPE.getValue())) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public Claims extractAllClaims(String token) { // 2
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail() {
        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new GeneralException(Code.NO_SEARCH_USER, "회원이 없습니다."));
        try {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            return principal.getUsername();
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
        return extractAllClaims(token).get("userEmail", String.class);
    }

//    public String getUserEmail(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        Key secretKey1 = Keys.hmacShaKeyFor(keyBytes);
        return secretKey1;
    }

    public Boolean validateToken(String accessToken) {
        //로그아웃 체크
        if (checkLogout(accessToken)) {
            return false;
        }

        //유효시간 체크
        return isTokenExpired(accessToken);
    }

    private Boolean checkLogout(String accessToken) {
        return logoutAccessTokenRedisRepository.existsById(accessToken);
    }

    public Boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return !expiration.before(new Date());
        } catch (JwtException | NullPointerException exception) {
            return false;
        }
    }

    public long getRemainMilliSeconds(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

}
