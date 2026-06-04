export type MetodoPago = 'EFECTIVO' | 'TARJETA' | 'YAPE' | 'PLIN' | 'TRANSFERENCIA';
export type EstadoPago = 'PENDIENTE' | 'PAGADO' | 'ANULADO';

export interface Pago {
  id?: number;
  reservaId: number;
  numeroComprobante?: string;
  monto: number;
  metodoPago: MetodoPago;
  estado?: EstadoPago;
  fechaPago?: string;
}
