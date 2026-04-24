---
applyTo: "src/main/java/**/*.java"
---

Al generar código Java en este repositorio:

- Respetar la estructura de paquetes de la arquitectura hexagonal existente.
- Mantener las clases de dominio desacopladas del framework siempre que sea posible.
- No colocar anotaciones JPA en los modelos de dominio.
- Colocar las reglas de negocio en `application` o `domain`, no en los controladores.
- No acceder a repositorios de persistencia directamente desde controladores REST.
- Mantener los mapeadores en el paquete `infrastructure.mapper`.
- Mantener las implementaciones relacionadas con persistencia dentro de `infrastructure.output.persistence`.
- Mantener los puntos de entrada REST dentro de `infrastructure.input.rest`.
- Usar inyección por constructor.
- Preferir código explícito, legible y fácil de mantener.
- Mantener el código alineado únicamente con Spring MVC.
- Asegurar que el código relacionado con API sea consistente con el contrato OpenAPI proporcionado.
- Derivar el nombre base del recurso desde el contrato OpenAPI usando la convención `*Request` -> `<Resource>`.
- Si existe una interfaz generada por OpenAPI, crear implementaciones concretas en `src/main/java` sin modificar los artefactos generados.
- Mantener una sola familia de clases por recurso y evitar duplicados.
- Reutilizar `operationId` para nombres de métodos cuando sea razonable.
- No crear lógica mantenible en `target`.

## Dominio limpio

- El dominio no debe importar clases generadas por OpenAPI.
- El dominio no debe importar DTOs, controllers, JPA ni clases de infraestructura.
- Si el contrato define enums, el dominio puede representarlos como `String` o como enum propio del dominio, pero nunca como enum generado por OpenAPI.
- El dominio debe permanecer independiente del contrato de transporte.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una clase equivalente para el mismo recurso.
- Buscar si ya existen modelos generados por OpenAPI en `com.test.services.server.models`.
- Buscar si ya existe una interfaz generada por OpenAPI en `com.test.services.server`.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.