PruebaFrontend - Sistema de Gestión de Préstamos


Interfaz desarrollada en **React + Vite** que permite a los usuarios solicitar préstamos y consultar su estado, y a los administradores aprobar o rechazar solicitudes.

## Decisiones técnicas

Datos mock en memoria (useState)**: dado que el requerimiento permite usar datos simulados sin depender del backend cuando el tiempo es limitado, se optó por manejar usuarios y préstamos como datos mock en el estado de React. Esto permite demostrar el flujo completo (login, roles, CRUD de préstamos) sin necesidad de conexión real.

Simulación de roles (USER / ADMIN)**: el login valida contra un arreglo de usuarios predefinido y redirige a la vista correspondiente según el rol.

Validaciones en formularios**: se usa el atributo `required` y `min="1"` en los inputs, además de validación adicional en el manejador de envío para evitar montos inválidos.

Arquitectura preparada para conectar al backend real**: el array `USUARIOS` puede reemplazarse por una llamada a `/api/auth/login` (JWT), y el estado `prestamos` por llamadas a los endpoints ya construidos en el backend (`/api/prestamos`).


## Credenciales de prueba

| Rol   | Email              | Contraseña |
|-------|---------------------|------------|
| User  | usuario@test.com    | 123        |
| Admin | admin@test.com      | 123        |

## Cómo ejecutar

```bash
pnpm install
pnpm dev
```

Abrir `http://localhost:5173`

