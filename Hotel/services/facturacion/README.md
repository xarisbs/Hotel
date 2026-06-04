# Microservicio Facturación

Registra pagos de reservas hoteleras.

## API

- Base: `/api/v1/facturacion`
- Eureka: `facturacion`
- Puerto dev: `6060`

## Métodos de pago

- `EFECTIVO`, `TARJETA`, `TRANSFERENCIA`

## Relación

Cada pago referencia `reservaId`.
