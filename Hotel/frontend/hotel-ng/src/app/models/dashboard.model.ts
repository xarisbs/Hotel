export interface DashboardPrincipal {
  habitacionesDisponibles: number;
  habitacionesOcupadas: number;
  reservasActivas: number;
  ingresosMes: number;
  clientesRegistrados: number;
}

export interface DashboardEjecutivo extends DashboardPrincipal {
  totalHuespedes?: number;
  reservasPendientes?: number;
  ingresosTotales?: number;
  totalHabitaciones?: number;
  habitacionesPorEstado?: Record<string, number>;
  porcentajeOcupacion?: number;
}

export interface ReportesEjecutivos {
  ingresosPorMes: Record<string, number>;
  reservasPorMes: Record<string, number>;
  habitacionesMasReservadas: { numeroHabitacion: string; totalReservas: number }[];
  clientesFrecuentes: { nombreHuesped: string; totalReservas: number; montoAcumulado: number }[];
  habitacionesOcupadas: number;
  habitacionesDisponibles: number;
  tasaOcupacion: number;
}
