# Auth — Microservicio de autenticación

Emite JWT para el sistema hotelero.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/login` | Login (devuelve `accessToken`) |
| GET | `/auth/instancia` | Info de instancia (balanceo) |

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| admin | admin123 | ADMIN |
| recepcion | recepcion123 | RECEPCION |

## Flujo

1. Cliente → `POST /auth/login` (vía Gateway `7091`)
2. Gateway valida JWT en rutas protegidas
3. Cada microservicio valida JWT como OAuth2 Resource Server

Config JWT en `infra/config-repo/auth-dev.yml` y `gateway-dev.yml` (mismo `secret` e `issuer`).
