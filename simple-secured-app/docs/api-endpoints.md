# API Endpoints

## GET /api/hello
Returns the authenticated principal propagated by the JASPIC module.

- **URL**: `/simple-secured-app/api/hello`
- **Method**: `GET`
- **Authentication**: Required (handled by `idp-jaspic-domain`).
- **Success Response**:
  - **Code**: `200 OK`
  - **Body**:
    ```json
    {
      "message": "Hello",
      "authenticatedUser": "<principal-name>"
    }
    ```
- **Error Responses**:
  - `401 Unauthorized` if authentication fails.
  - `403 Forbidden` if the caller lacks the `user` role.

## cURL Example
```bash
curl -H "Accept: application/json" \
     http://localhost:8080/simple-secured-app/api/hello
```
