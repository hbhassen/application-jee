package com.example.app;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import com.example.app.rest.HelloResource;

/**
 * JAX-RS application configuration that exposes REST resources under the base path {@code /api}.
 * <p>
 * Usage example:
 * <pre>{@code
 * // The application container discovers this class automatically and binds it to /api
 * // Example request: GET /simple-secured-app/api/hello
 * }</pre>
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {

    /**
     * Registers the REST resource classes available for this application.
     *
     * @return a set containing the {@link HelloResource} class
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(HelloResource.class);
        return resources;
    }
}
