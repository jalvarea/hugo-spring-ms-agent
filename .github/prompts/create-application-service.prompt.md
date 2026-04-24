# Crear servicio de aplicación

Genera o completa un servicio de aplicación para este repositorio.

## Objetivo

Crear una clase en `application/service` que implemente lógica de aplicación o caso de uso.

## Instrucciones

- Respeta la arquitectura hexagonal del repositorio.
- Crea la clase en `application/service`.
- Coloca aquí la lógica de aplicación y orquestación.
- No conviertas esta clase en controlador REST.
- No coloques aquí lógica HTTP.
- No accedas directamente a infraestructura desde los controladores.
- Usa inyección por constructor.
- Mantén la lógica alineada con el contrato OpenAPI cuando aplique.
- Deriva el nombre base del recurso desde el contrato OpenAPI usando la convención `*Request` -> `<Resource>`.
- Nombra la clase como `<Resource>ApplicationService`.
- Si ya existe una clase equivalente en `application/service`, complétala en lugar de duplicarla.
- El servicio debe orquestar dominio, mapeo y persistencia sin mezclar lógica HTTP.

## Regla contract-first

- El servicio de aplicación no debe crear ni depender de DTOs propios si ya existen modelos OpenAPI generados.
- Si recibe modelos OpenAPI desde el controlador, debe usar el mapper para convertirlos a dominio.
- El servicio debe trabajar internamente con dominio y coordinar persistencia mediante componentes de infraestructura o puertos definidos.
- No debe depender de controllers ni de anotaciones REST.

## Flujo esperado

- Recibir datos desde el controlador.
- Usar `<Resource>Mapper` para convertir OpenAPI request a dominio cuando aplique.
- Ejecutar lógica de aplicación.
- Usar persistencia o adaptador correspondiente.
- Retornar modelo OpenAPI response o dominio transformado por mapper, según la convención existente del proyecto.

## Regla de revisión previa

Antes de crear una clase nueva:

- Buscar si ya existe una clase equivalente para el mismo recurso.
- Completar lo existente antes de crear clases nuevas.
- No generar duplicados por diferencia de sufijo o paquete.

## Resultado esperado

- Clase ubicada en `application/service`.
- Nombre consistente con el recurso principal.
- Responsabilidad de aplicación clara.
- Sin anotaciones REST.
- Sin lógica de persistencia acoplada innecesariamente.