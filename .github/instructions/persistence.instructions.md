---
applyTo: "src/main/java/**/infrastructure/output/persistence/**/*.java"
---

Para el código de persistencia en este repositorio:

- Mantener las responsabilidades de persistencia dentro de este paquete.
- No colocar aquí lógica de controladores ni lógica HTTP.
- No colocar aquí lógica de orquestación de negocio.
- Las implementaciones de persistencia deben soportar los casos de uso de la aplicación, no definirlos.
- Mantener separadas las responsabilidades de mapeo cuando sea posible y usar el paquete `infrastructure.mapper`.
- Seguir la arquitectura seleccionada del repositorio y sus convenciones de nombres.
- Si se solicita integración cliente saliente, no confundir el código HTTP saliente con código de persistencia.
- Derivar el nombre base del recurso desde el contrato OpenAPI.
- Para recursos persistibles, usar la convención `<Resource>Entity`.
- Mantener correspondencia clara entre modelo de dominio, entidad de persistencia y mapper del mismo recurso.
- No convertir entidades de persistencia en modelos de dominio ni en DTOs.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una entidad o adaptador equivalente para el mismo recurso.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.