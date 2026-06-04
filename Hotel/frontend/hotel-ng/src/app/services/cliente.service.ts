import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import { Cliente, ReservaHistorial } from '../models/cliente.model';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/clientes`;

  list(q?: string): Observable<Cliente[]> {
    const params = q ? { q } : undefined;
    return this.http.get<Cliente[]>(this.base, { params });
  }

  buscar(q: string): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.base}/buscar`, { params: { q } });
  }

  historialReservas(id: number): Observable<ReservaHistorial[]> {
    return this.http.get<ReservaHistorial[]>(`${this.base}/${id}/reservas`);
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.base, cliente);
  }

  update(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.base}/${id}`, cliente);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
