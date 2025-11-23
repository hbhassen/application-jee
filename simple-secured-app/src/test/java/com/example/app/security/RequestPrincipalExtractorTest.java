package com.example.app.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Optional;

import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestPrincipalExtractorTest {

    @Test
    @DisplayName("Should extract principal when available")
    void shouldExtractPrincipal() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("alice");
        when(securityContext.getUserPrincipal()).thenReturn(principal);

        RequestPrincipalExtractor extractor = new RequestPrincipalExtractor();
        Optional<String> result = extractor.extractPrincipalName(securityContext);

        assertTrue(result.isPresent());
        assertEquals("alice", result.get());
    }

    @Test
    @DisplayName("Should return empty when security context missing")
    void shouldReturnEmptyWhenContextMissing() {
        RequestPrincipalExtractor extractor = new RequestPrincipalExtractor();
        Optional<String> result = extractor.extractPrincipalName(null);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return empty when principal not set")
    void shouldReturnEmptyWhenPrincipalMissing() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getUserPrincipal()).thenReturn(null);

        RequestPrincipalExtractor extractor = new RequestPrincipalExtractor();
        Optional<String> result = extractor.extractPrincipalName(securityContext);

        assertFalse(result.isPresent());
    }
}
