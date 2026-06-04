#!/usr/bin/env bash
# Recrea todas las bases MySQL de desarrollo del hotel (volúmenes nuevos).
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"

SERVICES=(
  "services/auth"
  "services/cliente"
  "services/tipo-habitacion"
  "services/habitacion"
  "services/reserva"
  "services/facturacion"
  "services/reportes"
)

echo "==> Deteniendo contenedores y eliminando volúmenes antiguos..."
for dir in "${SERVICES[@]}"; do
  compose="${ROOT}/${dir}/docker-compose-dev.yml"
  if [[ -f "$compose" ]]; then
    echo "    - ${dir}"
    docker compose -f "$compose" down -v --remove-orphans 2>/dev/null || true
  fi
done

echo "==> Levantando MySQL con bases nuevas db_hotel_*..."
for dir in "${SERVICES[@]}"; do
  compose="${ROOT}/${dir}/docker-compose-dev.yml"
  if [[ -f "$compose" ]]; then
    docker compose -f "$compose" up -d
  fi
done

echo ""
echo "Bases de datos creadas:"
echo "  db_hotel_auth          → localhost:3341"
echo "  db_hotel_clientes      → localhost:3396"
echo "  db_hotel_tipos         → localhost:3381"
echo "  db_hotel_habitaciones  → localhost:3391"
echo "  db_hotel_reservas      → localhost:3393"
echo "  db_hotel_facturacion   → localhost:3394"
echo "  db_hotel_reportes      → localhost:3395"
echo ""
echo "Espera ~20s a que MySQL inicie y luego arranca los microservicios."
