import { TipoHabitacion } from './tipo-habitacion.model';

export type EstadoHabitacion =
  | 'DISPONIBLE'
  | 'OCUPADA'
  | 'LIMPIEZA'
  | 'MANTENIMIENTO'
  | 'FUERA_SERVICIO';

export interface Habitacion {
  id?: number;
  numero: string;
  piso?: number;
  descripcion?: string;
  idTipoHabitacion: number;
  precioPorNoche: number;
  estado?: EstadoHabitacion;
  tipoHabitacion?: TipoHabitacion;
}
