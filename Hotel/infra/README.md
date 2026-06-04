# Infraestructura Hotel

Componentes compartidos del sistema hotelero.

| Servicio | Puerto DEV | Puerto PROD (Docker) |
|----------|------------|----------------------|
| Config Server | 7071 | 7072 |
| Eureka | 7081 | 7082 |
| Gateway | 7091 | 7092 |

## Arranque DEV

Desde la raíz del proyecto (`Hotel/`):

```bash
cd infra/config-server && ./mvnw spring-boot:run
cd infra/registry-server && ./mvnw spring-boot:run
cd infra/gateway && ./mvnw spring-boot:run
```

Configuraciones externas en `infra/config-repo/`.

## Arranque PROD (Docker)

```bash
cd infra
docker compose up --build -d
```

Red compartida: `ms-net`.

Documentación completa del sistema: ver [README.md](../README.md) en la raíz del proyecto.
