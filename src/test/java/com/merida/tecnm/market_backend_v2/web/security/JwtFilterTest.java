package com.merida.tecnm.market_backend_v2.web.security;

import com.merida.tecnm.market_backend_v2.domain.service.MarketUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtFilterTest {
    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void authenticatesValidBearerTokenAndIgnoresInvalidToken() throws Exception {
        JwtUtil jwt = new JwtUtil("test-secret-with-at-least-32-characters");
        MarketUserDetailsService users = mock(MarketUserDetailsService.class);
        var user = User.withUsername("1").password("hash").authorities("ROLE_USER").build();
        when(users.loadUserByUsername("1")).thenReturn(user);
        JwtFilter filter = new JwtFilter(jwt, users);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt.generateToken(user));
        filter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
        assertEquals("1", SecurityContextHolder.getContext().getAuthentication().getName());

        SecurityContextHolder.clearContext();
        MockHttpServletRequest invalid = new MockHttpServletRequest();
        invalid.addHeader("Authorization", "Bearer invalid-token");
        filter.doFilterInternal(invalid, new MockHttpServletResponse(), new MockFilterChain());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
