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

