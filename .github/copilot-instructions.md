# Instrucciones del repositorio para GitHub Copilot

Este repositorio se genera a partir de un arquetipo de Spring Boot para microservicios basados en MVC con arquitectura hexagonal.

## Convenciones generales del proyecto

- Este proyecto usa Spring Boot con paradigma MVC únicamente.
- No generar WebFlux, Mono, Flux ni controladores reactivos.
- Respetar la estructura de paquetes y convenciones de nombres existentes.
- Usar inyección por constructor.
- Generar código listo para producción, con responsabilidades claramente separadas.
- Preferir implementaciones simples y mantenibles sobre abstracciones innecesarias.

## Reglas de arquitectura

Este repositorio sigue una arquitectura hexagonal con los siguientes paquetes principales:

- `application`
  - `dto`
  - `service`
- `domain`
  - `exception`
  - `model`
  - `port/in`
- `infrastructure`
  - `input/rest`
  - `input/rest/error`
  - `mapper`
  - `output/persistence`

Respetar esta estructura al crear o modificar código.

## Dirección de dependencias

- `domain` no debe depender de `infrastructure`.
- `domain` no debe depender de clases generadas por OpenAPI.
- `application` contiene casos de uso y lógica de orquestación del negocio.
- `infrastructure` contiene implementaciones técnicas como controladores REST, mapeadores y adaptadores de persistencia.
- Los controladores no deben contener reglas de negocio.
- La persistencia no debe contener lógica de controladores ni lógica HTTP.

## Reglas MVC

- Usar controladores Spring MVC y manejo estándar de request/response.
- No generar tipos reactivos.
- No introducir dependencias de WebFlux.

## Reglas del contrato OpenAPI

- El contrato OpenAPI proporcionado es siempre la fuente de verdad.
- El contrato fuente debe estar en `src/main/resources/openapi.json`.
- No generar endpoints, modelos de request ni modelos de response que contradigan el contrato OpenAPI.
- Mantener el código relacionado con la API alineado con el contrato.
- No inventar operaciones fuera del contrato, salvo que se solicite explícitamente.
- Usar `operationId`, tags, paths, request, response y códigos HTTP definidos en el contrato.

## Paquetes del código generado por OpenAPI

Las clases generadas por OpenAPI se encuentran en:

- Interfaces API generadas: `com.test.services.server`
- Modelos request/response generados: `com.test.services.server.models`

Reglas obligatorias:

- No usar paquetes inventados como `com.test.services.rest.server`.
- No crear modelos propios si ya existen modelos generados en `com.test.services.server.models`.
- Antes de crear un DTO, buscar si ya existe en `com.test.services.server.models`.
- Antes de crear un controller, buscar si existe una interfaz generada en `com.test.services.server`.

## Regla estable de derivación de nombres

- Derivar el nombre base del recurso desde el schema principal terminado en `Request`.
- Ejemplo: `CustomerRequest` -> `Customer`.
- Si no existe un schema `*Request`, usar el schema principal terminado en `Response`.
- Si tampoco existe, derivar el nombre desde el tag principal del OpenAPI en singular.
- No inventar nombres alternativos si el recurso puede inferirse claramente desde el contrato.

## Regla de familia de clases por recurso

Para cada recurso principal del contrato, mantener una única familia coherente de clases:

- Modelo de dominio: `<Resource>`
- Servicio de aplicación: `<Resource>ApplicationService`
- Controlador REST concreto: `<Resource>ControllerImpl`
- Entidad de persistencia: `<Resource>Entity`
- Mapper: `<Resource>Mapper`

Ejemplo para `CustomerRequest`:

- `Customer`
- `CustomerApplicationService`
- `CustomerControllerImpl`
- `CustomerEntity`
- `CustomerMapper`

## Regla de no duplicación

- Si ya existe una familia parcial de clases para un recurso, completarla en lugar de crear otra con nombres distintos.
- Evitar variantes duplicadas como:
  - `CustomerService` y `CustomerApplicationService`
  - `CustomerController` y `CustomerControllerImpl`
  - `CustomerRequestDto` y `CustomerRequest`
  - `CustomerResponseDto` y `CustomerResponse`
  - `CustomerModel` y `Customer`
- Usar una sola convención por recurso.

## Regla obligatoria sobre DTOs

- No crear DTOs propios como:
  - `<Resource>RequestDto`
  - `<Resource>ResponseDto`
  - `<Resource>Dto`
  - `Create<Resource>Request`
  - `Update<Resource>Request`
  - `<Resource>Payload`
- Si OpenAPI Generator ya generó modelos request/response, esos modelos son los únicos DTOs permitidos para el adaptador REST.
- Usar directamente los modelos generados por OpenAPI, por ejemplo:
  - `CustomerRequest`
  - `CustomerResponse`
- No reemplazar los modelos generados por OpenAPI con DTOs internos.
- No crear clases en `application/dto` para duplicar request/response del contrato.
- El paquete `application/dto` solo debe usarse si se solicita explícitamente un modelo interno distinto al contrato.

## Regla sobre código generado por OpenAPI

- Las interfaces y modelos generados por OpenAPI son artefactos derivados del build.
- No modificar manualmente clases generadas por OpenAPI.
- Las implementaciones concretas deben crearse en código mantenible dentro de `src/main/java`.
- No crear lógica mantenible en `target`.
- Si existe una interfaz generada para el controlador, la implementación concreta debe implementarla.
- Reutilizar `operationId`, tags y schemas del contrato para nombrar métodos cuando sea razonable.

## Regla contract-first para controladores

- No crear endpoints manuales si existe una interfaz generada por OpenAPI.
- No duplicar rutas usando `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping` o `@DeleteMapping` cuando la interfaz OpenAPI ya define esas operaciones.
- El controlador concreto debe implementar la interfaz generada por OpenAPI.
- Los métodos del controlador deben usar `@Override`.
- El controlador debe respetar exactamente las firmas de la interfaz generada.
- El controlador debe usar modelos generados desde `com.test.services.server.models`.

## Reglas de implementación

- El controlador REST debe validar y delegar.
- La lógica de aplicación y orquestación debe ir en `application/service`.
- El modelo de dominio debe permanecer desacoplado de JPA, HTTP y OpenAPI.
- La persistencia debe implementarse en `infrastructure/output/persistence`.
- El mapeo entre DTOs OpenAPI, dominio y persistencia debe ubicarse en `infrastructure/mapper`.
- No acceder directamente a persistencia desde los controladores REST.

## Flujo correcto de implementación contract-first

El flujo correcto debe ser:

1. Controller REST implementa interfaz OpenAPI generada.
2. Controller recibe modelos OpenAPI generados.
3. Controller delega al ApplicationService.
4. ApplicationService usa Mapper para convertir request OpenAPI a dominio.
5. ApplicationService trabaja con dominio.
6. Mapper convierte dominio a entidad para persistencia.
7. Mapper convierte entidad a dominio.
8. Mapper convierte dominio a response OpenAPI.

No usar este flujo:

1. Controller manual con mappings propios.
2. DTOs propios creados por Copilot.
3. Mapper basado en DTOs duplicados.
4. Contrato OpenAPI usado solo como referencia documental.

## Regla de conversión de enums OpenAPI

- No asignar directamente enums generados por OpenAPI a campos `String` del dominio.
- Si un modelo OpenAPI tiene un enum y el dominio usa `String`, convertir usando:
  - `enumValue.getValue()`
  - o `enumValue.toString()`
- Si un modelo OpenAPI response espera un enum y el dominio usa `String`, convertir usando:
  - `EnumType.fromValue(value)`
- El dominio no debe depender de enums generados por OpenAPI.

## Reglas de integración cliente

- Si `isClientWeb` está habilitado, tratar `contentClient` como la fuente de verdad para integraciones salientes.
- Generar código de integración cliente solo cuando se solicite explícitamente.
- No crear código cliente saliente que contradiga el contrato proporcionado.
- Si se crea un adaptador cliente, usar una convención consistente como `<Resource>ClientAdapter` o la convención existente del repositorio.

## Reglas de generación de código

- Mantener los nombres de paquetes alineados con `package_name`.
- Usar `service_name` y `description` de forma consistente cuando aplique en documentación generada.
- Crear solo archivos que encajen con la arquitectura seleccionada y la estructura actual del repositorio.
- Evitar clases duplicadas con responsabilidades similares.
- Agregar o actualizar pruebas cuando se introduzca nuevo comportamiento de negocio.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una clase equivalente para el mismo recurso.
- Buscar si ya existen modelos generados por OpenAPI.
- Buscar si ya existe una interfaz generada por OpenAPI.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Validaciones antes de finalizar

Antes de considerar un cambio como terminado:

- Verificar que los imports estén limpios.
- Verificar que la ubicación del paquete sea correcta.
- Verificar que se respeten los límites de la arquitectura.
- Verificar que MVC se use de forma consistente.
- Verificar que el código basado en contrato coincida con el contrato OpenAPI proporcionado.
- Verificar que no se haya creado lógica mantenible en `target`.
- Verificar que no existan clases duplicadas para el mismo recurso.
- Verificar que no se hayan creado DTOs propios duplicando modelos generados por OpenAPI.