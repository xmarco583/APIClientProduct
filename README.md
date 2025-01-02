# Proyecto API de Información del Cliente y Productos Financieros

## Descripción
Este proyecto consiste en la creación de una API que devuelve información del cliente y sus productos financieros, como cuentas de ahorro y tarjetas de crédito. La API recibe un valor **codigoUnico** y devuelve los datos del cliente, incluyendo **nombres**, **apellidos**, **tipoDocumento**, **numeroDocumento** y todos sus productos financieros (tipo de productos, nombre y saldo).

## Funcionalidades
- Devuelve información del cliente.
- Devuelve información de los productos financieros del cliente.
- Autenticación para acceder a la API.
- Encriptación del valor **codigoUnico**.
- Integración de microservicios para cliente y productos financieros.
- Uso de patrones de diseño y principios SOLID.
- Diseño de la API usando Open API (Swagger).
- Implementación de capa de seguridad con OAuth.
- Configuración de logback.
- Creación de un starter básico en Spring.
- Dockerización de microservicios y BFF-Pendiente



## Estructura del Proyecto
1. **Microservicio de Cliente**: Contiene la información del cliente.
2. **Microservicio de Productos Financieros**: Contiene la información de los productos financieros.
3. **Backend for Frontend (BFF)**: Integra los microservicios de cliente y productos financieros.

## Tecnologías Utilizadas
- **Java 17**: Interfaces Funcionales, Predicate, Stream, Optional, Default, Lambda, Date Time.
- **Spring Framework**: Security, Boot, Data.
- **Junit 5**: Para pruebas.
- **AOP (Aspect-Oriented Programming)**: Programación orientada a aspectos.
- **WebFlux**: Para la programación reactiva.
- **OAuth**: Para la capa de seguridad.
- **Logback**: Para la configuración de logs.
- **MapStruct, Lombok, Gson o Jackson**: Para mapeo y manejo de datos.
- **Docker**: Para la dockerización de los microservicios y BFF.

## Uso de la API
El uso de la API puede ser demostrado desde Postman. Se debe realizar una petición a la API enviando el **codigoUnico** encriptado. La API responderá con la información del cliente y sus productos financieros.



### Respuesta de endpoint de microservicio cliente 
- Tener el cuenta el encabezado 'X-Request-ID' al momento de realizar la solicitud.En el BFF el mismo servicio lo generará,pero para pruebas en el endpoint puede ser cualquier string.
```json
{
  "id": 1,
  "codigoUnico": 124,
  "nombres": "Juan Daniel",
  "apellidos": "Alvarado Diaz",
  "tipoDoc": "DNI",
  "numeroDoc": "74823099"
}
```

### Respuesta de endpoint de microservicio Producto
```json
[
  {
    "id": 1,
    "tipoProducto": "Cuenta",
    "nombre": "Cuenta de ahorros",
    "saldo": 234.12,
    "codigoCliente": 124
  },
  {
    "id": 2,
    "tipoProducto": "Tarjeta",
    "nombre": "Credito visa",
    "saldo": 5000,
    "codigoCliente": 124
  }
]
```
### Flujo de autorización OAuth2

- Nos vamos a logear en el siguiente link http://127.0.0.1:8080/oauth2/authorization/oauth-client.
- Esto nos va a redirigir a http://127.0.0.1:5000/login pagina para logearse con las credenciales,en este caso username=test y password=1234567.
- Aceptamos todos los permisos y nos dará como resultado el código de autorización para obtener el token.
- Ejemplo de código de autorización:
 ```json
{
  "authorizationCode": "5RX-GwE3_RXT9sx9fBCIYAvV6R1Adl4obJF1YLduMWMPc5r8_CcS4tazacD37U_BRKlDKnpZsxQq8DxFAPtdUm_oID3h0QQco3HAxOs1ngEYBy9rv7vXEbOcfCI_iJeE"
}
```
- Nos dirigimos a Postman tipo de solicitud:POST  http://127.0.0.1:5000/oauth2/token y en Body usamos form-data agregamos los siguientes 3 pares de key-valor
- Key1:code value1:código de autorización  Key2:grant_type value2:authorization_code  Key3:redirect-uri value3:http://127.0.0.1:8080/authorized
- Esto nos dara el siguiente resultado:
```json
{
    "access_token": "eyJraWQiOiI3ZWY3NmYxYy00ZWM1LTRmZWYtYWZhMS1iMmQ2NjNjNmE4YWUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiYXVkIjoib2F1dGgtY2xpZW50IiwibmJmIjoxNzM1ODMzNTM0LCJzY29wZSI6WyJyZWFkIiwib3BlbmlkIiwicHJvZmlsZSIsIndyaXRlIl0sImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6NTAwMCIsImV4cCI6MTczNTgzMzgzNCwiaWF0IjoxNzM1ODMzNTM0LCJqdGkiOiI4MDlhZjNmZS0zNWNhLTQ4OWEtODRkYi0wMWY4NDJlYWU2ODcifQ.g-auNTzdzIXEe6N5tyLdwiItB1Um7lDZF6cLpA15SUC7HIit9xqUygiSbgc1sPUV-bCOtkhpzeLkuHEhFeDL2D6HU6O-NdqR7a0_nj7xzbUBWq_saUUXHQA-yvmjECqPHvSPEijFXOCiyNSciBg3I3iKi36O2iEw7hPP7DLP2N7Qj3CVePIDee52P_ajEz5nrBAqp0g24quRRyWx4eJ09xCDdb5dsy2XsI9fEON3DHbnVQvyWvlp4SeoBGi9Xhz0HAAOFn2XJ0i0xrN5jv1Sc66GKEdvl3AUG7XyGKsaa_sMl9Dh9D6ljXjjG7Y05m4jXdHYql4hfNuTNBbV9L1Ksg",
    "refresh_token": "nfQCxRz9og2UZDb7KPgq1dDWNdieJ8RomvzfOMG36zjyUYmtDnrHPA3gMUVrCq8rruL0OaoqKN59GGdX8Bni4KCRIiEbMwswSJN4x0oDQE7c-mjOML7fYt4Ez9G4EGx3",
    "scope": "read openid profile write",
    "id_token": "eyJraWQiOiI3ZWY3NmYxYy00ZWM1LTRmZWYtYWZhMS1iMmQ2NjNjNmE4YWUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiYXVkIjoib2F1dGgtY2xpZW50IiwiYXpwIjoib2F1dGgtY2xpZW50IiwiYXV0aF90aW1lIjoxNzM1ODMzNTI1LCJpc3MiOiJodHRwOi8vMTI3LjAuMC4xOjUwMDAiLCJleHAiOjE3MzU4MzUzMzQsImlhdCI6MTczNTgzMzUzNCwibm9uY2UiOiJnQzNHOGxTWFYyMnRYRlRta29maUgySXJhbGFidF93bkNMeThDX1ZuMUVvIiwianRpIjoiZWRlMDdkNzktZTJkYi00ZDU1LTg4MTctZDY0YzU4YTdlMmEwIiwic2lkIjoibVkzT2RVTm8wcFhibE9kUDdRR2h5RDA1ZTY4N1BHd2NBOFZTTkFDWFF2USJ9.PGRTHb4bXZsJqxiYq0LA5MHX4-tPAvjHP4OXe9KDFnT4Gtx3q_3CUfVthEwaxN13lx4FdW_nCt0ZNvbmZ6sSYi_nZCIbVBr5f1ExH-H0hZ8iQyQK0a4m6x5HKgo_3z7UK7jo94QUVi294VK4i6mRvkjG0xC2O6RnhxH9-2bVX0JIKYju5kF1rWrQWazfFUgRaEgZACMuBFqVDwj-klerTJqqvKXHfnM2qiD3aX4aeOSFj-pzON23SxrYwAGO1188i05DEWHYpaYZstlZd1XE4k2_a2_PgIUdMj3O8iqpYJFOp4w_mOtKUC760vGdLmQeJGSVMxYKIGbHcUxD_lFySQ",
    "token_type": "Bearer",
    "expires_in": 300
}
```
- Solo debemos tener en cuenta el access_token por lo que lo copiamos sin comillas y lo usamos para nusetra solciitud en el BFF
- Al usar el BFF primero vamos a encriptar un codigo a modo de prueba para que se pueda buscar ya que debido a los requerimientos el código se envia encriptado
- Usamos Postman con solicitud POST y usamos la siguiente url http://127.0.0.1:7000/resources/encriptar/124  El codigo a encriptar será 124
- Nos dirigimos a Authorization y en Auth Type seleccionamos Bearer Token y pegamos el token generado previamente
- La respuesta será el código único del cliente encriptado que usaremos para hacer la búsqueda en nuestro endpoint de cliente y productos en este caso al ser 124 se ecnripto de esta manera : 2POIebdCS7yxe3Xxvrm5dg==.
- En el endpoint de datos usaremos la siguiente url http://127.0.0.1:7000/resources/clienteinfo/2POIebdCS7yxe3Xxvrm5dg==  y nos dirigimos a Authorization y en Auth Type seleccionamos Bearer Token y pegamos el token generado previamente
- Finalmente la consulta nos devolverá lo requerido por el reto y generará un Request-ID que será enviado a nuestros microservicios de cliente y producto para un mayor control de solicitudes.
```json
{
    "client": {
        "id": 1,
        "nombres": "Juan Daniel",
        "apellidos": "Alvarado Diaz",
        "tipoDoc": "DNI",
        "numeroDoc": "74823099"
    },
    "products": [
        {
            "id": 1,
            "tipoProducto": "Cuenta",
            "nombre": "Cuenta de ahorros",
            "saldo": 234.12
        },
        {
            "id": 2,
            "tipoProducto": "Tarjeta",
            "nombre": "Credito visa",
            "saldo": 5000.0
        }
    ]
}
```
## Tener en cuenta que la parte de OAuth los microservicios estan en la carpeta retoapi y test2


