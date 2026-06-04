import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import { TipoHabitacion } from '../models/tipo-habitacion.model';

@Injectable({ providedIn: 'root' })
export class TipoHabitacionService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/tipos-habitacion`;

  list(): Observable<TipoHabitacion[]> {
    return this.http.get<TipoHabitacion[]>(this.base);
  }

  create(tipo: TipoHabitacion): Observable<TipoHabitacion> {
    const form = new FormData();
    form.append('nombre', tipo.nombre);
    form.append('descripcion', tipo.descripcion ?? '');
    form.append('capacidadMaxima', String(tipo.capacidadMaxima));
    if (tipo.precioBase != null) {
      form.append('precioBase', String(tipo.precioBase));
    }
    if (tipo.cantidadCamas != null) {
      form.append('cantidadCamas', String(tipo.cantidadCamas));
    }
    return this.http.post<TipoHabitacion>(this.base, form);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
