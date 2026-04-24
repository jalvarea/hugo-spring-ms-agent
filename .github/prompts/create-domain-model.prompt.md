# Crear modelo de dominio

Genera o completa un modelo de dominio para este repositorio.

## Objetivo

Crear una clase de dominio alineada con la arquitectura hexagonal del proyecto.

## Instrucciones

- Respeta las instrucciones del repositorio y las instrucciones por ruta.
- Crea la clase en `domain/model`.
- La clase debe representar un modelo de dominio, no una entidad técnica.
- No uses anotaciones JPA.
- No dependas de infraestructura.
- Usa código claro, mantenible y coherente con Spring Boot MVC.
- Mantén consistencia con el contrato OpenAPI proporcionado cuando aplique.
- Deriva el nombre base del recurso desde el contrato OpenAPI.
- Nombra el modelo de dominio como `<Resource>`.
- No agregar sufijos como DTO, Entity o Model al modelo de dominio salvo que la convención existente del repositorio lo exija.
- Mantener el modelo desacoplado de JPA, HTTP y OpenAPI.

## Regla de dominio limpio

- El dominio no debe importar clases generadas por OpenAPI.
- El dominio no debe importar DTOs, controllers, JPA ni clases de infraestructura.
- Si el contrato define enums, el dominio puede representarlos como `String` o como enum propio del dominio, pero nunca como enum generado por OpenAPI.
- El dominio debe permanecer independiente del contrato de transporte.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe un modelo de dominio equivalente para el mismo recurso.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Resultado esperado

- Clase ubicada en `domain/model`.
- Nombre consistente con el recurso principal.
- Sin dependencias de persistencia.
- Sin anotaciones HTTP o REST.
- Sin imports de clases OpenAPI generadas.
- Con atributos y comportamiento apropiado para dominio.