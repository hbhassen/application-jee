package com.example.app.rest;

import java.util.Objects;

import com.example.app.security.RequestPrincipalExtractor;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

/**
 * REST resource that exposes a secured endpoint returning the authenticated user information.
 * <p>
 * Usage example:
 * <pre>{@code
 * GET /simple-secured-app/api/hello
 * Response:
 * {
 *   "message": "Hello",
 *   "authenticatedUser": "user@example.com"
 * }
 * }</pre>
 */
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    private final RequestPrincipalExtractor principalExtractor;

    /**
     * Default constructor required by JAX-RS providers.
     */
    public HelloResource() {
        this(new RequestPrincipalExtractor());
    }

    /**
     * Creates a resource with a custom {@link RequestPrincipalExtractor}, primarily useful for testing.
     *
     * @param principalExtractor extractor used to resolve the authenticated principal
     */
    public HelloResource(RequestPrincipalExtractor principalExtractor) {
        this.principalExtractor = Objects.requireNonNull(principalExtractor, "principalExtractor");
    }

    /**
     * Returns a greeting message along with the authenticated principal propagated by the custom JASPIC module.
     *
     * @param securityContext JAX-RS {@link SecurityContext} injected by the container
     * @return a DTO containing a greeting and the principal name
     */
    @GET
    public HelloResponse hello(@Context SecurityContext securityContext) {
        String principalName = principalExtractor.extractPrincipalName(securityContext)
                .orElse("anonymous");
        return new HelloResponse("Hello", principalName);
    }

    /**
     * Simple DTO returned by the {@link HelloResource#hello(SecurityContext)} endpoint.
     */
    public static class HelloResponse {
        private final String message;
        private final String authenticatedUser;

        /**
         * Creates a new immutable response.
         *
         * @param message greeting message
         * @param authenticatedUser authenticated principal name
         */
        public HelloResponse(String message, String authenticatedUser) {
            this.message = Objects.requireNonNull(message, "message");
            this.authenticatedUser = Objects.requireNonNull(authenticatedUser, "authenticatedUser");
        }

        /**
         * Greeting message to return to the caller.
         *
         * @return message content
         */
        public String getMessage() {
            return message;
        }

        /**
         * Name of the authenticated principal extracted from the security context.
         *
         * @return principal name
         */
        public String getAuthenticatedUser() {
            return authenticatedUser;
        }
    }
}
