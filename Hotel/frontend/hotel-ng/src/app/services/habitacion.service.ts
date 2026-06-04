import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import { Habitacion } from '../models/habitacion.model';

@Injectable({ providedIn: 'root' })
export class HabitacionService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/habitaciones`;

  list(): Observable<Habitacion[]> {
    return this.http.get<Habitacion[]>(this.base);
  }

  detalle(id: number): Observable<Habitacion> {
    return this.http.get<Habitacion>(`${this.base}/detalle/${id}`);
  }

  create(habitacion: Habitacion): Observable<Habitacion> {
    return this.http.post<Habitacion>(this.base, habitacion);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
