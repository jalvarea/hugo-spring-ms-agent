---
applyTo: "src/main/java/**/infrastructure/input/rest/**/*.java"
---

Para los controladores REST de este repositorio:

- Usar únicamente Spring MVC.
- Mantener los controladores ligeros.
- Los controladores deben validar y delegar, no implementar reglas de negocio.
- Delegar el comportamiento de negocio a la capa `application`.
- No inyectar clases de persistencia directamente en los controladores.
- Mantener el manejo de errores alineado con el paquete `infrastructure.input.rest.error`.
- Asegurar que los endpoints, modelos de request y modelos de response estén alineados con el contrato OpenAPI proporcionado.
- No generar operaciones en controladores que no estén definidas en el contrato, salvo que se solicite explícitamente.

## Regla contract-first obligatoria

- Antes de crear un controlador, buscar la interfaz generada por OpenAPI en `com.test.services.server`.
- Si existe una interfaz generada, crear una clase `<Resource>ControllerImpl` que implemente esa interfaz.
- El controlador concreto debe implementar la interfaz generada por OpenAPI.
- Los métodos del controlador deben usar `@Override`.
- Respetar exactamente las firmas de la interfaz generada.
- Usar modelos request/response generados desde `com.test.services.server.models`.

## Prohibiciones

- No crear endpoints manuales si existe una interfaz generada por OpenAPI.
- No duplicar rutas usando `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping` o `@DeleteMapping` cuando la interfaz OpenAPI ya define esas operaciones.
- No crear DTOs propios como `<Resource>RequestDto`, `<Resource>ResponseDto` o `<Resource>Dto` si ya existen modelos generados por OpenAPI.
- No importar paquetes inventados como `com.test.services.rest.server`.
- No inyectar repositorios ni entidades en el controlador.

## Regla de imports contract-first

- En controladores REST, importar la interfaz generada por OpenAPI desde `com.test.services.server`.
- Importar los modelos generados desde `com.test.services.server.models`.
- No importar DTOs propios si existen modelos OpenAPI equivalentes.