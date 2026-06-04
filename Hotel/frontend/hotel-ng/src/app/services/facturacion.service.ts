import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import { Pago } from '../models/pago.model';

@Injectable({ providedIn: 'root' })
export class FacturacionService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/facturacion`;

  list(): Observable<Pago[]> {
    return this.http.get<Pago[]>(this.base);
  }

  create(pago: Pago): Observable<Pago> {
    return this.http.post<Pago>(this.base, pago);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
