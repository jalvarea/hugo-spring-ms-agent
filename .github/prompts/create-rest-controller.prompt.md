# Crear controlador REST MVC

Genera o completa un controlador REST para este repositorio.

## Objetivo

Crear un controlador en `infrastructure/input/rest` alineado con MVC, contrato OpenAPI y arquitectura hexagonal.

## Instrucciones

- Usa únicamente Spring MVC.
- Crea la clase en `infrastructure/input/rest`.
- El controlador debe ser ligero: validar y delegar.
- No coloques reglas de negocio en el controlador.
- No inyectes clases de persistencia directamente.
- Alinea endpoints, requests y responses con el contrato OpenAPI.
- No generes operaciones fuera del contrato salvo que se solicite explícitamente.
- Usa manejo de errores consistente con `infrastructure/input/rest/error`.
- Deriva el nombre base del recurso desde el contrato OpenAPI usando la convención `*Request` -> `<Resource>`.
- Si existe una interfaz generada por OpenAPI para el endpoint, crea una implementación concreta llamada `<Resource>ControllerImpl`.
- Delega al servicio de aplicación del mismo recurso.
- No inventar nombres alternativos si el recurso ya puede inferirse claramente desde el contrato.

## Regla contract-first obligatoria

- Antes de crear un controlador, buscar la interfaz generada por OpenAPI en `com.test.services.server`.
- El controlador concreto debe implementar la interfaz generada por OpenAPI.
- Los métodos del controlador deben usar `@Override`.
- El controlador debe respetar exactamente las firmas de la interfaz generada.
- Usar directamente modelos generados desde `com.test.services.server.models`.

## Prohibiciones

- No crear endpoints manuales si existe una interfaz generada por OpenAPI.
- No duplicar rutas usando:
  - `@RequestMapping`
  - `@GetMapping`
  - `@PostMapping`
  - `@PutMapping`
  - `@DeleteMapping`
  cuando la interfaz OpenAPI ya define esas operaciones.
- No crear DTOs propios como:
  - `<Resource>RequestDto`
  - `<Resource>ResponseDto`
  - `<Resource>Dto`
- No importar paquetes inventados como `com.test.services.rest.server`.
- No inyectar repositorios ni entidades en el controlador.

## Regla de imports contract-first

- En controladores REST, importar la interfaz generada por OpenAPI desde `com.test.services.server`.
- Importar los modelos generados desde `com.test.services.server.models`.
- No importar DTOs propios si existen modelos OpenAPI equivalentes.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una clase equivalente para el mismo recurso.
- Buscar si ya existen modelos generados por OpenAPI.
- Buscar si ya existe una interfaz generada por OpenAPI.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Resultado esperado

- Controlador MVC.
- Nombre consistente con el recurso principal.
- Implementa interfaz generada por OpenAPI.
- Sin WebFlux.
- Sin lógica de negocio compleja.
- Delegación a `application/service`.
- Consistencia con contrato OpenAPI.
- Sin DTOs duplicados.