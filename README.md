# Sistema de Gestión de Préstamos Bancarios

## Sobre el proyecto

Desarrollé esta API REST con Spring Boot para gestionar solicitudes de préstamos bancarios, permitiendo a los usuarios solicitar préstamos y consultar su estado, y a los administradores aprobar o rechazar dichas solicitudes.

## ¿Por qué elegí esta arquitectura?

Decidí usar una arquitectura en capas (Controller → Service → Repository) con inyección de dependencias por constructor, en lugar de algo como hexagonal o DDD. La razón es que el dominio de este proyecto (gestión de préstamos) no tiene la complejidad ni la necesidad de escalabilidad que justificaría meter esa capa extra de abstracción. Prefiero usar el patrón correcto para el problema, no el más complejo posible.

Eso sí, dentro de esa arquitectura simple aplico buenas prácticas: cada capa tiene una responsabilidad clara, las dependencias se inyectan por interfaz (gracias a Spring Data JPA), y separo la lógica de negocio del controlador.

## Decisiones técnicas que tomé

- **Spring Data JPA + H2**: usé H2 en memoria para agilizar el desarrollo y las pruebas. La configuración está lista para cambiar a PostgreSQL o MySQL solo modificando el `application.properties`.

- **Caché con `@Cacheable` / `@CacheEvict`**: implementé caché en las consultas de préstamos por usuario, ya que son las que más se repiten. Cuando se cambia el estado de un préstamo, invalido la caché con `@CacheEvict` para no servir datos desactualizados.

- **Transacciones con `@Transactional`**: lo aplico en el método `cambiarEstado`, porque ahí se hace una lectura, una modificación y un guardado que deben ejecutarse como una sola unidad atómica. Si algo falla a mitad de camino, no quiero un estado inconsistente en la base de datos.

- **Validaciones con Hibernate Validator**: agregué `@NotNull` y `@Min` en los campos de `Prestamo` (monto y plazo), activadas con `@Valid` en el controller, para no depender solo de validación manual.

- **Manejo global de errores**: centralicé el manejo de excepciones con `@RestControllerAdvice`, así la API devuelve códigos HTTP claros (404 cuando no encuentra un recurso, 500 para errores inesperados) en vez de stacktraces genéricos.

- **Spring Security**: dejé configurado el filtro para que el endpoint de cambio de estado solo pueda ser usado por el rol ADMIN. Por el tiempo que tenía, no llegué a conectar esto con un `UserDetailsService` real contra la tabla de usuarios — lo dejo documentado como siguiente paso.

- **Frontend en React**: lo hice con datos simulados (mock) en el estado de React, ya que el requerimiento permitía esto cuando no se alcanza a conectar con el backend en el tiempo dado. La estructura ya está pensada para reemplazar esos datos por llamadas reales a esta API.


## Cómo correr el proyecto

```bash
./gradlew bootRun
```

La API queda disponible en `http://localhost:8080/api/prestamos`
