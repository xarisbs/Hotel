import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import {
  ActualizarEstadoRequest,
  Reserva,
  ReservaRequest,
} from '../models/reserva.model';

@Injectable({ providedIn: 'root' })
export class ReservaService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/reservas`;

  list(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.base);
  }

  findById(id: number): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.base}/${id}`);
  }

  create(request: ReservaRequest): Observable<Reserva> {
    return this.http.post<Reserva>(this.base, request);
  }

  actualizarEstado(id: number, request: ActualizarEstadoRequest): Observable<Reserva> {
    return this.http.patch<Reserva>(`${this.base}/${id}/estado`, request);
  }
}
