# Integration with WildFly 31

## Prerequisites
- Java 17
- WildFly 31 with the custom JASPIC server authentication module installed and bound to the security domain `idp-jaspic-domain`.

## Configure JASPIC Module
1. Copy the custom JASPIC module JAR into `${WILDFLY_HOME}/modules` following the module definition generated previously.
2. Declare the module in `module.xml` and ensure dependencies include `jakarta.security.auth.message` and WildFly security APIs.
3. Register the JASPIC module within the Elytron configuration and map it to the security domain `idp-jaspic-domain`.

## Deploying the WAR
1. Build the application:
   ```bash
   mvn clean package
   ```
2. Copy `target/simple-secured-app.war` into `${WILDFLY_HOME}/standalone/deployments/`.
3. Start or restart WildFly:
   ```bash
   ${WILDFLY_HOME}/bin/standalone.sh -c standalone.xml
   ```
4. Verify deployment via the management console or by checking server logs for `simple-secured-app`.

## Testing the Endpoint
- Execute an authenticated request that triggers the JASPIC module, for example:
  ```bash
  curl -v --cookie "JSESSIONID=<session>" \
       http://localhost:8080/simple-secured-app/api/hello
  ```
- Successful responses return HTTP 200 with the authenticated principal:
  ```json
  {
    "message": "Hello",
    "authenticatedUser": "your-principal"
  }
  ```
