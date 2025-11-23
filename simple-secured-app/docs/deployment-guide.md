# Deployment Guide

## Build the WAR
1. Ensure JDK 17 is installed and `JAVA_HOME` points to it.
2. From the project root, run:
   ```bash
   mvn clean package
   ```
3. The built artifact is available at `target/simple-secured-app.war`.

## Prepare WildFly 31
1. Install the custom JASPIC server authentication module if not already present.
2. Bind the module to the Elytron security domain `idp-jaspic-domain`.
3. Confirm the domain is available to web applications via the Undertow subsystem.

## Deploy
1. Copy the WAR to `${WILDFLY_HOME}/standalone/deployments/`.
2. Start WildFly:
   ```bash
   ${WILDFLY_HOME}/bin/standalone.sh -c standalone.xml
   ```
3. Watch the console or `standalone/log/server.log` to verify successful deployment.

## Access
- Application root: `http://localhost:8080/simple-secured-app/`
- Secured endpoint: `http://localhost:8080/simple-secured-app/api/hello`

## Restart Sequence
- To apply configuration changes, stop the server with `Ctrl+C` or `${WILDFLY_HOME}/bin/jboss-cli.sh --connect command=:shutdown`.
- Start the server again using the command above.
