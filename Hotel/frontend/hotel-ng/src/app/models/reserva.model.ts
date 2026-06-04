export type EstadoReserva =
  | 'PENDIENTE'
  | 'CONFIRMADA'
  | 'CHECK_IN'
  | 'CHECK_OUT'
  | 'CANCELADA';

export interface Reserva {
  id?: number;
  idHuesped?: number;
  nombreHuesped?: string;
  idHabitacion: number;
  numeroHabitacion?: string;
  fechaCheckIn: string;
  fechaCheckOut: string;
  cantidadHuespedes?: number;
  observaciones?: string;
  noches?: number;
  total?: number;
  estado?: EstadoReserva;
  fechaCreacion?: string;
}

export interface ReservaRequest {
  idHuesped?: number;
  nombreHuesped?: string;
  idHabitacion: number;
  fechaCheckIn: string;
  fechaCheckOut: string;
  cantidadHuespedes: number;
  observaciones?: string;
}

export interface ActualizarEstadoRequest {
  estado: EstadoReserva;
}
