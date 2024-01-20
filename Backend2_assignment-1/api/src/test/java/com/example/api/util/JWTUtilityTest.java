package com.example.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@ActiveProfiles("test")
@SpringBootTest
class JWTUtilityTest {

    private String secretKey;
    private String accessToken;

    @BeforeEach
    void setUp() {
        secretKey = "onlyTheTestKnowsThisSecret";
        UserDetails user = User.builder().username("ali").password("1234").authorities(List.of()).build();
        JWTUtility jwtUtility = new JWTUtility(secretKey);
        accessToken = jwtUtility.generateToken(user);
    }
    private Claims getBody(String accessToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken)
                .getBody();
    }

    @Test
    @DisplayName("Get subject (username) from token")
    void accessToken_usesUsername_Test() {
        String subject = getBody(accessToken).getSubject();
        assertThat(subject, equalTo("ali"));
    }

    @Test
    @DisplayName("Get issued date from token")
    void accessToken_usesIssueDate_Test() {
        Date issuedAt = getBody(accessToken).getIssuedAt();
        assertThat(issuedAt, notNullValue());
    }
}