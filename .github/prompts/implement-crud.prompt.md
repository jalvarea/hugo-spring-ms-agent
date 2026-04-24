# Implementar recurso CRUD completo

Implementa el recurso principal del microservicio a partir del contrato OpenAPI existente, siguiendo estrictamente las reglas del repositorio.

## Objetivo

A partir del contrato OpenAPI:

- Derivar el recurso principal.
- Implementar completamente el flujo CRUD.
- Crear o completar todas las clases necesarias.
- Dejar el código listo para compilar y ejecutar.
- Mantener el enfoque contract-first.

## Paso 1: Ubicación del contrato

- El contrato OpenAPI fuente debe estar en `src/main/resources/openapi.json`.
- Usar este archivo para derivar:
  - recurso principal
  - endpoints
  - request
  - response
  - path params
  - operationId
  - códigos HTTP
- No usar archivos generados en `target` como fuente principal de verdad.

## Paso 2: Paquetes del código generado por OpenAPI

Las clases generadas por OpenAPI se encuentran en:

- Interfaces API generadas: `com.test.services.server`
- Modelos request/response generados: `com.test.services.server.models`

Reglas:

- No usar paquetes como `com.test.services.rest.server`.
- Antes de crear cualquier DTO o modelo de transporte, buscar en `com.test.services.server.models`.
- Antes de crear cualquier controller, buscar interfaz en `com.test.services.server`.
- Si existen modelos como `CustomerRequest` y `CustomerResponse`, usarlos directamente.
- No crear modelos duplicados.

## Paso 3: Derivar el recurso

- Derivar el nombre base del recurso desde el schema principal terminado en `Request`.
- Ejemplo: `CustomerRequest` -> `Customer`.
- Si no existe `*Request`, usar `*Response`.
- Si tampoco existe, usar el `tag` principal del OpenAPI en singular.

Este nombre será referido como `<Resource>`.

## Paso 4: Validar código existente

Antes de crear clases nuevas:

- Buscar si ya existen clases relacionadas con `<Resource>`.
- Buscar si ya existen modelos generados por OpenAPI.
- Buscar si ya existe una interfaz generada por OpenAPI.
- Si existen, completarlas en lugar de duplicarlas.
- No crear variantes innecesarias.

Ejemplo de duplicación prohibida:

- `CustomerService` y `CustomerApplicationService`
- `CustomerController` y `CustomerControllerImpl`
- `CustomerRequestDto` y `CustomerRequest`
- `CustomerResponseDto` y `CustomerResponse`

## Paso 5: Crear o completar la familia de clases

Asegurar que exista una única familia coherente.

### Dominio

- `domain/model/<Resource>.java`
- Modelo de dominio puro.
- Sin anotaciones JPA.
- Sin dependencias de infraestructura.
- Sin imports de clases generadas por OpenAPI.
- Si el contrato define enums, el dominio puede usar `String` o enum propio, pero no enum generado por OpenAPI.

### Aplicación

- `application/service/<Resource>ApplicationService.java`
- Orquesta la lógica del caso de uso.
- No contiene lógica HTTP.
- Usa dominio, mapper y persistencia.

### Persistencia

- `infrastructure/output/persistence/<Resource>Entity.java`
- Usar JPA si aplica.
- No mezclar con dominio.
- No importar modelos OpenAPI.

### Mapper

- `infrastructure/mapper/<Resource>Mapper.java`
- Convertir explícitamente entre:
  - Modelo OpenAPI request -> dominio
  - Dominio -> modelo OpenAPI response
  - Entidad de persistencia -> dominio
  - Dominio -> entidad de persistencia

Ejemplo:

- `CustomerRequest` -> `Customer`
- `Customer` -> `CustomerResponse`
- `CustomerEntity` -> `Customer`
- `Customer` -> `CustomerEntity`

### Controller REST

- `infrastructure/input/rest/<Resource>ControllerImpl.java`
- Implementar la interfaz generada por OpenAPI si existe.
- Importar la interfaz desde `com.test.services.server`.
- Importar modelos desde `com.test.services.server.models`.
- Delegar al `<Resource>ApplicationService`.
- No incluir lógica de negocio.

## Paso 6: Regla contract-first para controller

- Antes de crear un controller, buscar la interfaz generada por OpenAPI.
- Si existe, implementar esa interfaz.
- No duplicar endpoints manualmente con anotaciones Spring MVC.
- No crear DTOs propios para request/response cuando ya existan modelos generados por OpenAPI.
- El controller debe delegar al application service y respetar las firmas de la interfaz generada.
- Los métodos deben usar `@Override`.

Prohibido si existe interfaz OpenAPI:

- `@RequestMapping`
- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@DeleteMapping`

## Paso 7: Regla obligatoria sobre DTOs

- No crear DTOs propios como:
  - `<Resource>RequestDto`
  - `<Resource>ResponseDto`
  - `<Resource>Dto`
  - `Create<Resource>Request`
  - `Update<Resource>Request`
  - `<Resource>Payload`
- Si OpenAPI Generator ya generó modelos request/response, esos modelos son los únicos DTOs permitidos para el adaptador REST.
- Usar directamente los modelos generados por OpenAPI:
  - `CustomerRequest`
  - `CustomerResponse`
- No crear clases en `application/dto` para duplicar request/response del contrato.

## Paso 8: Regla de conversión de enums OpenAPI

- No asignar directamente enums generados por OpenAPI a campos `String` del dominio.
- Si un modelo OpenAPI tiene un enum y el dominio usa `String`, convertir usando:
  - `enumValue.getValue()`
  - o `enumValue.toString()`
- Si un modelo OpenAPI response espera un enum y el dominio usa `String`, convertir usando:
  - `EnumType.fromValue(value)`
- El dominio no debe depender de enums generados por OpenAPI.

Ejemplo:

```java
request.getGender() != null ? request.getGender().getValue() : null