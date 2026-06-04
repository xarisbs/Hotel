export function formatPen(amount: number | undefined | null): string {
  if (amount == null) {
    return 'S/ 0.00';
  }
  return new Intl.NumberFormat('es-PE', {
    style: 'currency',
    currency: 'PEN',
    minimumFractionDigits: 2,
  }).format(Number(amount));
}

export function formatDate(iso: string | undefined): string {
  if (!iso) {
    return '—';
  }
  const [y, m, d] = iso.split('-');
  const months = [
    'ene', 'feb', 'mar', 'abr', 'may', 'jun',
    'jul', 'ago', 'sep', 'oct', 'nov', 'dic',
  ];
  return `${parseInt(d, 10)} ${months[parseInt(m, 10) - 1]} ${y}`;
}

export function estadoBadgeClass(estado: string | undefined): string {
  if (!estado) {
    return 'badge-pendiente';
  }
  return `badge-${estado.toLowerCase()}`;
}

export function estadoLabel(estado: string | undefined): string {
  const labels: Record<string, string> = {
    DISPONIBLE: 'Disponible',
    OCUPADA: 'Ocupada',
    LIMPIEZA: 'Limpieza',
    MANTENIMIENTO: 'Mantenimiento',
    FUERA_SERVICIO: 'Fuera de servicio',
    PENDIENTE: 'Pendiente',
    CONFIRMADA: 'Confirmada',
    CHECK_IN: 'Check-in',
    CHECK_OUT: 'Check-out',
    CANCELADA: 'Cancelada',
  };
  return labels[estado ?? ''] ?? estado ?? '—';
}

export function roomImageGradient(id: number | undefined, tipo?: string): string {
  const palettes = [
    'linear-gradient(145deg, #1f4e79 0%, #2a6ba3 50%, #d4af37 100%)',
    'linear-gradient(145deg, #163a5c 0%, #1f4e79 60%, #8b7355 100%)',
    'linear-gradient(145deg, #2c5282 0%, #4a7ab5 50%, #c9a227 100%)',
    'linear-gradient(145deg, #1a365d 0%, #2d4a6f 40%, #b8942e 100%)',
    'linear-gradient(145deg, #234e70 0%, #3d6a8f 55%, #d4af37 100%)',
  ];
  const idx = (id ?? 0) % palettes.length;
  if (tipo?.toLowerCase().includes('suite')) {
    return palettes[4];
  }
  if (tipo?.toLowerCase().includes('doble')) {
    return palettes[2];
  }
  return palettes[idx];
}

export function initials(name: string): string {
  return name
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0]?.toUpperCase() ?? '')
    .join('');
}
