# Crear mapper

Genera o completa un mapper para este repositorio.

## Objetivo

Crear un mapper en `infrastructure/mapper` para convertir entre modelos OpenAPI, modelos de dominio y entidades de persistencia.

## Instrucciones

- Crea la clase o interfaz en `infrastructure/mapper`.
- Mantén separadas las responsabilidades de mapeo.
- No coloques lógica de negocio en el mapper.
- No conviertas el mapper en controlador ni en servicio.
- Si existe una entidad de persistencia y un modelo de dominio, permite la conversión entre ambos.
- Deriva el nombre base del recurso desde el contrato OpenAPI.
- Nombra el mapper como `<Resource>Mapper`.

## Regla obligatoria sobre DTOs

- No crear DTOs propios como:
  - `<Resource>RequestDto`
  - `<Resource>ResponseDto`
  - `<Resource>Dto`
  - `Create<Resource>Request`
  - `Update<Resource>Request`
- Si OpenAPI Generator ya generó modelos request/response, usar esos modelos directamente.
- Importar modelos generados desde `com.test.services.server.models`.
- No crear ni usar DTOs propios si ya existen modelos generados por OpenAPI.

## Regla contract-first para mappers

El mapper debe convertir explícitamente entre:

- Modelo OpenAPI request -> dominio
- Dominio -> modelo OpenAPI response
- Entidad de persistencia -> dominio
- Dominio -> entidad de persistencia

Ejemplo esperado:

- `CustomerRequest` -> `Customer`
- `Customer` -> `CustomerResponse`
- `CustomerEntity` -> `Customer`
- `Customer` -> `CustomerEntity`

## Regla de conversión de enums OpenAPI

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