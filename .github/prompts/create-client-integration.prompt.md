# Crear integración cliente saliente

Genera o completa código de integración cliente para este repositorio.

## Objetivo

Crear la integración saliente necesaria cuando `isClientWeb` está habilitado.

## Instrucciones

- Usa `contentClient` como fuente de verdad para la integración saliente.
- Genera solo lo necesario para consumir el cliente definido.
- No mezcles integración cliente con persistencia.
- No coloques este código en `domain/model`.
- Mantén la estructura del proyecto y la arquitectura hexagonal.
- Usa MVC en el proyecto, pero recuerda que esta parte es integración saliente, no controlador de entrada.
- Si se requieren DTOs, ubícalos en la capa apropiada según la convención del repositorio.
- Deriva el nombre base del recurso principal desde `contentClient` o desde el contrato cliente.
- Si se crea un adaptador cliente, nombrarlo con una convención consistente, por ejemplo `<Resource>ClientAdapter` o la convención existente del repositorio.
- No crear integración saliente duplicada si ya existe una implementación equivalente.

## Reglas contract-first para cliente

- Si OpenAPI Generator ya generó cliente o modelos cliente, reutilizarlos.
- No crear modelos duplicados que contradigan `contentClient`.
- No mezclar cliente saliente con controller REST de entrada.
- No usar paquetes inventados para clientes generados.
- Respetar los paquetes generados configurados en el proyecto.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una integración equivalente.
- Buscar si ya existe cliente generado por OpenAPI.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Resultado esperado

- Cliente saliente consistente con `contentClient`.
- Sin mezclar persistencia ni controladores.
- Código alineado con la arquitectura del repositorio.
- Nombres consistentes con el recurso principal.