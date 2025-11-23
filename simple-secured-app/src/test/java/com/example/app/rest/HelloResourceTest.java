package com.example.app.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.app.security.RequestPrincipalExtractor;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloResourceTest {

    @Test
    @DisplayName("Should return principal name when present")
    void shouldReturnPrincipalName() {
        SecurityContext securityContext = mock(SecurityContext.class);
        RequestPrincipalExtractor extractor = mock(RequestPrincipalExtractor.class);
        when(extractor.extractPrincipalName(securityContext)).thenReturn(Optional.of("jaspic-user"));

        HelloResource resource = new HelloResource(extractor);
        HelloResource.HelloResponse helloResponse = resource.hello(securityContext);

        assertNotNull(helloResponse);
        assertEquals("Hello", helloResponse.getMessage());
        assertEquals("jaspic-user", helloResponse.getAuthenticatedUser());
    }

    @Test
    @DisplayName("Should fallback to anonymous when principal missing")
    void shouldFallbackToAnonymous() {
        SecurityContext securityContext = mock(SecurityContext.class);
        RequestPrincipalExtractor extractor = mock(RequestPrincipalExtractor.class);
        when(extractor.extractPrincipalName(securityContext)).thenReturn(Optional.empty());

        HelloResource resource = new HelloResource(extractor);
        HelloResource.HelloResponse helloResponse = resource.hello(securityContext);

        assertEquals("anonymous", helloResponse.getAuthenticatedUser());
    }
}
