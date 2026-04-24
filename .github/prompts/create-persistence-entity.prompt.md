
---

## `.github/prompts/create-persistence-entity.prompt.md`

```md
# Crear entidad de persistencia

Genera o completa una entidad de persistencia para este repositorio.

## Objetivo

Crear una clase técnica de persistencia en `infrastructure/output/persistence` separada del modelo de dominio.

## Instrucciones

- Respeta la arquitectura hexagonal del repositorio.
- Crea la clase en `infrastructure/output/persistence`.
- La clase puede usar anotaciones JPA si son necesarias.
- No mezcles esta clase con el modelo de dominio.
- No coloques aquí lógica de controlador ni lógica HTTP.
- No coloques aquí reglas de negocio complejas.
- Mantén alineación con el contrato OpenAPI cuando corresponda, pero separando transporte, dominio y persistencia.
- Deriva el nombre base del recurso desde el contrato OpenAPI.
- Nombra la entidad como `<Resource>Entity`.
- Si el contrato define un identificador claro, reflejarlo en la entidad.
- No convertir la entidad de persistencia en modelo de dominio ni en DTO.

## Regla de persistencia limpia

- La entidad puede usar `jakarta.persistence`.
- La entidad no debe importar clases generadas por OpenAPI.
- La entidad no debe importar DTOs internos.
- La entidad no debe contener lógica HTTP.
- La entidad no debe reemplazar al modelo de dominio.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una entidad equivalente para el mismo recurso.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Resultado esperado

- Entidad ubicada en `infrastructure/output/persistence`.
- Nombre consistente con el recurso principal.
- Uso correcto de JPA si aplica.
- Sin mezcla con controlador.
- Sin mezclar responsabilidades de dominio.