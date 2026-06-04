import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import {
  DashboardEjecutivo,
  DashboardPrincipal,
  ReportesEjecutivos,
} from '../models/dashboard.model';

@Injectable({ providedIn: 'root' })
export class ReportesService {
  private readonly http = inject(HttpClient);
  private readonly base = `${API_BASE_URL}/api/v1/reportes`;

  dashboard(): Observable<DashboardPrincipal> {
    return this.http.get<DashboardPrincipal>(`${this.base}/dashboard`);
  }

  dashboardEjecutivo(): Observable<DashboardEjecutivo> {
    return this.http.get<DashboardEjecutivo>(`${this.base}/dashboard/ejecutivo`);
  }

  ejecutivos(): Observable<ReportesEjecutivos> {
    return this.http.get<ReportesEjecutivos>(`${this.base}/ejecutivos`);
  }

  ocupacionPorEstado(): Observable<Record<string, number>> {
    return this.http.get<Record<string, number>>(`${this.base}/avanzados/ocupacion`);
  }
}
