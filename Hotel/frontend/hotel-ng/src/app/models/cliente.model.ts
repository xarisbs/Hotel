export interface Cliente {
  id?: number;
  nombre: string;
  apellido: string;
  documento: string;
  dni?: string;
  email?: string;
  correo?: string;
  telefono?: string;
  nacionalidad?: string;
  nombreCompleto?: string;
  fechaRegistro?: string;
}

export interface ReservaHistorial {
  id?: number;
  numeroHabitacion?: string;
  fechaCheckIn?: string;
  fechaCheckOut?: string;
  estado?: string;
  total?: number;
  fechaCreacion?: string;
}
