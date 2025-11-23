# Architecture

## Overview
The application is a lightweight Jakarta EE 10 WAR targeting WildFly 31. It exposes a single JAX-RS resource (`HelloResource`) under the base path `/api`. Authentication is delegated to a custom JASPIC server authentication module configured at the application server level.

## Components
- **ApplicationConfig**: Registers JAX-RS and sets the base URI path `/api`.
- **HelloResource**: REST endpoint at `/api/hello` returning the authenticated principal.
- **RequestPrincipalExtractor**: Utility used by `HelloResource` to obtain the `Principal` from the container-provided `SecurityContext`.
- **Deployment Descriptors**: `web.xml` declares constraints and `jboss-web.xml` associates the application with the JASPIC-enabled security domain.

## Request Flow
1. A client calls `GET /simple-secured-app/api/hello`.
2. WildFly routes the request to the configured JASPIC server authentication module for validation.
3. On success, the module establishes a `Principal` and roles in the container security context mapped to `idp-jaspic-domain`.
4. JAX-RS invokes `HelloResource`, which uses `RequestPrincipalExtractor` to read the `Principal` from the `SecurityContext`.
5. The resource returns JSON containing the greeting and the authenticated user name.

## JASPIC Interaction
- The custom module plugs into the WildFly security domain `idp-jaspic-domain`.
- Once authentication succeeds, the resulting `Principal` is available to the application via `SecurityContext#getUserPrincipal()`.
