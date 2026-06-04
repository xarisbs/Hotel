# Hotel — Microservicios Spring Cloud

Sistema de gestión hotelera basado en microservicios Spring Cloud.

## Estructura del proyecto

```text
Hotel/
├── infra/                  # config-server, registry, gateway
├── services/
│   ├── auth/
│   ├── cliente/            # Huéspedes
│   ├── tipo-habitacion/
│   ├── habitacion/
│   ├── reserva/
│   ├── facturacion/
│   └── reportes/
├── frontend/hotel-ng/      # Angular 21 (DIST10–11)
├── observability/          # Prometheus, Grafana, Loki
└── scripts/
```

## Dominio

| Servicio (Eureka) | Responsabilidad |
|-------------------|-----------------|
| **cliente** | Registro de huéspedes (documento, contacto, nacionalidad) |
| **tipo-habitacion** | Tipos de habitación (Estándar, Suite, etc.) |
| **habitacion** | Habitaciones físicas, tarifa por noche y **estados hoteleros** |
| **reserva** | Reservas con check-in / check-out, validación de huésped y habitación |
| **facturacion** | Pagos asociados a reservas |
| **reportes** | Dashboard ejecutivo, ocupación e ingresos (Feign) |
| **auth** | Autenticación JWT (recepción, admin) |

### Estados hoteleros (habitación)

`DISPONIBLE` · `OCUPADA` · `LIMPIEZA` · `MANTENIMIENTO` · `FUERA_SERVICIO`

Al cambiar el estado de una reserva se sincroniza la habitación:
- **CHECK_IN** → OCUPADA
- **CHECK_OUT** → LIMPIEZA
- **CANCELADA / CONFIRMADA** → DISPONIBLE

## Arquitectura

```text
Cliente → API Gateway → Microservicios → Eureka → Config Server
                ↓
         cliente ←── reserva (Feign, idHuesped)
                ↓
         habitacion ←── reserva (Feign, disponibilidad + estado)
                ↓
         facturacion (reservaId)
                ↓
         reportes (Feign → cliente, habitacion, reserva, facturacion)
                ↓
         Grafana (métricas hotel_* desde reportes)
```

## Infraestructura

```bash
cd infra/config-server && ./mvnw spring-boot:run
cd infra/registry-server && ./mvnw spring-boot:run
cd infra/gateway && ./mvnw spring-boot:run
```

## APIs principales (vía Gateway `7091`)

| Recurso | Ruta |
|---------|------|
| Login | `POST /auth/login` |
| Huéspedes | `/api/v1/clientes/**` |
| Tipos habitación | `/api/v1/tipos-habitacion/**` |
| Habitaciones | `/api/v1/habitaciones/**` |
| Reservas | `/api/v1/reservas/**` |
| Facturación | `/api/v1/facturacion/**` |
| Reportes | `/api/v1/reportes/**` |
| **Dashboard ejecutivo** | `GET /api/v1/reportes/dashboard/ejecutivo` |
| Ocupación por estado | `GET /api/v1/reportes/avanzados/ocupacion` |
| Ingresos totales | `GET /api/v1/reportes/avanzados/ingresos` |

## Usuarios de prueba (auth)

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| admin | admin123 | ADMIN |
| recepcion | recepcion123 | RECEPCION |

## Bases de datos MySQL (dev)

| Base de datos | Servicio | Puerto MySQL |
|---------------|----------|-------------:|
| `db_hotel_auth` | auth | 3341 |
| `db_hotel_clientes` | cliente | 3396 |
| `db_hotel_tipos` | tipo-habitacion | 3381 |
| `db_hotel_habitaciones` | habitacion | 3391 |
| `db_hotel_reservas` | reserva | 3393 |
| `db_hotel_facturacion` | facturacion | 3394 |
| `db_hotel_reportes` | reportes | 3395 |

### Recrear bases desde cero

```bash
chmod +x scripts/reset-db-dev.sh
./scripts/reset-db-dev.sh
```

Espera ~20 s antes de arrancar los microservicios.

## Cómo levantar el proyecto (DEV)

Abre **11 terminales** en este orden:

| # | Componente | Comando |
|---|------------|---------|
| 0 | MySQL (todas las BD) | `./scripts/reset-db-dev.sh` |
| 1 | Config Server | `cd infra/config-server && ./mvnw spring-boot:run` |
| 2 | Eureka | `cd infra/registry-server && ./mvnw spring-boot:run` |
| 3 | Auth | `cd services/auth && ./mvnw spring-boot:run` |
| 4 | **Cliente** | `cd services/cliente && ./mvnw spring-boot:run` |
| 5 | Tipo habitación | `cd services/tipo-habitacion && ./mvnw spring-boot:run` |
| 6 | Habitación | `cd services/habitacion && ./mvnw spring-boot:run` |
| 7 | Reserva | `cd services/reserva && ./mvnw spring-boot:run` |
| 8 | Facturación | `cd services/facturacion && ./mvnw spring-boot:run` |
| 9 | Reportes | `cd services/reportes && ./mvnw spring-boot:run` |
| 10 | Gateway | `cd infra/gateway && ./mvnw spring-boot:run` |

Opcional — observabilidad:

```bash
cd observability && docker compose -f docker-compose-dev.yml up -d
```

- Grafana: http://localhost:13000 (admin / admin) → carpeta **Hotel → Dashboard Ejecutivo**
- Prometheus: http://localhost:19090

Entrada API: **http://localhost:7091**

## Frontend Angular 21 (DIST10–11)

Terminal adicional — requiere Node.js 20+ y npm:

```bash
cd frontend/hotel-ng
npm install
npm start
```

UI: **http://localhost:4200**

| Terminal # | Componente |
|------------|------------|
| 11 | Angular | `cd frontend/hotel-ng && npm start` |

## Flujo de prueba sugerido

1. `POST /auth/login` → obtener JWT (o usar la UI Angular)
2. `POST /api/v1/clientes` → registrar huésped
3. `POST /api/v1/tipos-habitacion` y `POST /api/v1/habitaciones`
4. `POST /api/v1/reservas` con `idHuesped` (nombre se resuelve vía Feign)
5. `PATCH /api/v1/reservas/{id}/estado` → CHECK_IN / CHECK_OUT (actualiza habitación)
6. `POST /api/v1/facturacion` → registrar pago
7. Dashboard en Angular o `GET /api/v1/reportes/dashboard/ejecutivo`

## Cumplimiento curso (DIST01–DIST11)

| Tema | Estado |
|------|--------|
| Arquitectura base + Docker dev/prod | ✅ |
| Config Server + config-repo | ✅ |
| Eureka + escalado manual | ✅ |
| Gateway + balanceo `lb://` | ✅ |
| Feign entre microservicios | ✅ |
| Circuit Breaker (Resilience4j) | ✅ habitacion + reserva |
| Observabilidad (Prometheus/Grafana/Loki) | ✅ |
| JWT (auth + gateway + microservicios) | ✅ |
| Angular 21 + Gateway + JWT | ✅ `frontend/hotel-ng` |
| Kafka (opcional) | — no implementado |

## Demos para sustentación

**Balanceo (apagar/encender 2 instancias):**
```bash
curl http://localhost:7091/api/v1/habitaciones/instancia
```

**Circuit Breaker (detener tipo-habitacion, probar detalle):**
```bash
curl http://localhost:7091/api/v1/habitaciones/detalle/1
# Responde sin tipoHabitacion (fallback)
```

**JWT:**
```bash
curl -s -X POST http://localhost:7091/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Dashboard ejecutivo:**
```bash
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:7091/api/v1/reportes/dashboard/ejecutivo
```

