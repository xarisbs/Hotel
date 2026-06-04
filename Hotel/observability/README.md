# Observabilidad — Hotel

Stack: **Prometheus**, **Loki**, **Promtail** y **Grafana**.

## Arranque rápido (DEV)

```bash
cd "/Users/macbook/Mi Proyecto/Hotel/observability"
docker compose -f docker-compose-dev.yml up -d
```

## Servicios monitoreados

| Servicio | Log file | Puerto métricas |
|----------|----------|----------------:|
| gateway | `infra/gateway/logs/` | 7091 |
| tipo-habitacion | `services/tipo-habitacion/logs/tipo-habitacion.log` | 8081 |
| habitacion | `services/habitacion/logs/habitacion.log` | 9091 |
| reserva | `services/reserva/logs/reserva.log` | 9095 |
| facturacion | `services/facturacion/logs/facturacion.log` | 6060 |
| reportes | `services/reportes/logs/reportes.log` | 9070 |
| auth | `services/auth/logs/` | 8041 |

## URLs (DEV)

| Recurso | URL |
|---------|-----|
| Grafana | http://localhost:13000 (admin / admin) |
| Prometheus | http://localhost:19090/targets |
| Loki | http://localhost:13100 |

## LogQL útil

```logql
{service="habitacion"}
{service="tipo-habitacion"}
{service="reserva"}
{service=~"gateway|habitacion|reserva|facturacion"}
```
