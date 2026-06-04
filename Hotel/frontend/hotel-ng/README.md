# Hotel NG — Frontend Angular 21

Panel administrativo del sistema hotelero. Consume **solo el API Gateway** (`http://localhost:7091`), nunca los microservicios directamente.

## Requisitos

- Node.js 20+ y npm
- Backend levantado (Config → Eureka → MS → Gateway)

Instalar Angular CLI (opcional):

```bash
npm install -g @angular/cli@21
```

## Arranque

```bash
cd frontend/hotel-ng
npm install
npm start
```

Abre **http://localhost:4200**

## Arquitectura (curso DIST10–11)

```text
Angular 21 → Gateway (7091) + JWT Interceptor → Microservicios
```

| Pieza | Archivo |
|-------|---------|
| Login | `src/app/pages/login/` |
| JWT Interceptor | `src/app/core/auth/auth.interceptor.ts` |
| Auth Guard | `src/app/core/auth/auth.guard.ts` |
| Token storage | `src/app/core/auth/token.storage.ts` |

## Pantallas

- **Login** — `POST /auth/login`
- **Dashboard** — KPIs ejecutivos (`/api/v1/reportes/dashboard/ejecutivo`)
- **Huéspedes** — CRUD clientes
- **Tipos habitación** — equivalente a *catálogo* del docente
- **Habitaciones** — equivalente a *producto* + botón detalle Feign

## Usuarios

| Usuario | Contraseña | Puede |
|---------|------------|-------|
| admin | admin123 | Todo (crear tipos, habitaciones) |
| recepcion | recepcion123 | Huéspedes, consultas |
