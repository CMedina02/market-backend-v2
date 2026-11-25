package com.merida.tecnm.market_backend_v2.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    void signsAndValidatesToken() {
        JwtUtil util = new JwtUtil("test-secret-with-at-least-32-characters");
        var user = User.withUsername("1").password("hash").authorities("ROLE_USER").build();
        String token = util.generateToken(user);

        assertEquals("1", util.extractUsername(token));
        assertTrue(util.validateToken(token, user));
        assertFalse(util.validateToken(token, User.withUsername("2").password("hash").authorities("ROLE_USER").build()));
        assertThrows(Exception.class, () -> new JwtUtil("different-test-secret-with-32-characters").extractUsername(token));
    }

    @Test
    void rejectsWeakSecret() {
        assertThrows(IllegalArgumentException.class, () -> new JwtUtil("short"));
    }
}
