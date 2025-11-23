package com.example.app.security;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

import jakarta.ws.rs.core.SecurityContext;

/**
 * Utility component that extracts the authenticated {@link Principal} from a {@link SecurityContext}.
 * <p>
 * Usage example:
 * <pre>{@code
 * SecurityContext context = ...;
 * Optional<String> principalName = new RequestPrincipalExtractor().extractPrincipalName(context);
 * principalName.ifPresent(System.out::println);
 * }</pre>
 */
public class RequestPrincipalExtractor {

    /**
     * Retrieves the {@link Principal} associated with the provided {@link SecurityContext}.
     *
     * @param securityContext the JAX-RS security context, may be {@code null}
     * @return an {@link Optional} containing the principal when present
     */
    public Optional<Principal> extractPrincipal(SecurityContext securityContext) {
        if (securityContext == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(securityContext.getUserPrincipal());
    }

    /**
     * Retrieves the authenticated principal name from the provided {@link SecurityContext}.
     *
     * @param securityContext the JAX-RS security context, may be {@code null}
     * @return an {@link Optional} containing the principal name when available
     */
    public Optional<String> extractPrincipalName(SecurityContext securityContext) {
        return extractPrincipal(securityContext)
                .map(Principal::getName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(name -> !name.isEmpty());
    }
}
